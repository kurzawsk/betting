package com.kk.betting.domain;

import com.kk.betting.domain.converter.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "MATCH_ODD", indexes = {
        @Index(columnList = "MATCH_ID")})
public class MatchOdd extends ManagedEntityImp implements Serializable {

    private static final long serialVersionUID = -1L;

    public enum Type {
        ODD1("1"),
        ODD2("2"),
        ODDX("X"),
        ODD1X("1X"),
        ODD12("12"),
        ODDX2("X2"),
        ODDBTSY("BTS"),
        ODDBTSN("BTSN"),
        ODDO05("Ov 0.5"),
        ODDO15("Ov 1.5"),
        ODDO25("Ov 2.5"),
        ODDO35("Ov 3.5"),
        ODDO45("Ov 4.5"),
        ODDO55("Ov 5.5"),
        ODDO65("Ov 6.5"),
        ODDU05("Un 0.5"),
        ODDU15("Un 1.5"),
        ODDU25("Un 2.5"),
        ODDU35("Un 3.5"),
        ODDU45("Un 4.5"),
        ODDU55("Un 5.5"),
        ODDU65("Un 6.5");

        private String label;
        Type(String label){
            this.label = label;
        }

        public String getLabel(){
            return label;
        }
    }

    public enum SupportedOU {
        _05("0.5"), _15("1.5"), _25("2.5"), _35("3.5"), _45("4.5"), _55("5.5"), _65("6.5");

        private String text;

