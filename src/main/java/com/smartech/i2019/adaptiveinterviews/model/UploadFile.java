package com.smartech.i2019.adaptiveinterviews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@Table(name = "files")
public class UploadFile {
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "file_data", nullable = false)
    private byte[] data;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable  = false)
    private Employee employee;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id", nullable  = false)
//    public Employee getEmployee() {
//        return (Employee) Hibernate.unproxy(employee);
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
}
