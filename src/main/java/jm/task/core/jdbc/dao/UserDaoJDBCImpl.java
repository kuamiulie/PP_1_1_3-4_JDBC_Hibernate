package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public void createUsersTable() {
        try (Connection connection = Util.establishConnection()) {
            Statement createStatement = connection.createStatement();
            createStatement.execute("create table if not exists `usersdb_jdbc` ( id bigint(3) primary key auto_increment, name varchar(20)," +
                    " lastname varchar(20), age tinyint(20) );");
        } catch (SQLException | IOException e) {
            System.out.println("A TABLE ALREADY EXISTS");
            e.printStackTrace();
        }
    }
    public void dropUsersTable() {
        try (Connection connection = Util.establishConnection()) {
            Statement createStatement = connection.createStatement();
            createStatement.execute("DROP TABLE if exists usersdb_jdbc");
        } catch (SQLException | IOException e) {
            System.out.println("A TABLE DOESN'T EXIST");
            e.printStackTrace();
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.establishConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO usersdb_jdbc (name, lastname, age) VALUES(?,?,?)");
            preparedStatement.execute("use usersdb;");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) {
        try (Connection connection = Util.establishConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(" DELETE FROM usersdb_jdbc WHERE id = ?");
            preparedStatement.execute("use usersdb;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        try (Connection connection = Util.establishConnection()) {
            Statement createStatement = connection.createStatement();
            ResultSet rs = createStatement.executeQuery("SELECT * FROM usersdb_jdbc");
            while(rs.next()) {
                String name = rs.getString(2);
                String lastname = rs.getString(3);
                int age = rs.getInt(4);
                User user = new User(name, lastname, (byte) age);
                allUsers.add(user);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        for (User user : allUsers) {
            System.out.println(user.toString());
        }
        return allUsers;
    }
    public void cleanUsersTable() {
        try (Connection connection = Util.establishConnection()) {
            Statement createStatement = connection.createStatement();
            createStatement.execute("TRUNCATE TABLE usersdb_jdbc");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}




