package reporting;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.intuit.karate.Results;
import com.intuit.karate.core.ScenarioResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.intuit.karate.core.Result;
import com.intuit.karate.core.Step;

public class KarateExtentReport {

    private ExtentReports extentReports;
    private ExtentSparkReporter extentSparkReporter;
    private String reportDir = "target/ExtentReport/KarateApiTestReport.html";
    private String reportTitle;
    private Results testResults;
    private ExtentTest featureNode;
    private String featureTitle = "";
    private ExtentTest scenarioNode;
    private String scenarioTitle = "";

    public KarateExtentReport() {
        extentReports = new ExtentReports();
    }

    public KarateExtentReport withKarateResult(Results testResults) {
        this.testResults = testResults;
        return this;
    }

    public KarateExtentReport withReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
        return this;
    }

    public void generateExtentReport() {
        if (this.reportDir != null && !this.reportDir.isEmpty() && this.testResults != null) {
            extentSparkReporter = new ExtentSparkReporter(reportDir);
            extentReports.attachReporter(extentSparkReporter);
            setConfig();
            List<ScenarioResult> scenarioResults = getScenarioResults();
            scenarioResults = scenarioResults.stream().filter((name) -> {
                return (name.getScenario().getName() != null) && !(name.getScenario().getName().isEmpty());
            }).collect(Collectors.toList());
            scenarioResults.forEach((scenarioResult) -> {
                String featureName = getFeatureName(scenarioResult);
                String featureDesc = getFeatureDesc(scenarioResult);
                ExtentTest featureNode = createFeatureNode(featureName, featureDesc);
                String scenarioTitle = getScenarioTitle(scenarioResult);
                ExtentTest scenarioNode = createScenarioNode(featureNode, scenarioTitle);
                scenarioResult.getStepResults().forEach((step) -> {
                    addScenarioStep(scenarioNode, step.getStep(), step.getResult(), step.getStepLog());
                });
            });
            extentReports.flush();
            try {
                URL reportPath = Paths.get("target", "/ExtentReport/KarateApiTestReport.html").toUri().toURL();
                System.out.println("\nKarate Extent Report published: " + reportPath.getProtocol() + "://" + reportPath.getPath());
            } catch (MalformedURLException badReportPath) {
                System.out.println(badReportPath.getMessage());
            }
            return;
        }
        throw new RuntimeException("Missing the Karate Test Result / Report Dir location");
    }

    private List<ScenarioResult> getScenarioResults() {
        return this.testResults.getScenarioResults().toList();
    }

    private String getFeatureName(ScenarioResult scenarioResult) {
        return scenarioResult.getScenario().getFeature().getName();
    }

    private String getFeatureDesc(ScenarioResult scenarioResult) {
        return scenarioResult.getScenario().getFeature().getDescription();
    }

    private ExtentTest createFeatureNode(String featureName, String featureDesc) {
        if (this.featureTitle.equalsIgnoreCase(featureName)) {
            return featureNode;
        }
        this.featureTitle = featureName;
        featureNode = extentReports.createTest(Feature.class, featureName, featureDesc);
        return featureNode;
    }

    private ExtentTest createScenarioNode(ExtentTest featureNode, String scenarioTitle) {
        if (this.scenarioTitle.equalsIgnoreCase(scenarioTitle)) {
            return scenarioNode;
        }
        this.scenarioTitle = scenarioTitle;
        scenarioNode = featureNode.createNode(Scenario.class, scenarioTitle);
        return scenarioNode;
    }

    private String getScenarioTitle(ScenarioResult scenarioResult) {
        return scenarioResult.getScenario().getName();

    }

    private void addScenarioStep(ExtentTest scenarioNode, Step step, Result stepResult, String stepLogs) {
        String type = step.getPrefix();
        String stepTitle = step.getText();
        String status = stepResult.getStatus();
        Throwable error = stepResult.getError();
        ExtentTest stepNode;

        switch (type) {
            case "Given" -> {
                stepNode = scenarioNode.createNode(Given.class, getStepTitle(type, stepTitle));
                addStatus(stepNode, status, error, stepLogs);
            }
            case "When" -> {
                stepNode = scenarioNode.createNode(When.class, getStepTitle(type, stepTitle));
                addStatus(stepNode, status, error, stepLogs);
            }
            case "Then" -> {
                stepNode = scenarioNode.createNode(Then.class, getStepTitle(type, stepTitle));
                addStatus(stepNode, status, error, stepLogs);
            }
            case "And" -> {
                stepNode = scenarioNode.createNode(And.class, getStepTitle(type, stepTitle));
                addStatus(stepNode, status, error, stepLogs);
            }
            default -> {
                stepNode = scenarioNode.createNode(type + " " + getStepTitle(type, stepTitle));
                addStatus(stepNode, status, error, stepLogs);
            }
        }
    }

    private String getStepTitle(String type, String stepText) {
        return type.startsWith("*") ? stepText : type + " " + stepText;
    }

    private void addStatus(ExtentTest stepNode, String status, Throwable error, String stepLogs) {
        switch (status) {
            case "passed" -> stepNode.pass("");
            case "failed" -> stepNode.fail(error);
            default -> stepNode.skip("");
        }
        if (stepLogs != null && !stepLogs.isEmpty())
            stepNode.info(String.format("[print] %s", stepLogs));
    }

    private void setConfig() {
        extentSparkReporter.config().enableOfflineMode(true);
        extentSparkReporter.config().setDocumentTitle(reportTitle);
        extentSparkReporter.config().setTimelineEnabled(true);
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.viewConfigurer().viewOrder().as(
                new ViewName[]{
                        ViewName.DASHBOARD,
                        ViewName.TEST
                }).apply();
        extentReports.setAnalysisStrategy(AnalysisStrategy.BDD);
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Test Environment", System.getProperty("env"));
        extentReports.setSystemInfo("WebService User", "api_testing_SysProd");
    }
}
