package com.kk.betting.web.controller;

import com.kk.betting.dto.ProcessProposedMatchMappingsDTO;
import com.kk.betting.dto.ProposedMatchMappingDTO;
import com.kk.betting.web.service.BettingRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by KK on 2017-07-31.
 */
@Controller
@RestController
public class ProposedMatchMappingController {

    public static final String PROPOSED_MATCH_MAPPING_VIEW = "proposed-match-mappings";

    private BettingRestService bettingRestService;

    @Autowired
    public ProposedMatchMappingController(BettingRestService bettingRestService) {
        this.bettingRestService = bettingRestService;
    }

    @RequestMapping(value = Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT, method = RequestMethod.GET)
    public ModelAndView viewAll() {
        return new ModelAndView(PROPOSED_MATCH_MAPPING_VIEW);
    }


    @GetMapping(Paths.DATA_ROOT + Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT)
    public ResponseEntity<List<ProposedMatchMappingDTO>> getProposedMatchMappings() {
        ResponseEntity<ProposedMatchMappingDTO[]> response = bettingRestService.getForEntity(Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT, ProposedMatchMappingDTO[].class);
        return new ResponseEntity(Stream.of(response.getBody()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(Paths.DATA_ROOT + Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT + "/{proposedMatchMappingId}" + Paths.ProposedMatchMapping.ACCEPT)
    public ResponseEntity<List<ProposedMatchMappingDTO>> acceptProposedMatchMapping(@PathVariable("proposedMatchMappingId") Long proposedMatchMappingId) {
        ResponseEntity<Boolean> response = bettingRestService.postForEntity(Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT + proposedMatchMappingId + Paths.ProposedMatchMapping.ACCEPT, Boolean.class);
        return new ResponseEntity(response.getBody(), HttpStatus.OK);
    }

    @PostMapping(Paths.DATA_ROOT + Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT + "/{proposedMatchMappingId}" + Paths.ProposedMatchMapping.REJECT)
    public ResponseEntity<Boolean> rejectProposedMatchMapping(@PathVariable("proposedMatchMappingId") Long proposedMatchMappingId) {
        ResponseEntity<Boolean> response = bettingRestService.postForEntity(Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT + proposedMatchMappingId + Paths.ProposedMatchMapping.REJECT, Boolean.class);
        return new ResponseEntity(response.getBody(), HttpStatus.OK);
    }

    @PostMapping(value = Paths.DATA_ROOT + Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT + Paths.ProposedMatchMapping.BULK_PROCESS, consumes = "application/json")
    public ResponseEntity<Boolean> processProposedMatchMappings(@RequestBody ProcessProposedMatchMappingsDTO processProposedMatchMappingsDTO) {
        ResponseEntity<Boolean> response = bettingRestService.postForEntity(Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT + Paths.ProposedMatchMapping.BULK_PROCESS, Boolean.class, processProposedMatchMappingsDTO);
        return new ResponseEntity(response.getBody(), HttpStatus.OK);
    }
}
