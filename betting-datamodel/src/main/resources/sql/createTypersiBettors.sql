
--- basic profit ----
INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_10" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_20" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_30" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_40" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_50" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_60" ]
}', 704);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_70" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_80" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_90" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_100" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_110" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_120" ]
}', 704);

--- typeri efficiency

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_10" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_20" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_30" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_40" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_50" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_60" ]
}', 704);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_70" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_80" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_90" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_100" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_110" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_120" ]
}', 704);

--- profit & efficiency









INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_10" , "TYPERSI.EFFICIENCY_10"]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_20" , "TYPERSI.EFFICIENCY_20" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_30", "TYPERSI.EFFICIENCY_30" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_40", "TYPERSI.EFFICIENCY_40" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_50", "TYPERSI.EFFICIENCY_50" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_60", "TYPERSI.EFFICIENCY_60" ]
}', 704);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_70", "TYPERSI.EFFICIENCY_70" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_80", "TYPERSI.EFFICIENCY_80" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_90", "TYPERSI.EFFICIENCY_90" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_100", "TYPERSI.EFFICIENCY_100" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_110", "TYPERSI.EFFICIENCY_110" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_120" , "TYPERSI.EFFICIENCY_120" ]
}', 704);


--- top 5

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.TOP5" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.MOST_EFFICIENT" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.MOST_EFFICIENT" , "TYPERSI.TOP5" ]
}', 704);

--- Fixed risk amount  ---
--- basic profit ----
INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_10" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_20" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_30" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_40" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_50" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_60" ]
}', 704);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_70" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_80" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_90" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_100" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_110" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_120" ]
}', 704);

--- typeri efficiency

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_10" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_20" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_30" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_40" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_50" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_60" ]
}', 704);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_70" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_80" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_90" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_100" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_110" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_120" ]
}', 704);

--- profit & efficiency

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_10" , "TYPERSI.EFFICIENCY_10"]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_20" , "TYPERSI.EFFICIENCY_20" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_30", "TYPERSI.EFFICIENCY_30" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_40", "TYPERSI.EFFICIENCY_40" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_50", "TYPERSI.EFFICIENCY_50" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_60", "TYPERSI.EFFICIENCY_60" ]
}', 704);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_70", "TYPERSI.EFFICIENCY_70" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_80", "TYPERSI.EFFICIENCY_80" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_90", "TYPERSI.EFFICIENCY_90" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_100", "TYPERSI.EFFICIENCY_100" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_110", "TYPERSI.EFFICIENCY_110" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_120" , "TYPERSI.EFFICIENCY_120" ]
}', 704);


--- top 5

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.TOP5" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.MOST_EFFICIENT" ]
}', 704);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.MOST_EFFICIENT" , "TYPERSI.TOP5" ]
}', 704);

--- bet at home ---


--- basic profit ----
INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_10" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_20" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_30" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_40" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_50" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_60" ]
}', 706);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_70" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_80" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_90" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_100" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_110" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_120" ]
}', 706);

--- typeri efficiency

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_10" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_20" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_30" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_40" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_50" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_60" ]
}', 706);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_70" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_80" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_90" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_100" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_110" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_120" ]
}', 706);

--- profit & efficiency









INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_10" , "TYPERSI.EFFICIENCY_10"]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_20" , "TYPERSI.EFFICIENCY_20" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_30", "TYPERSI.EFFICIENCY_30" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_40", "TYPERSI.EFFICIENCY_40" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_50", "TYPERSI.EFFICIENCY_50" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_60", "TYPERSI.EFFICIENCY_60" ]
}', 706);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_70", "TYPERSI.EFFICIENCY_70" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_80", "TYPERSI.EFFICIENCY_80" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_90", "TYPERSI.EFFICIENCY_90" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_100", "TYPERSI.EFFICIENCY_100" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_110", "TYPERSI.EFFICIENCY_110" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_120" , "TYPERSI.EFFICIENCY_120" ]
}', 706);


--- top 5

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.TOP5" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.MOST_EFFICIENT" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider",
    "part" : 0.05
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.MOST_EFFICIENT" , "TYPERSI.TOP5" ]
}', 706);

--- Fixed risk amount  ---
--- basic profit ----
INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_10" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_20" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_30" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_40" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_50" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_60" ]
}', 706);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_70" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_80" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_90" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_100" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_110" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_120" ]
}', 706);

--- typeri efficiency

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_10" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_20" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_30" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_40" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_50" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_60" ]
}', 706);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_70" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_80" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_90" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_100" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_110" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_120" ]
}', 706);

--- profit & efficiency

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_10" , "TYPERSI.EFFICIENCY_10"]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_20" , "TYPERSI.EFFICIENCY_20" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_30", "TYPERSI.EFFICIENCY_30" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_40", "TYPERSI.EFFICIENCY_40" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_50", "TYPERSI.EFFICIENCY_50" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_60", "TYPERSI.EFFICIENCY_60" ]
}', 706);


INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_70", "TYPERSI.EFFICIENCY_70" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_80", "TYPERSI.EFFICIENCY_80" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_90", "TYPERSI.EFFICIENCY_90" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_100", "TYPERSI.EFFICIENCY_100" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [  "TYPERSI.PROFIT_110", "TYPERSI.EFFICIENCY_110" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.PROFIT_120" , "TYPERSI.EFFICIENCY_120" ]
}', 706);


--- top 5

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.TOP5" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.MOST_EFFICIENT" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, null, '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.MOST_EFFICIENT" , "TYPERSI.TOP5" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, '"TYPERSI.EFFICIENCY_20" , "TYPERSI.PROFIT_10"', '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_20" , "TYPERSI.PROFIT_10" ]
}', 706);

INSERT INTO public.bettor (id, active, available_resources, betting_in_progress, description, logic_parameters, bookmaker_id) VALUES (nextval('seq_int'), true, 200.00, false, '"TYPERSI.EFFICIENCY_20" , "TYPERSI.PROFIT_10"', '{
  "@class" : "com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic",
  "riskAmountProvider" : {
    "@class" : "com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider",
    "fixedRiskAmount" : 20.00
  },
  "supportedOddRangesProvider" : {
    "lowerLimit" : 1.0,
    "upperLimit" : 10000.00
  },
  "supportedOddTypes" : [ "ODD1", "ODD2", "ODDX", "ODD1X", "ODD12", "ODDX2", "ODDBTSY", "ODDBTSN", "ODDO05", "ODDO15", "ODDO25", "ODDO35", "ODDO45", "ODDO55", "ODDO65", "ODDU05", "ODDU15", "ODDU25", "ODDU35", "ODDU45", "ODDU55", "ODDU65" ],
  "supportedProposalsSources" : [ "TYPERSI.EFFICIENCY_20" , "TYPERSI.PROFIT_10" ]
}', 704);
