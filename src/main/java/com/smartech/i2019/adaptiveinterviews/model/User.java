package com.smartech.i2019.adaptiveinterviews.model;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
//@Data
@Table(name = "users")
public class User {
    private String name;
    private String email;
    private long id;
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Interview> interviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    public Set<Interview> getInterviews() {
        Iterator iterator = interviews.iterator();
        while (iterator.hasNext()) {
            Hibernate.unproxy(iterator.next());
        }
        return interviews;
    }

    public void setInterviews(Set<Interview> interviews) {
        this.interviews = interviews;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
