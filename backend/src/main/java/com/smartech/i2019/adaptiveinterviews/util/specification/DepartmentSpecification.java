package com.smartech.i2019.adaptiveinterviews.util.specification;

import com.smartech.i2019.adaptiveinterviews.model.Department;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DepartmentSpecification {
    public Specification<Department> hasName(String name) {
        return (department, cq, cb) -> cb.equal(department.get("name"), name);
    }
}
