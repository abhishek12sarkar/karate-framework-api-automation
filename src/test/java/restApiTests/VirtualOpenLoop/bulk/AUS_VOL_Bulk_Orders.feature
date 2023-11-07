@VirtualOpenLoop @Bulk @Australia
Feature: Australia - Virtual Open Loop Bulk Orders

  Background:
    * def apiEndpoints = call read("../../MarketplaceApiEndpoints.feature")
    * def karateUtils = call read("../../KarateUtilities.feature")
    Given url baseURL
    * def AUS_VOL_Bulk_WithOneOrderLine_payload = read(testData + 'VirtualOpenLoop/bulk/AUS_VOL_Bulk_WithOneOrderLine.json')
    * def AUS_VOL_Bulk_WithMultipleOrderLines_payload = read(testData + 'VirtualOpenLoop/bulk/AUS_VOL_Bulk_WithMultipleOrderLines.json')

  @Regression @Sanity
  Scenario: US VOL Bulk order with one orderline
    Given path apiEndpoints.submitVirtualBulk
    And header requestId = karateUtils.generateRequestID()
    And request AUS_VOL_Bulk_WithOneOrderLine_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true

  @Regression
  Scenario: US VOL Bulk order with multiple orderline
    Given path apiEndpoints.submitVirtualBulk
    And header requestId = karateUtils.generateRequestID()
    And request AUS_VOL_Bulk_WithMultipleOrderLines_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true