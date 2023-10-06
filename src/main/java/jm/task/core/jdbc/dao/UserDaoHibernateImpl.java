package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {

    }

    {
        try {
            session = Util.getSessionFactoryFromConfig().openSession();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUsersTable() {
        try {
            session.beginTransaction();
            session.createSQLQuery("create table if not exists `users` " +
                    "( id bigint(3) primary key auto_increment," +
                    " name varchar(20)," +
                    " lastname varchar(20)," +
                    " age tinyint(20) )").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = session.createQuery("from User ", User.class).getResultList();
        for (User user : list) {
            System.out.println(user.toString());
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            session.createSQLQuery("delete from users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }
}





