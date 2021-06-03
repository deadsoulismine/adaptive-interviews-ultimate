package com.smartech.i2019.adaptiveinterviews.dao;

import com.smartech.i2019.adaptiveinterviews.api.EmployeeDao;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public EmployeeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Employee employee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(employee);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Employee employee, int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee1 = session.get(Employee.class, id);
        employee1.setFirstName(employee.getFirstName());
        employee1.setLastName(employee.getLastName());
        employee1.setDepartment(employee.getDepartment());
        employee1.setEmploymentDate(employee.getEmploymentDate());
        employee1.setEndOfAdaptation(employee.getEndOfAdaptation());
        employee1.setPosition(employee.getPosition());
        employee1.setStatus(employee.getStatus());
        session.update(employee1);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.delete(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Employee getById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.getTransaction().commit();
        session.close();
        return employee;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> getByLastName(String lastName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Employee where lastName = :paramName");
        query.setParameter("paramName", lastName);
        List<Employee> employees = query.list();
        session.getTransaction().commit();
        session.close();
        return employees;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> list() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Employee> employees = session.createQuery("FROM Employee").list();
        session.getTransaction().commit();
        session.close();
        return employees;
    }

    @Override
    public List<Employee> listForNewInterview() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Employee where status = :paramName");
        query.setParameter("paramName", "Проходит адаптацию");
        List<Employee> employees = query.list();
        session.getTransaction().commit();
        session.close();
        return employees;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> listByLastName(String lastName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Employee where lastName like :paramName");
        query.setParameter("paramName", "%" + lastName + "%");
        List<Employee> employees = query.list();
        session.getTransaction().commit();
        session.close();
        return employees;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> listInAdaptation() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Employee where status = :paramName");
        query.setParameter("paramName", "Проходит адаптацию");
        List<Employee> employees = query.list();
        session.getTransaction().commit();
        session.close();
        return employees;
    }

    @Override
    public Set<Interview> getInterviews(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        Set<Interview> interviews = employee.getInterviews();
        session.getTransaction().commit();
        session.close();
        return interviews;
    }

    @Override
    public Employee getByName(String firstName, String lastName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Employee.class);
        Employee employee = (Employee) criteria.add(Restrictions.and(
                Restrictions.eq("firstName", firstName),
                Restrictions.eq("lastName", lastName)
        )).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return employee;
    }
}
