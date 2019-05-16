package com.kk.betting.services.bettorhandling.common.endpoint;

import com.kk.betting.domain.BettingEvent;
import com.kk.betting.domain.Bettor;
import com.kk.betting.dto.BettingEventDTO;
import com.kk.betting.dto.BettorDTO;
import com.kk.betting.services.bettorhandling.common.service.BettingEventConverter;
import com.kk.betting.services.bettorhandling.common.service.BettorConverter;
import com.kk.betting.services.common.dao.BettingEventDao;
import com.kk.betting.services.common.dao.BettorDao;
import com.kk.betting.services.common.util.Paths;
import com.kk.betting.services.common.util.Roles;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-07-13.
 */

@LocalBean
@Stateless
@Path(Paths.Bettor.BETTORS_ROOT)
@RolesAllowed({Roles.RO})
public class BettorResource {

    public static final String SHOW_ONLY_PENDING_MATCHES_PARAMETER_NAME = "showOnlyPending";

    @Inject
    private BettorDao bettorDao;

    @Inject
    private BettingEventDao bettingEventDao;

    @Inject
    private BettorConverter bettorConverter;

    @Inject
    private BettingEventConverter bettingEventConverter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBettors() {
        List<BettorDTO> bettorDTOs = bettorDao.findAll().stream().sorted((b1, b2) -> b2.getAvailableResources().compareTo(b1.getAvailableResources()))
                .map(bettor -> bettorConverter.convertToDTO(bettor)).collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(bettorDTOs).build();
    }

    @GET
    @Path("/{bettorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBettor(@PathParam("bettorId") Long bettorId) {
        return Response.status(Response.Status.OK).entity(bettorConverter.convertToDTO(bettorDao.findById(bettorId))).build();
    }

    @GET
    @Path("/{bettorId}" + Paths.Bettor.BETTING_EVENTS)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBettingEvents(@PathParam("bettorId") Long bettorId,
                                        @QueryParam("showTypes") String showTypesRaw,
                                        @QueryParam("from") String fromRaw,
                                        @QueryParam("to") String toRaw) {
        BettingEventTypes showTypes = BettingEventTypes.valueOf(showTypesRaw);
        Predicate<BettingEvent> timeFilter;
        LocalDate from;
        LocalDate to;
        try {
            from = parse(fromRaw);
            to = parse(toRaw);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error parsing dates: " + fromRaw + ", " + toRaw).build();
        }
        if (Objects.nonNull(from) && Objects.nonNull(to) && from.isAfter(to)) {
            Response.status(Response.Status.BAD_REQUEST).entity("Incorrect dates range: " + fromRaw + " - " + toRaw).build();
        }

        LocalDateTime fromTs = from != null ? from.atStartOfDay() : null;
        LocalDateTime toTs = to != null ? to.atStartOfDay().withHour(23).withMinute(59).withSecond(59) : null;
        timeFilter = b -> (fromTs == null || b.getEventTime().isAfter(fromTs)) && (toTs == null || b.getEventTime().isBefore(toTs));

        List<BettingEventDTO> bettingEventsDTOs = showTypes.getBettingEvents(bettingEventDao, bettorDao.findById(bettorId))
                .stream().filter(timeFilter).sorted((b1, b2) -> b2.getId().compareTo(b1.getId())).map(be -> bettingEventConverter.convertToDTO(be)).collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(bettingEventsDTOs).build();
    }

    private LocalDate parse(String rawDate) {
        if (StringUtils.isNotEmpty(rawDate)) {
            return LocalDate.parse(rawDate);
        }
        return null;
    }

    private enum BettingEventTypes {
        ALL {
            public List<? extends BettingEvent> getBettingEvents(BettingEventDao bettingEventDao, Bettor bettor) {
                return bettor.getBettingEvents();
            }
        }, PENDING_MATCHES {
            public List<? extends BettingEvent> getBettingEvents(BettingEventDao bettingEventDao, Bettor bettor) {
                return bettingEventDao.gePendingMatchBettingEvents(bettor.getId());
            }
        }, TOTAL_PROFIT_CHANGES {
            public List<? extends BettingEvent> getBettingEvents(BettingEventDao bettingEventDao, Bettor bettor) {
                return bettingEventDao.geProfitChangesBettingEvents(bettor.getId());
            }
        };

        public abstract List<? extends BettingEvent> getBettingEvents(BettingEventDao bettingEventDao, Bettor bettor);
    }
}
