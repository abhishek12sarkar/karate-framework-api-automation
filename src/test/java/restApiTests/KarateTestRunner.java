package restApiTests;

import com.intuit.karate.Results;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import com.intuit.karate.Runner.Builder;
import reporting.KarateExtentReport;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class KarateTestRunner {

    private static final int _PARALLEL_THREAD_COUNT = 50;
    private static final String _DELIMITER = ",";
    private static String _ENVIRONMENT = System.getProperty("env");
    private static final String _DEFAULT_ENVIRONMENT = "stratus";
    private static final String _REPORT_TITLE = "Karate API Test Report";

    @Test
    public void executeKarateTestsParallel() {
        Builder testRunner = new Builder();
        setTestExecutionEnvironment(testRunner);
        testRunner.path("classpath:restApiTests/");
        testRunner.outputCucumberJson(true);
        Results result = testRunner.tags(getTags()).parallel(_PARALLEL_THREAD_COUNT);
        generateReport(result.getReportDir());
        KarateExtentReport karateExtentReport = new KarateExtentReport()
                .withKarateResult(result)
                .withReportTitle(_REPORT_TITLE);
        karateExtentReport.generateExtentReport();
    }


    private void generateReport(String reportDirLocation) {
        File reportDir = new File(reportDirLocation);
        Collection<File> jsonFileCollection = FileUtils.listFiles(reportDir, new String[]{"json"}, true);
        List<String> jsonFiles = new ArrayList<>();
        jsonFileCollection.forEach(file -> jsonFiles.add(file.getAbsolutePath()));
        Configuration configuration = new Configuration(reportDir, _REPORT_TITLE);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.addCustomJsFiles(Collections.singletonList("src/test/java/restApiTests/CustomReportUI.js"));
        configuration.addClassifications("User", "api_testing_SysProd");
        configuration.addClassifications("Operating System", System.getProperty("os.name"));
        configuration.addClassifications("Test Environment", _ENVIRONMENT);
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
        try {
            URL reportPath = Paths.get("target", "karate-reports/cucumber-html-reports/overview-features.html").toUri().toURL();
            System.out.println("\nKarate Cucumber Report published: " + reportPath.getProtocol() + "://" + reportPath.getPath());
        } catch (MalformedURLException badReportPath) {
            System.out.println(badReportPath.getMessage());
        }
    }


    private List<String> getTags() {
        String cliTags = System.getProperty("tags");
        List<String> cliTagsList = Collections.emptyList();
        if (cliTags == null) {
            return cliTagsList;
        }
        if (cliTags.contains(_DELIMITER)) {
            String[] cliTagArray = cliTags.split(_DELIMITER);
            cliTagsList = Arrays.asList(cliTagArray);
            return cliTagsList;
        }
        cliTagsList = List.of(cliTags);
        return cliTagsList;
    }

    private static void setTestExecutionEnvironment(Builder testRunner) {
        if (_ENVIRONMENT == null) {
            _ENVIRONMENT = _DEFAULT_ENVIRONMENT;
            testRunner.karateEnv(_ENVIRONMENT);
        } else
            testRunner.karateEnv(_ENVIRONMENT);
    }
}


