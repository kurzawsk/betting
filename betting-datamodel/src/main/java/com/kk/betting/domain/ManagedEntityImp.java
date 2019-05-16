package com.kk.betting.domain;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class ManagedEntityImp implements ManagedEntity<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_int")
    @SequenceGenerator(name = "seq_int", sequenceName = "seq_int", allocationSize = 1)
    @Column(name = "ID")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ManagedEntityImp that = (ManagedEntityImp) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ", id: " + getId();
    }
}
