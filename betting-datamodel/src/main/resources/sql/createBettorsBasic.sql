INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (1, true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 1.25
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX" ],
  "supportedProposalsSources" : [ "ProposeAllPinnacleMatchesLogic" ]
}', 652);
INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (2, true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.5,
    "upperLimit" : 1.75
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX" ],
  "supportedProposalsSources" : [ "ProposeAllPinnacleMatchesLogic" ]
}', 652);
INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (3, true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.75,
    "upperLimit" : 2.0
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX" ],
  "supportedProposalsSources" : [ "ProposeAllPinnacleMatchesLogic" ]
}', 652);
INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (4, true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.25,
    "upperLimit" : 1.5
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX" ],
  "supportedProposalsSources" : [ "ProposeAllPinnacleMatchesLogic" ]
}', 652);
INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (5, true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 5.5
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X","ODDX2","ODD12" ],
  "supportedProposalsSources" : [ "ProposeAllPinnacleMatchesLogic" ]
}', 652);