package com.kk.betting.domain;

import com.kk.betting.domain.converter.MapStringAttributeConverter;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;



@Entity
@Table(name = "BETTOR")
public class Bettor extends ManagedEntityImp implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final MapStringAttributeConverter mapStringConverter =  new MapStringAttributeConverter();

    @ManyToOne(targetEntity = Bookmaker.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "BOOKMAKER_ID", nullable = false)
    protected Bookmaker bookmaker;

    @Column(name = "AVAILABLE_RESOURCES", precision = 9, scale = 2, nullable = false)
    private BigDecimal availableResources = BigDecimal.ZERO;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    @Column(name = "BETTING_IN_PROGRESS", nullable = false)
    private boolean bettingInProgress = false;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "LOGIC_PARAMETERS", length = 4000)
    public String logicParametersSerialized;

    @Column(name = "DEFAULT_MONTH_START_AMOUNT", precision = 9, scale = 2, nullable = false)
    private BigDecimal defaultMonthStartAmount = BigDecimal.ZERO;

    @Column(name = "TOTAL_PROFIT", precision = 9, scale = 2)
    private BigDecimal totalProfit;

    @Column(name = "AVG_RESOURCES_SINCE_LAST_ALGN", precision = 9, scale = 2)
    private BigDecimal avgResourcesSinceLastAlignment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bettor", cascade = CascadeType.REMOVE)
    @SortComparator(BettingEventTimeComparator.class)
    private List<BettingEvent> bettingEvents;

    public Bettor() {
    }

    public boolean isBettingInProgress() {
        return bettingInProgress;
    }

    public void setBettingInProgress(boolean bettingInProgress) {
        this.bettingInProgress = bettingInProgress;
    }

    public BigDecimal getAvailableResources() {
        return availableResources;
    }

    public void addToResources(BigDecimal amount) {
        availableResources = availableResources.add(amount);
    }

    public void subtractFromResources(BigDecimal amount) {
        availableResources = availableResources.subtract(amount);
    }

    public Bookmaker getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(Bookmaker bookmaker) {
        this.bookmaker = bookmaker;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Map<String, Object> getLogicParameters() {
        return mapStringConverter.convertToDatabaseColumn(logicParametersSerialized);
    }

    public void setLogicParameters(Map<String, Object> logicParameters) throws IOException {
        this.logicParametersSerialized =  mapStringConverter.convertToEntityAttribute(logicParameters);
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void addToTotalProfit(BigDecimal amount) {
        totalProfit =  totalProfit.add(amount);
    }

    public BigDecimal getDefaultMonthStartAmount() {
        return defaultMonthStartAmount;
    }

    public void setDefaultMonthStartAmount(BigDecimal defaultMonthStartAmount) {
        this.defaultMonthStartAmount = defaultMonthStartAmount;
    }

    public BigDecimal getAvgResourcesSinceLastAlignment() {
        return avgResourcesSinceLastAlignment;
    }

    public void setAvgResourcesSinceLastAlignment(BigDecimal avgResourcesSinceLastAlignment) {
        this.avgResourcesSinceLastAlignment = avgResourcesSinceLastAlignment;
    }

    public List<BettingEvent> getBettingEvents() {
        return bettingEvents;
    }

    public static class BettingEventTimeComparator implements Comparator<BettingEvent> {
        @Override
        public int compare(BettingEvent b1, BettingEvent b2) {
            return b1.getEventTime().compareTo(b2.getEventTime());
        }
    }


}
