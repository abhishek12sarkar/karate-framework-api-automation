@ignore @report=false
Feature: Karate Utilities

  Scenario: Karate Utilities

    * def generateRequestID =
    """
    function() {
    return java.util.UUID.randomUUID();
    }
    """