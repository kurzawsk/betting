package com.kk.betting.web.controller;

/**
 * Created by KK on 2017-07-18.
 */
public class Paths {

    public static final String DATA_ROOT = "/data";

    public static class Admin {
        public static final String ADMIN_ROOT = "/admin";
        public static final String ADMIN_DOWNLOAD_FILE = ADMIN_ROOT + "/download";
    }

    public static class Bettor {
        public static final String BETTORS_ROOT = "/bettors";
        public static final String BETTING_EVENTS = "/betting-events";
    }

    public static class ProposedMatchMapping {
        public static final String PROPOSED_MATCH_MAPPINGS_ROOT = "/proposed-match-mappings";
        public static final String ACCEPT = "/accept";
        public static final String REJECT = "/reject";
        public static final String BULK_PROCESS = "/bulk-process";
    }
}
