/**
 *
 */
package com.kk.betting.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author kk
 */
@Entity
@Table(name = "BOOKMAKER")
public class Bookmaker extends ManagedEntityImp {

    @Column(name = "WEB_PAGE_URL", nullable = false, unique = true, length = 500)
    private String webPageUrl;

    @Column(name = "LABEL", unique = true, length = 30)
    private String label;

    @Column(name = "MINIMAL_AMOUNT_TO_BET", precision = 9, scale = 2, nullable = false)
    private BigDecimal minimalAmountToBet = BigDecimal.ZERO;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bookmaker", cascade = CascadeType.PERSIST)
    private List<BookmakerName> names;

    @Column(name = "TAX", precision = 9, scale = 2, nullable = false)
    private BigDecimal tax = BigDecimal.ZERO;

    public Bookmaker() {
    }

    public Bookmaker(String webPageUrl,String label) {
        this.webPageUrl = webPageUrl;
        this.label = label;
    }

    public Bookmaker(String webPageUrl, String label, List<BookmakerName> names) {
        this.webPageUrl = webPageUrl;
        this.label = label;
        this.names = names;
    }

    public List<BookmakerName> getNames() {
        return names;
    }

    public void setNames(List<BookmakerName> names) {
        this.names = names;
    }

    public String getName(String systemName) {
        return getNames().stream().filter(n -> systemName.equals(n.getSystem())).findFirst().get().getName();
    }

    public BigDecimal getMinimalAmountToBet() {
        return minimalAmountToBet;
    }

    public void setMinimalAmountToBet(BigDecimal minimalAmountToBet) {
        this.minimalAmountToBet = minimalAmountToBet;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWebPageUrl() {
        return webPageUrl;
    }

    public void setWebPageUrl(String webPageUrl) {
        this.webPageUrl = webPageUrl;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bookmaker bookmaker = (Bookmaker) o;

        return label != null ? label.equals(bookmaker.label) : bookmaker.label == null;

    }

    @Override
    public int hashCode() {
        return (label != null ? label.hashCode() : 0);

    }

    @Override
    public String toString() {
        return label;
    }

}
