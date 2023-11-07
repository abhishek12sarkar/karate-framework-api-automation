@ignore @report=false
Feature: Marketplace API Endpoints

  Scenario: API Endpoints

    # Virtual Open Loop -> POST
    * def submitVirtualIndividual = "/rewardsOrderProcessing/v1/submitVirtualIndividual"
    * def submitVirtualBulk = "/rewardsOrderProcessing/v1/submitVirtualBulk"
    * def submitRealTimeVirtualIndividual = "/rewardsOrderProcessing/v1/submitRealTimeVirtualIndividual"
    * def submitRealTimeVirtualBulk = "/rewardsOrderProcessing/v1/submitRealTimeVirtualBulk"

    # eGift -> POST
    * def submitEgiftIndividual = "/rewardsOrderProcessing/v1/submitEgiftIndividual"
    * def submitEgiftBulk = "/rewardsOrderProcessing/v1/submitEgiftBulk"
    * def submitRealTimeEgiftIndividual = "/rewardsOrderProcessing/v1/submitRealTimeEgiftIndividual"
    * def submitRealTimeEgiftBulk = "/rewardsOrderProcessing/v1/submitRealTimeEgiftBulk"

    # Physical Closed Loop -> POST
    * def submitClosedLoopAnonymous = "/rewardsOrderProcessing/v1/submitClosedLoopAnonymous"
    * def submitClosedLoopIndividual = "/rewardsOrderProcessing/v1/submitClosedLoopIndividual"

    # Physical Open Loop -> POST
    * def submitOpenLoopPersonalizedBulk = "/rewardsOrderProcessing/v1/submitOpenLoopPersonalizedBulk"
    * def submitOpenLoopPersonalizedIndividual = "/rewardsOrderProcessing/v1/submitOpenLoopPersonalizedIndividual"
    * def submitOpenLoopPersonalizedIndividual = "/rewardsOrderProcessing/v1/submitOpenLoopPersonalizedIndividual"

    # Instant Issue -> POST
    * def submitPersonalizedInstantIssueFunding = "/rewardsOrderProcessing/v1/submitPersonalizedInstantIssueFunding"

    # Demographic Update -> POST
    * def submitDemographicUpdate = "/rewardsOrderProcessing/v1/submitDemographicUpdate"

    # Funding -> POST
    * def submitFunding = "/rewardsOrderProcessing/v1/submitFunding"
    * def submitRealTimeFunding = "/rewardsOrderProcessing/v1/submitRealTimeFunding"

    # Order Info -> GET
    * def orderInfo = "/rewardsOrderProcessing/v1/orderInfo/byKeys"

    # Client Program Info -> GET
    * def clientProgram = "/rewardsCatalogProcessing/v1/clientProgram/byKey"
    * def clientProgramsBySubOrg = "/rewardsCatalogProcessing/v1/clientProgramsBySubOrg"