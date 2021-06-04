package com.smartech.i2019.adaptiveinterviews.dao;

import com.smartech.i2019.adaptiveinterviews.api.InterviewDao;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.util.Dates;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class InterviewDaoImpl implements InterviewDao {
    private final SessionFactory sessionFactory;
    @Autowired
    private Dates dates;

    @Autowired
    public InterviewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Interview interview) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(interview);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Interview interview, int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Interview interview1 = session.get(Interview.class, id);
        interview1.setDescription(interview.getDescription());
        interview1.setEmployee(interview.getEmployee());
        interview1.setUser(interview.getUser());
        interview1.setDate(interview.getDate());
        interview1.setName(interview.getName());
        session.update(interview1);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Interview interview = session.get(Interview.class, id);
        session.delete(interview);
        transaction.commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> listByEmployee(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Interview> interviews = session.createQuery("FROM Interview where employee = " + id).list();
        transaction.commit();
        session.close();
        return interviews;
    }

    @Override
    public List<Interview> listByDateNextDay() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Interview where date = :paramName");
        query.setParameter("paramName", dates.getSqlNextDay());
        List<Interview> interviews = query.list();
        transaction.commit();
        session.close();
        return interviews;
    }

    @Override
    public List<Interview> listByDateSubtractDay() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Interview where date = :paramName");
        query.setParameter("paramName", dates.getSqlSubtractDay());
        List<Interview> interviews = query.list();
        transaction.commit();
        session.close();
        return interviews;
    }

    @Override
    public List<Interview> listByDate(Date date) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Interview where date = :paramName");
        query.setParameter("paramName", date);
        List<Interview> interviews = query.list();
        transaction.commit();
        session.close();
        return interviews;
    }

    @Override
    public List<Interview> list() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Interview> interviews = session.createQuery("FROM Interview ").list();
        transaction.commit();
        session.close();
        return interviews;
    }

    @Override
    public List<Interview> listTodayAndAfter() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Interview where date >= :paramName and description = ''");
        query.setParameter("paramName", dates.getSqlToday());
        List<Interview> interviews = query.list();
        transaction.commit();
        session.close();
        return interviews;
    }

    @Override
    public List<Interview> listWithoutReview() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Interview where description = ''");
        List<Interview> interviews = query.list();
        transaction.commit();
        session.close();
        return interviews;
    }

    @Override
    public Interview getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Interview interview = session.get(Interview.class, id);
        transaction.commit();
        session.close();
        return interview;
    }

    @Autowired
    public Dates getDates() {
        return dates;
    }
}
