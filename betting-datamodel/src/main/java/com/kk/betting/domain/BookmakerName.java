package com.kk.betting.domain;

import javax.persistence.*;

/**
 * Created by KK on 2017-07-19.
 */

@Entity
@Table(name = "BOOKMAKER_NAME")
public class BookmakerName extends ManagedEntityImp {

    @OneToOne(targetEntity = Bookmaker.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOKMAKER_ID", nullable = false)
    private Bookmaker bookmaker;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "SYSTEM", nullable = false,  length = 50)
    private String system;

    public BookmakerName() {

    }

    public BookmakerName(Bookmaker bookmaker, String name, String system) {
        this.bookmaker = bookmaker;
        this.name = name;
        this.system = system;
    }

    public Bookmaker getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(Bookmaker bookmaker) {
        this.bookmaker = bookmaker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
