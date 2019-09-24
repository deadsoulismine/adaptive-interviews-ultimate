package com.smartech.i2019.adaptiveinterviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {
    private int id;
    private String supervisor;
    private String name;
    private Set<Employee> employeeSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    @JsonIgnore
    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "supervisor")
    @NotBlank(message = "Укажите начальника отдела")
    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Column(name = "name")
    @NotBlank(message = "Укажите название отдела")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
