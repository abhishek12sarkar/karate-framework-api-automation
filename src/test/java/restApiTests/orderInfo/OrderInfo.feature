Feature: Get Order Info

  Background:
    * def karateUtils = call read("../KarateUtilities.feature")
    Given url baseURL


  Scenario: Get Order Info by order number

    Given path '/rewardsOrderProcessing/v1/orderInfo/byKeys/'
    And header requestId = karateUtils.generateRequestID()
    And param orderNumber = 402911281
    When method GET
    Then status 200