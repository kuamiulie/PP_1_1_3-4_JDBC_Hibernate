package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.establishConnection();

    @Override
    public void createUsersTable() {
        try {
            Statement createStatement = connection.createStatement();
            createStatement.execute("create table if not exists `usersdb_jdbc` ( id bigint(3) primary key auto_increment, name varchar(20)," +
                    " lastname varchar(20), age tinyint(20) );");
        } catch (SQLException e) {
            System.out.println("A TABLE ALREADY EXISTS");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Statement createStatement = connection.createStatement();
            createStatement.execute("DROP TABLE if exists usersdb_jdbc");
        } catch (SQLException e) {
            System.out.println("A TABLE DOESN'T EXIST");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO usersdb_jdbc (name, lastname, age) VALUES(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement =
                    connection.prepareStatement(" DELETE FROM usersdb_jdbc WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        try (Connection connection = Util.establishConnection()) {
            Statement createStatement = connection.createStatement();
            ResultSet rs = createStatement.executeQuery("SELECT * FROM usersdb_jdbc");
            while (rs.next()) {
                String name = rs.getString(2);
                String lastname = rs.getString(3);
                byte age = rs.getByte(4);
                User user = new User(name, lastname, age);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (User user : allUsers) {
            System.out.println(user.toString());
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Statement createStatement = connection.createStatement();
            createStatement.execute("TRUNCATE TABLE usersdb_jdbc");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




