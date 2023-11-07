@eGift @Individual @RealtimeOrder @UnitedStates
Feature: Real Time eGift - Individual

  Background:
    * def apiEndpoints = call read("../../../MarketplaceApiEndpoints.feature")
    * def karateUtils = call read("../../../KarateUtilities.feature")
    Given url baseURL
    * def US_RealTime_eGift_Individual_payload = read(testData + 'realtimeOrders/eGift/individual/US_RealTime_eGift_Individual.json')

  @Regression @Sanity
  Scenario: Real Time eGift Individual order

    Given path apiEndpoints.submitRealTimeEgiftIndividual
    And header requestId = karateUtils.generateRequestID()
    And request US_RealTime_eGift_Individual_payload
    When method POST
    Then status 201
    And match response.isCompleted == true
    And match response.success == true