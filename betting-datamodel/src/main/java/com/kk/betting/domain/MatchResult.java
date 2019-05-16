package com.kk.betting.domain;

import com.google.common.collect.Sets;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KK on 2017-02-17.
 */
@Embeddable
@Access(value = AccessType.PROPERTY)
public class MatchResult {

    private static final String delimiters = ":-";
    private static final Pattern pattern = Pattern.compile("(-{0,1}\\d+?)[" + delimiters + "](-{0,1}\\d+?)");
    public static final MatchResult EMPTY_VALUE = MatchResult.fromScores(-1, -1);

    public enum Type {
        NORMAL(0, "Normal"), UNKNOWN(-1, "Unknown"), POSTPONED(-2, "Postponed"), CANCELLED(-3, "Cancelled"), NOT_FOUND(-4, "Not found");

        public static final Set<Type> FORMALLY_ANNULLED = Sets.immutableEnumSet(POSTPONED, CANCELLED);
        public static final Set<Type> NO_SCORES = Sets.immutableEnumSet(UNKNOWN, POSTPONED, CANCELLED, NOT_FOUND);

        private int code;
        private String label;

        Type(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public int getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }
    }

    @Column(name = "HOME_SCORE")
    private Short homeScore;

    @Column(name = "AWAY_SCORE")
    private Short awayScore;


    public MatchResult() {
    }

    private MatchResult(Short homeScore, Short awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public static MatchResult fromString(String result) {
        Matcher matcher = pattern.matcher(result.replaceAll("\\s", ""));
        if (matcher.matches()) {
            return fromScores(matcher.group(1), matcher.group(2));
        } else {
            throw new RuntimeException("Cannot create MatchResult from String: " + result);
        }
    }

    public static MatchResult fromScores(String homeScore, String awayScore) {
        return fromScores(Integer.parseInt(homeScore), Integer.parseInt(awayScore));
    }

    public static MatchResult fromScores(Number homeScore, Number awayScore) {
        return new MatchResult(homeScore.shortValue(), awayScore.shortValue());
    }

    public static MatchResult fromType(Type type) {
        if (Type.NO_SCORES.contains(type)) {
            return fromScores(type.getCode(), type.getCode());
        }
        throw new IllegalArgumentException("Cannot create MatchResult from type: " + type);
    }

    public Short getHomeScore() {
        return homeScore;
    }

    public Short getAwayScore() {
        return awayScore;
    }

    public void setHomeScore(Short homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(Short awayScore) {
        this.awayScore = awayScore;
    }

    public String toScores() {
        return homeScore + ":" + awayScore;
    }

    public Type asType() {

        if (!Objects.isNull(homeScore) && !Objects.isNull(awayScore)) {
            if (homeScore >= 0 && awayScore >= 0) {
                return Type.NORMAL;
            } else if (Type.POSTPONED.getCode() == homeScore.intValue() && Type.POSTPONED.getCode() == awayScore.intValue()) {
                return Type.POSTPONED;
            } else if (Type.NOT_FOUND.getCode() == homeScore.intValue() && Type.NOT_FOUND.getCode() == awayScore.intValue()) {
                return Type.NOT_FOUND;
            } else if (Type.CANCELLED.getCode() == homeScore.intValue() && Type.CANCELLED.getCode() == awayScore.intValue()) {
                return Type.CANCELLED;
            } else if (Type.UNKNOWN.getCode() == homeScore.intValue() && Type.UNKNOWN.getCode() == awayScore.intValue()) {
                return Type.UNKNOWN;
            }
        } else if (Objects.isNull(homeScore) && Objects.isNull(awayScore)) {
            return Type.UNKNOWN;
        }
        throw new RuntimeException("Cannot evaluate type from scores: " + homeScore + ", " + awayScore);
    }

    public String toCleanFormat() {
        if (asType() == Type.NORMAL) {
            return toScores();
        } else {
            return asType().getLabel();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchResult)) return false;

        MatchResult that = (MatchResult) o;

        return homeScore != null ? homeScore.equals(that.homeScore) : that.homeScore == null && (awayScore != null ? awayScore.equals(that.awayScore) : that.awayScore == null);

    }

    @Override
    public int hashCode() {
        return Objects.hash(homeScore, awayScore);
    }

    @Override
    public String toString() {
        return toScores();
    }
}