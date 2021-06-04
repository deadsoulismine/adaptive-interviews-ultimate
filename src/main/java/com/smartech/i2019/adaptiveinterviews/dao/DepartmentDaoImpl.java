package com.smartech.i2019.adaptiveinterviews.dao;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentDao;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public DepartmentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Department department) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(department);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Department department, int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Department department1 = session.get(Department.class, id);
        department1.setName(department.getName());
        department1.setSupervisor(department.getSupervisor());
        session.update(department1);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Department department = session.get(Department.class, id);
        session.delete(department);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Department getByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Department.class);
        Department department = (Department) criteria.add(Restrictions.eq("name", name))
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return department;
    }

    @Override
    public Department getById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Department department = session.get(Department.class, id);
        session.getTransaction().commit();
        session.close();
        return department;
    }

    @Override
    public List<Department> list() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Department> departments = session.createQuery("FROM Department").list();
        session.getTransaction().commit();
        session.close();
        return departments;
    }
}
