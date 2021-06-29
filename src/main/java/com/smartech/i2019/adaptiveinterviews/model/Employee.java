package com.smartech.i2019.adaptiveinterviews.model;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
//@Data
@Table(name = "employees")
public class Employee {
    private long id;
    private String firstName;
    private String lastName;
    private Department department;
    private Date employmentDate;
    private Date endOfAdaptation;
    private String position;
    private String status;
    private Set<UploadFile> files = new HashSet<>();
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Interview> interviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", orphanRemoval = true)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", orphanRemoval = true)
    public Set<UploadFile> getFiles() {
        Iterator iterator = files.iterator();
        while (iterator.hasNext()) {
            Hibernate.unproxy(iterator.next());
        }
        return files;
    }

    public void setFiles(Set<UploadFile> files) {
        this.files = files;
    }

    @Column(name = "employment_date", nullable = false)

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Column(name = "end_of_adaptation")
    public Date getEndOfAdaptation() {
        return endOfAdaptation;
    }

    public void setEndOfAdaptation(Date endOfAdaptation) {
        this.endOfAdaptation = endOfAdaptation;
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

    @Column(name = "firstname", nullable = false)

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastname", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToOne
    @JoinColumn(name = "department")
    public Department getDepartment() {
        return (Department) Hibernate.unproxy(department);
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Column(name = "position", nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
