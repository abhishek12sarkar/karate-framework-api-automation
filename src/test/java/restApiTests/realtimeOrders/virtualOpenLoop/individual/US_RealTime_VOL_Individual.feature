@VirtualOpenLoop @Individual @RealtimeOrder @UnitedStates
Feature: Real Time Virtual Open Loop - Individual

  Background:
    * def apiEndpoints = call read("../../../MarketplaceApiEndpoints.feature")
    * def karateUtils = call read("../../../KarateUtilities.feature")
    Given url baseURL
    * def US_RealTime_VOL_Individual_payload = read(testData + 'realtimeOrders/virtualOpenLoop/individual/US_RealTime_VOL_Individual.json')

  @Regression @Sanity
  Scenario: Real Time Virtual Open Loop Individual order

    Given path apiEndpoints.submitRealTimeVirtualIndividual
    And header requestId = karateUtils.generateRequestID()
    And request US_RealTime_VOL_Individual_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true