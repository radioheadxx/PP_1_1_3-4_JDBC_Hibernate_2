package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    SessionFactory sessionFactory = getSessionFactory();


    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            Query query = session.createQuery(sql).addEntity(User.class);

            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS users";

            Query query = session.createQuery(sql).addEntity(User.class);

            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        User user = new User(name,lastName, age);

        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            users = session.createQuery("from User")
                    .getResultList();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User");
            session.getTransaction().commit();
        }
    }
}
