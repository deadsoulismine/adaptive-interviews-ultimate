package com.smartech.i2019.adaptiveinterviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "firstname", nullable = false)
    private String firstName;
    @Column(name = "lastname", nullable = false)
    private String lastName;
    @Column(name = "employment_date", nullable = false)
    private Date employmentDate;
    @Column(name = "end_of_adaptation")
    private Date endOfAdaptation;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private Department department;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", orphanRemoval = true)
    @ToString.Exclude
    private List<UploadFile> files = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Interview> interviews = new ArrayList<>();

}

