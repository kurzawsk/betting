package com.kk.betting.web.controller;

import com.kk.betting.dto.BettingEventDTO;
import com.kk.betting.dto.BettorDTO;
import com.kk.betting.web.service.BettingRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by KK on 2017-07-04.
 */
@Controller
@RestController
public class BettorController {

    public static final String BETTORS_VIEW = "bettors";

    private BettingRestService bettingRestService;

    @Autowired
    public BettorController(BettingRestService bettingRestService) {
        this.bettingRestService = bettingRestService;
    }

    @RequestMapping(value = Paths.Bettor.BETTORS_ROOT, method = RequestMethod.GET)
    public ModelAndView viewAll() {
        return new ModelAndView(BETTORS_VIEW);
    }

    @GetMapping(Paths.DATA_ROOT + Paths.Bettor.BETTORS_ROOT)
    public ResponseEntity<List<BettorDTO>> getBettors() {
        ResponseEntity<BettorDTO[]> response = bettingRestService.getForEntity(Paths.Bettor.BETTORS_ROOT, BettorDTO[].class);
        return new ResponseEntity(Stream.of(response.getBody()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Paths.DATA_ROOT + Paths.Bettor.BETTORS_ROOT + "/{bettorId}" + Paths.Bettor.BETTING_EVENTS)
    public ResponseEntity<List<BettingEventDTO>> getBettingEvents(@PathVariable(value = "bettorId") Long bettorId,
                                                                  @RequestParam(name = "showTypes") String showTypesRaw,
                                                                  @RequestParam(name = "from", required = false) String fromRaw,
                                                                  @RequestParam(name = "to", required = false) String toRaw) {
        String uri = UriComponentsBuilder.newInstance().path(Paths.Bettor.BETTORS_ROOT).pathSegment(bettorId.toString())
                .path(Paths.Bettor.BETTING_EVENTS).queryParam("showTypes", showTypesRaw).queryParam("from", fromRaw)
                .queryParam("to", toRaw).build().toUriString();
        ResponseEntity<BettingEventDTO[]> response = bettingRestService.getForEntity(uri, BettingEventDTO[].class);
        return new ResponseEntity(Stream.of(response.getBody()).collect(Collectors.toList()), HttpStatus.OK);
    }
}

