@VirtualOpenLoop @Individual @Australia @Test123

Feature: Australia - Virtual Open Loop Individual Orders

  Background:
    * def apiEndpoints = call read("../../MarketplaceApiEndpoints.feature")
    * def karateUtils = call read("../../KarateUtilities.feature")
    Given url baseURL
    * def AUS_VOL_Individual_WithOneOrderLine_payload = read(testData + 'VirtualOpenLoop/individual/AUS_VOL_Individual_WithOneOrderLine.json')
    * def AUS_VOL_Individual_WithMultipleOrderLines_payload = read(testData + 'VirtualOpenLoop/individual/AUS_VOL_Individual_WithMultipleOrderLines.json')

  @Regression @Sanity
  Scenario: AUS VOL Individual order with one orderline
    Given path apiEndpoints.submitVirtualIndividual
    And header requestId = karateUtils.generateRequestID()
    And request AUS_VOL_Individual_WithOneOrderLine_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true

  @Regression
  Scenario: AUS VOL Individual order with multiple orderline
    Given path apiEndpoints.submitVirtualIndividual
    And header requestId = karateUtils.generateRequestID()
    And request AUS_VOL_Individual_WithMultipleOrderLines_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true