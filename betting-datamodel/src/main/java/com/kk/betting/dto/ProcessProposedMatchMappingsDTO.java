package com.kk.betting.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KK on 2017-08-01.
 */
public class ProcessProposedMatchMappingsDTO implements Serializable {

    private List<Long> idsToAccept;
    private List<Long> idsToReject;

    public List<Long> getIdsToAccept() {
        return idsToAccept;
    }

    public void setIdsToAccept(List<Long> idsToAccept) {
        this.idsToAccept = idsToAccept;
    }

    public List<Long> getIdsToReject() {
        return idsToReject;
    }

    public void setIdsToReject(List<Long> idsToReject) {
        this.idsToReject = idsToReject;
    }
}
