package com.smartech.i2019.adaptiveinterviews.model;

import lombok.Data;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
//@Data
@Table(name = "files")
public class UploadFile {
    private String fileName;
    private long id;
    private byte[] data;
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable  = false)
    public Employee getEmployee() {
        return (Employee) Hibernate.unproxy(employee);
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Column(name = "file_name", nullable = false)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "file_data", nullable = false)
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