        SupportedOU(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    @ManyToOne(targetEntity = Match.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCH_ID", nullable = false)
    private Match match;

    @Column(name = "TIMESTAMP", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime timestamp;

    @OneToOne(targetEntity = Bookmaker.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOKMAKER_ID", nullable = false)
    private Bookmaker bookmaker;

    @Column(name = "ODD_1", precision = 9, scale = 2, nullable = false)
    private BigDecimal odd1;

    @Column(name = "ODD_2", precision = 9, scale = 2, nullable = false)
    private BigDecimal odd2;

    @Column(name = "ODD_X", precision = 9, scale = 2, nullable = false)
    private BigDecimal oddX;

    @Column(name = "ODD_1X", precision = 9, scale = 2, nullable = true)
    private BigDecimal odd1X;

    @Column(name = "ODD_12", precision = 9, scale = 2, nullable = true)
    private BigDecimal odd12;

    @Column(name = "ODD_X2", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddX2;

    @Column(name = "ODD_BTS_Y", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddBTSY;

    @Column(name = "ODD_BTS_N", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddBTSN;

    @Column(name = "ODD_O_05", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddO05;

    @Column(name = "ODD_O_15", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddO15;

    @Column(name = "ODD_O_25", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddO25;

    @Column(name = "ODD_O_35", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddO35;

    @Column(name = "ODD_O_45", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddO45;

    @Column(name = "ODD_O_55", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddO55;

    @Column(name = "ODD_O_65", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddO65;

    @Column(name = "ODD_U_05", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddU05;

    @Column(name = "ODD_U_15", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddU15;

    @Column(name = "ODD_U_25", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddU25;

    @Column(name = "ODD_U_35", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddU35;

    @Column(name = "ODD_U_45", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddU45;

    @Column(name = "ODD_U_55", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddU55;

    @Column(name = "ODD_U_65", precision = 9, scale = 2, nullable = true)
    private BigDecimal oddU65;


    public MatchOdd() {

    }


    public MatchOdd(Match match, LocalDateTime timestamp, Bookmaker bookmaker, BigDecimal odd1, BigDecimal odd2, BigDecimal oddX, BigDecimal odd1X, BigDecimal odd12, BigDecimal oddX2, BigDecimal oddBTSY, BigDecimal oddBTSN, BigDecimal oddO05, BigDecimal oddO15, BigDecimal oddO25, BigDecimal oddO35, BigDecimal oddO45, BigDecimal oddO55, BigDecimal oddO65, BigDecimal oddU05, BigDecimal oddU15, BigDecimal oddU25, BigDecimal oddU35, BigDecimal oddU45, BigDecimal oddU55, BigDecimal oddU65) {
        this.match = match;
        this.timestamp = timestamp;
        this.bookmaker = bookmaker;
        this.odd1 = odd1;
        this.odd2 = odd2;
        this.oddX = oddX;
        this.odd1X = odd1X;
        this.odd12 = odd12;
        this.oddX2 = oddX2;
        this.oddBTSY = oddBTSY;
        this.oddBTSN = oddBTSN;
        this.oddO05 = oddO05;
        this.oddO15 = oddO15;
        this.oddO25 = oddO25;
        this.oddO35 = oddO35;
        this.oddO45 = oddO45;
        this.oddO55 = oddO55;
        this.oddO65 = oddO65;
        this.oddU05 = oddU05;
        this.oddU15 = oddU15;
        this.oddU25 = oddU25;
        this.oddU35 = oddU35;
        this.oddU45 = oddU45;
        this.oddU55 = oddU55;
        this.oddU65 = oddU65;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Bookmaker getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(Bookmaker bookmaker) {
        this.bookmaker = bookmaker;
    }

    public BigDecimal getOdd(Type type) {

        switch (type) {
            case ODD1:
                return odd1;
            case ODDX:
                return oddX;
            case ODD2:
                return odd2;
            case ODD1X:
                return odd1X;
            case ODDX2:
                return oddX2;
            case ODD12:
                return odd12;
            case ODDBTSY:
                return oddBTSY;
            case ODDBTSN:
                return oddBTSN;
            case ODDO05:
                return oddO05;
            case ODDU05:
                return oddU05;
            case ODDO15:
                return oddO15;
            case ODDU15:
                return oddU15;
            case ODDO25:
                return oddO25;
            case ODDU25:
                return oddU25;
            case ODDO35:
                return oddO35;
            case ODDU35:
                return oddU35;
            case ODDO45:
                return oddO45;
            case ODDU45:
                return oddU45;
            case ODDO55:
                return oddO55;
            case ODDU55:
                return oddU55;
            case ODDO65:
                return oddO65;
            case ODDU65:
                return oddU65;

        }

        return null;
    }

    public void setOdd(Type type, BigDecimal value) {
        switch (type) {
            case ODD1:
                odd1 = value;
            case ODDX:
                oddX = value;
            case ODD2:
                odd2 = value;
            case ODD1X:
                odd1X = value;
            case ODDX2:
                oddX2 = value;
            case ODD12:
                odd12 = value;
            case ODDBTSY:
                oddBTSY = value;
            case ODDBTSN:
                oddBTSN = value;
            case ODDO05:
                oddO05 = value;
            case ODDU05:
                oddU05 = value;
            case ODDO15:
                oddO15 = value;
            case ODDU15:
                oddU15 = value;
            case ODDO25:
                oddO25 = value;
            case ODDU25:
                oddU25 = value;
            case ODDO35:
                oddO35 = value;
            case ODDU35:
                oddU35 = value;
            case ODDO45:
                oddO45 = value;
            case ODDU45:
                oddU45 = value;
            case ODDO55:
                oddO55 = value;
            case ODDU55:
                oddU55 = value;
            case ODDO65:
                oddO65 = value;
            case ODDU65:
                oddU65 = value;
        }

    }

    public BigDecimal getOdd1() {
        return odd1;
    }

    public void setOdd1(BigDecimal odd1) {
        this.odd1 = odd1;
    }

    public BigDecimal getOdd2() {
        return odd2;
    }

    public void setOdd2(BigDecimal odd2) {
        this.odd2 = odd2;
    }

    public BigDecimal getOddX() {
        return oddX;
    }

    public void setOddX(BigDecimal oddX) {
        this.oddX = oddX;
    }

    public BigDecimal getOdd1X() {
        return odd1X;
    }

    public void setOdd1X(BigDecimal odd1X) {
        this.odd1X = odd1X;
    }

    public BigDecimal getOdd12() {
        return odd12;
    }

    public void setOdd12(BigDecimal odd12) {
        this.odd12 = odd12;
    }

    public BigDecimal getOddX2() {
        return oddX2;
    }

    public void setOddX2(BigDecimal oddX2) {
        this.oddX2 = oddX2;
    }

    public BigDecimal getOddBTSY() {
        return oddBTSY;
    }

    public void setOddBTSY(BigDecimal oddBTSY) {
        this.oddBTSY = oddBTSY;
    }

    public BigDecimal getOddBTSN() {
        return oddBTSN;
    }

    public void setOddBTSN(BigDecimal oddBTSN) {
        this.oddBTSN = oddBTSN;
    }

    public BigDecimal getOddO05() {
        return oddO05;
    }

    public void setOddO05(BigDecimal oddO05) {
        this.oddO05 = oddO05;
    }

    public BigDecimal getOddO15() {
        return oddO15;
    }

    public void setOddO15(BigDecimal oddO15) {
        this.oddO15 = oddO15;
    }

    public BigDecimal getOddO25() {
        return oddO25;
    }

    public void setOddO25(BigDecimal oddO25) {
        this.oddO25 = oddO25;
    }

    public BigDecimal getOddO35() {
        return oddO35;
    }

    public void setOddO35(BigDecimal oddO35) {
        this.oddO35 = oddO35;
    }

    public BigDecimal getOddO45() {
        return oddO45;
    }

    public void setOddO45(BigDecimal oddO45) {
        this.oddO45 = oddO45;
    }

    public BigDecimal getOddO55() {
        return oddO55;
    }

    public void setOddO55(BigDecimal oddO55) {
        this.oddO55 = oddO55;
    }

    public BigDecimal getOddO65() {
        return oddO65;
    }

    public void setOddO65(BigDecimal oddO65) {
        this.oddO65 = oddO65;
    }

    public BigDecimal getOddU05() {
        return oddU05;
    }

    public void setOddU05(BigDecimal oddU05) {
        this.oddU05 = oddU05;
    }

    public BigDecimal getOddU15() {
        return oddU15;
    }

    public void setOddU15(BigDecimal oddU15) {
        this.oddU15 = oddU15;
    }

    public BigDecimal getOddU25() {
        return oddU25;
    }

    public void setOddU25(BigDecimal oddU25) {
        this.oddU25 = oddU25;
    }

    public BigDecimal getOddU35() {
        return oddU35;
    }

    public void setOddU35(BigDecimal oddU35) {
        this.oddU35 = oddU35;
    }

    public BigDecimal getOddU45() {
        return oddU45;
    }

    public void setOddU45(BigDecimal oddU45) {
        this.oddU45 = oddU45;
    }

    public BigDecimal getOddU55() {
        return oddU55;
    }

    public void setOddU55(BigDecimal oddU55) {
        this.oddU55 = oddU55;
    }

    public BigDecimal getOddU65() {
        return oddU65;
    }

    public void setOddU65(BigDecimal oddU65) {
        this.oddU65 = oddU65;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MatchOdd that = (MatchOdd) o;

        if (match != null ? !match.equals(that.match) : that.match != null) return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null && (bookmaker != null ? bookmaker.equals(that.bookmaker) : that.bookmaker == null);

    }

    @Override
    public int hashCode() {
        return Objects.hash(match, timestamp, bookmaker);
    }

    @Override
    public String toString() {
        return "MatchOdd{" +
                "match=" + match +
                ", startTime=" + timestamp +
                ", bookmaker=" + bookmaker +
                ", odd1=" + odd1 +
                ", odd2=" + odd2 +
                ", oddX=" + oddX +
                ", odd1X=" + odd1X +
                ", odd12=" + odd12 +
                ", oddX2=" + oddX2 +
                ", oddBTSY=" + oddBTSY +
                ", oddBTSN=" + oddBTSN +
                ", oddO05=" + oddO05 +
                ", oddO15=" + oddO15 +
                ", oddO25=" + oddO25 +
                ", oddO35=" + oddO35 +
                ", oddO45=" + oddO45 +
                ", oddO55=" + oddO55 +
                ", oddO65=" + oddO65 +
                ", oddU05=" + oddU05 +
                ", oddU15=" + oddU15 +
                ", oddU25=" + oddU25 +
                ", oddU35=" + oddU35 +
                ", oddU45=" + oddU45 +
                ", oddU55=" + oddU55 +
                ", oddU65=" + oddU65 +
                '}';
    }

    public static class MatchOddBuilder {
        private Match match;
        private LocalDateTime timestamp;
        private Bookmaker bookmaker;
        private BigDecimal odd1;
        private BigDecimal odd2;
        private BigDecimal oddX;
        private BigDecimal odd1X;
        private BigDecimal odd12;
        private BigDecimal oddX2;
        private BigDecimal oddBTSY;
        private BigDecimal oddBTSN;
        private BigDecimal oddO05;
        private BigDecimal oddO15;
        private BigDecimal oddO25;
        private BigDecimal oddO35;
        private BigDecimal oddO45;
        private BigDecimal oddO55;
        private BigDecimal oddO65;
        private BigDecimal oddU05;
        private BigDecimal oddU15;
        private BigDecimal oddU25;
        private BigDecimal oddU35;
        private BigDecimal oddU45;
        private BigDecimal oddU55;
        private BigDecimal oddU65;

        public MatchOddBuilder setMatch(Match match) {
            this.match = match;
            return this;
        }

        public MatchOddBuilder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public MatchOddBuilder setBookmaker(Bookmaker bookmaker) {
            this.bookmaker = bookmaker;
            return this;
        }

        public MatchOddBuilder setOdd1(BigDecimal odd1) {
            this.odd1 = odd1;
            return this;
        }

        public MatchOddBuilder setOdd2(BigDecimal odd2) {
            this.odd2 = odd2;
            return this;
        }

        public MatchOddBuilder setOddX(BigDecimal oddX) {
            this.oddX = oddX;
            return this;
        }

        public MatchOddBuilder setOdd1X(BigDecimal odd1X) {
            this.odd1X = odd1X;
            return this;
        }

        public MatchOddBuilder setOdd12(BigDecimal odd12) {
            this.odd12 = odd12;
            return this;
        }

        public MatchOddBuilder setOddX2(BigDecimal oddX2) {
            this.oddX2 = oddX2;
            return this;
        }

        public MatchOddBuilder setOddBTSY(BigDecimal oddBTSY) {
            this.oddBTSY = oddBTSY;
            return this;
        }

        public MatchOddBuilder setOddBTSN(BigDecimal oddBTSN) {
            this.oddBTSN = oddBTSN;
            return this;
        }

        public MatchOddBuilder setOddO05(BigDecimal oddO05) {
            this.oddO05 = oddO05;
            return this;
        }

        public MatchOddBuilder setOddO15(BigDecimal oddO15) {
            this.oddO15 = oddO15;
            return this;
        }

        public MatchOddBuilder setOddO25(BigDecimal oddO25) {
            this.oddO25 = oddO25;
            return this;
        }

        public MatchOddBuilder setOddO35(BigDecimal oddO35) {
            this.oddO35 = oddO35;
            return this;
        }

        public MatchOddBuilder setOddO45(BigDecimal oddO45) {
            this.oddO45 = oddO45;
            return this;
        }

        public MatchOddBuilder setOddO55(BigDecimal oddO55) {
            this.oddO55 = oddO55;
            return this;
        }

        public MatchOddBuilder setOddO65(BigDecimal oddO65) {
            this.oddO65 = oddO65;
            return this;
        }

        public MatchOddBuilder setOddU05(BigDecimal oddU05) {
            this.oddU05 = oddU05;
            return this;
        }

        public MatchOddBuilder setOddU15(BigDecimal oddU15) {
            this.oddU15 = oddU15;
            return this;
        }

        public MatchOddBuilder setOddU25(BigDecimal oddU25) {
            this.oddU25 = oddU25;
            return this;
        }

        public MatchOddBuilder setOddU35(BigDecimal oddU35) {
            this.oddU35 = oddU35;
            return this;
        }

        public MatchOddBuilder setOddU45(BigDecimal oddU45) {
            this.oddU45 = oddU45;
            return this;
        }

        public MatchOddBuilder setOddU55(BigDecimal oddU55) {
            this.oddU55 = oddU55;
            return this;
        }

        public MatchOddBuilder setOddU65(BigDecimal oddU65) {
            this.oddU65 = oddU65;
            return this;
        }

        public MatchOdd createMatchOdd() {
            return new MatchOdd(match, timestamp, bookmaker, odd1, odd2, oddX, odd1X, odd12, oddX2, oddBTSY, oddBTSN, oddO05, oddO15, oddO25, oddO35, oddO45, oddO55, oddO65, oddU05, oddU15, oddU25, oddU35, oddU45, oddU55, oddU65);
        }
    }
}
