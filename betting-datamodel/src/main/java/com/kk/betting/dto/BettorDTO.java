package com.kk.betting.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by KK on 2017-07-05.
 */
public class BettorDTO implements Serializable {

    private Long id;
    private BigDecimal availableResources;
    private BigDecimal totalProfit;
    private String description;
    private String bookmaker;
    private String parameters;
    private boolean isActive;
    private BigDecimal avgResourcesSinceLastAlignment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAvailableResources() {
        return availableResources;
    }

    public void setAvailableResources(BigDecimal availableResources) {
        this.availableResources = availableResources;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(String bookmaker) {
        this.bookmaker = bookmaker;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public BigDecimal getAvgResourcesSinceLastAlignment() {
        return avgResourcesSinceLastAlignment;
    }

    public void setAvgResourcesSinceLastAlignment(BigDecimal avgResourcesSinceLastAlignment) {
        this.avgResourcesSinceLastAlignment = avgResourcesSinceLastAlignment;
    }
}
