package com.smartech.i2019.adaptiveinterviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "position", nullable = false)
    private String position;
    @JoinTable(name = "users_interviews",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interview_id"))
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonIgnore
    private List<Interview> interviews = new ArrayList<>();
}
