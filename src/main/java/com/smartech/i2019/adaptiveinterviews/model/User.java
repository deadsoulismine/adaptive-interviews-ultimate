package com.smartech.i2019.adaptiveinterviews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<Interview> interviews = new HashSet<>();

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
//    public Set<Interview> getInterviews() {
//        Iterator iterator = interviews.iterator();
//        while (iterator.hasNext()) {
//            Hibernate.unproxy(iterator.next());
//        }
//        return interviews;
//    }
//
//    public void setInterviews(Set<Interview> interviews) {
//        this.interviews = interviews;
//    }

}
