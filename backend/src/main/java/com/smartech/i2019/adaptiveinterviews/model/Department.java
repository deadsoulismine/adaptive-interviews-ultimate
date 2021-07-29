package com.smartech.i2019.adaptiveinterviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "supervisor")
    @NotBlank(message = "Укажите начальника отдела")
    private String supervisor;
    @Column(name = "name")
    @NotBlank(message = "Укажите название отдела")
    private String name;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Employee> employeeSet = new ArrayList<>();
}
