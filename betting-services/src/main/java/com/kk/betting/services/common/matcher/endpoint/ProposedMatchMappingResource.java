package com.kk.betting.services.common.matcher.endpoint;

import com.kk.betting.domain.Match;
import com.kk.betting.domain.ProposedMatchMapping;
import com.kk.betting.dto.ProcessProposedMatchMappingsDTO;
import com.kk.betting.dto.ProposedMatchMappingDTO;
import com.kk.betting.dto.ProposedMatchMappingRequestDTO;
import com.kk.betting.services.common.dao.ProposedMatchMappingDao;
import com.kk.betting.services.common.util.Paths;
import com.kk.betting.services.common.util.Roles;
import com.kk.betting.services.common.matcher.service.MappingService;
import com.kk.betting.services.common.matcher.service.ProposedMatchMappingConverter;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-07-29.
 */
@LocalBean
@Stateless
@Path(Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ROOT)
@RolesAllowed({Roles.ADMIN})
public class ProposedMatchMappingResource {

    private static final String SOURCE_SYSTEM = "MANUAL";

    @Inject
    private MappingService mappingService;

    @Inject
    private ProposedMatchMappingConverter proposedMatchMappingConverter;

    @Inject
    private ProposedMatchMappingDao proposedMatchMappingDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProposedMatchMappingsToProcess() {
        List<ProposedMatchMappingDTO> result = proposedMatchMappingDao.getProposedMatchMappingToProcess()
                .stream().map(p -> proposedMatchMappingConverter.convertToDTO(p)).collect(Collectors.toList());
        return Response.ok().entity(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findMatchingMatches(@Valid ProposedMatchMappingRequestDTO proposedMatchMappingRequestDTO) {

        Optional<Match> match = mappingService.findMatch(proposedMatchMappingRequestDTO.getHomeTeam(), proposedMatchMappingRequestDTO.getAwayTeam(), proposedMatchMappingRequestDTO.getStartTime());

        if (match.isPresent()) {
            ProposedMatchMappingDTO proposedMatchMappingDTO = new ProposedMatchMappingDTO();
            proposedMatchMappingDTO.setMatchId(match.get().getId());
            proposedMatchMappingDTO.setProposedHomeTeamName(proposedMatchMappingRequestDTO.getHomeTeam());
            proposedMatchMappingDTO.setHomeTeamId(match.get().getHomeTeam().getId());
            proposedMatchMappingDTO.setHomeTeamNames(proposedMatchMappingConverter.getAllTeamNames(match.get().getHomeTeam()));
            proposedMatchMappingDTO.setHomeTeamSimilarityFactor(1.0);

            proposedMatchMappingDTO.setMatchId(match.get().getId());
            proposedMatchMappingDTO.setProposedAwayTeamName(proposedMatchMappingRequestDTO.getAwayTeam());
            proposedMatchMappingDTO.setAwayTeamId(match.get().getAwayTeam().getId());
            proposedMatchMappingDTO.setAwayTeamNames(proposedMatchMappingConverter.getAllTeamNames(match.get().getAwayTeam()));
            proposedMatchMappingDTO.setAwayTeamSimilarityFactor(1.0);
            proposedMatchMappingDTO.setProposedStartTime(proposedMatchMappingRequestDTO.getStartTime());
            proposedMatchMappingDTO.setMatchStartTime(match.get().getStartTime());
            proposedMatchMappingDTO.setSourceSystem(SOURCE_SYSTEM);

            return Response.ok().entity(proposedMatchMappingDTO).build();
        } else {
            List<ProposedMatchMapping> proposedMappings = mappingService.findProposedMatchMappings(proposedMatchMappingRequestDTO.getHomeTeam(), proposedMatchMappingRequestDTO.getAwayTeam(), proposedMatchMappingRequestDTO.getStartTime(), SOURCE_SYSTEM);
            List<ProposedMatchMappingDTO> proposedMatchMappingDTOs = proposedMappings.stream().map(pmm -> proposedMatchMappingConverter.convertToDTO(pmm)).collect(Collectors.toList());
            return Response.ok().entity(proposedMatchMappingDTOs).build();
        }


    }

    @POST
    @Path(Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_STORE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAndStoreSimilarMatches(@Valid ProposedMatchMappingRequestDTO proposedMatchMappingRequestDTO) {
        List<ProposedMatchMapping> proposedMappings = mappingService.findAndStoreProposedMatchMappings(proposedMatchMappingRequestDTO.getHomeTeam(), proposedMatchMappingRequestDTO.getAwayTeam(), proposedMatchMappingRequestDTO.getStartTime(), SOURCE_SYSTEM);
        List<ProposedMatchMappingDTO> proposedMatchMappingDTOs = proposedMappings.stream().map(pmm -> proposedMatchMappingConverter.convertToDTO(pmm)).collect(Collectors.toList());
        return Response.ok().entity(proposedMatchMappingDTOs).build();
    }

    @POST
    @Path("/{proposedMatchMappingId}" + Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_ACCEPT)
    @Produces(MediaType.TEXT_PLAIN)
    public Response acceptProposedMatchMapping(@PathParam("proposedMatchMappingId") Long proposedMatchMappingId) {
        boolean result = mappingService.acceptProposedMatchMapping(proposedMatchMappingId);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("/{proposedMatchMappingId}" + Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_REJECT)
    @Produces(MediaType.TEXT_PLAIN)
    public Response rejectProposedMatchMapping(@PathParam("proposedMatchMappingId") Long proposedMatchMappingId) {
        boolean result = mappingService.rejectProposedMatchMapping(proposedMatchMappingId);
        return Response.ok().entity(result).build();
    }

    @POST
    @Path(Paths.ProposedMatchMapping.PROPOSED_MATCH_MAPPINGS_BULK_PROCESS)
    public Response processProposedMatchMappings(@Valid ProcessProposedMatchMappingsDTO processProposedMatchMappingsDTO) {
        boolean result = mappingService.processProposedMatchMappings(processProposedMatchMappingsDTO.getIdsToAccept(), processProposedMatchMappingsDTO.getIdsToReject());
        return Response.ok().entity(result).build();
    }

}
