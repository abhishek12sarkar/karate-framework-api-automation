@VirtualOpenLoop @Bulk @RealtimeOrder @UnitedStates
Feature: Real Time Virtual Open Loop - Bulk

  Background:
    * def apiEndpoints = call read("../../../MarketplaceApiEndpoints.feature")
    * def karateUtils = call read("../../../KarateUtilities.feature")
    Given url baseURL
    * def US_RealTime_VOL_Bulk_payload = read(testData + 'realtimeOrders/virtualOpenLoop/bulk/US_RealTime_VOL_Bulk.json')


  @Regression @Sanity
  Scenario: Real Time Virtual Open Loop Individual order
    Given path apiEndpoints.submitRealTimeVirtualBulk
    And header requestId = karateUtils.generateRequestID()
    And request US_RealTime_VOL_Bulk_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true