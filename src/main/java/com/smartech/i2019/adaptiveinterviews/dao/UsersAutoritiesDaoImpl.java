package com.smartech.i2019.adaptiveinterviews.dao;

import com.smartech.i2019.adaptiveinterviews.api.UsersAutoritiesDao;
import com.smartech.i2019.adaptiveinterviews.model.UserAutorities;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersAutoritiesDaoImpl implements UsersAutoritiesDao {
    private SessionFactory sessionFactory;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersAutoritiesDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserAutorities findByUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(UserAutorities.class);
        UserAutorities userAutorities = (UserAutorities) criteria.add(Restrictions.eq("username", username))
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return userAutorities;
    }

    @Override
    public List<UserAutorities> list() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<UserAutorities> users = session.createQuery("FROM UserAutorities").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }
    @Override
    public void add(UserAutorities user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(UserAutorities user, int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        UserAutorities user1 = session.get(UserAutorities.class, id);
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setUsername(user.getUsername());
        user1.setRole(user.getRole());
        session.update(user1);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        UserAutorities user = session.get(UserAutorities.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}
