@VirtualOpenLoop @Individual @UnitedStates

Feature: United States - Virtual Open Loop Individual Orders

  Background:
    * def apiEndpoints = call read("../../MarketplaceApiEndpoints.feature")
    * def karateUtils = call read("../../KarateUtilities.feature")
    Given url baseURL
    * def US_VOL_Individual_WithOneOrderLine_payload = read(testData + 'VirtualOpenLoop/individual/US_VOL_Individual_WithOneOrderLine.json')
    * def US_VOL_Individual_WithMultipleOrderLines_payload = read(testData + 'VirtualOpenLoop/individual/US_VOL_Individual_WithMultipleOrderLines.json')

  @Regression @Sanity
  Scenario: US VOL Individual order with one orderline
    Given path apiEndpoints.submitVirtualIndividual
    And header requestId = karateUtils.generateRequestID()
    And request US_VOL_Individual_WithOneOrderLine_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true

  @Regression
  Scenario: US VOL Individual order with multiple orderline
    Given path apiEndpoints.submitVirtualIndividual
    And header requestId = karateUtils.generateRequestID()
    And request US_VOL_Individual_WithMultipleOrderLines_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true