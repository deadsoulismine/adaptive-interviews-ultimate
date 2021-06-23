package com.smartech.i2019.adaptiveinterviews.dao;

import com.smartech.i2019.adaptiveinterviews.api.UploadFileDao;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UploadFileDaoImpl implements UploadFileDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public UploadFileDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(UploadFile file) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(file);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        UploadFile file = session.get(UploadFile.class, id);
        session.delete(file);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public UploadFile getById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        UploadFile uploadFile = session.get(UploadFile.class, id);
        session.getTransaction().commit();
        session.close();
        return uploadFile;
    }

    @Override
    public List<UploadFile> getByEmployee(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<UploadFile> uploadFiles = session.createQuery(
                "FROM UploadFile " + " where employee.id = " + id).list();
        session.getTransaction().commit();
        session.close();
        return uploadFiles;
    }
}
