package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String NameTable = "usersdb1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "23676123";
    private static final String URL = "jdbc:mysql://localhost:3306/usersdb";

    public UserDaoJDBCImpl() {


    }

    public void createUsersTable() {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME,PASSWORD))  {
            Statement createStatement = connection.createStatement();
            createStatement.execute("use usersdb;");
            createStatement.execute(" create table `usersdb1` ( id int(3) primary key auto_increment, name varchar(20)," +
                    " lastname varchar(20), age varchar(20) );");
        } catch (SQLException e) {
            System.out.println("A TABLE ALREADY EXISTS");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,PASSWORD)) {
            Statement createStatement = connection.createStatement();
            createStatement.execute("use usersdb;");
            createStatement.execute("DROP TABLE "+ NameTable);
        } catch (SQLException e) {
            System.out.println("A TABLE DOESN'T EXIST");
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,PASSWORD))  {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO "+ NameTable+
                            " (name, lastname, age) "+ " VALUES(?,?,?)");
            preparedStatement.execute("use usersdb;");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем –"+  name+" добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,PASSWORD)) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(" DELETE FROM usersdb1 WHERE id = " + id);
            preparedStatement.execute("use usersdb;");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        ArrayList<User> AllUsers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,PASSWORD)) {
            Statement createStatement = connection.createStatement();
            createStatement.execute("use usersdb;");
            ResultSet rs = createStatement.executeQuery("SELECT * FROM usersdb1");
            while(rs.next()) {
                String name = rs.getString(2);
                String lastname = rs.getString(3);
                int age = rs.getInt(4);
                User user = new User(name, lastname, (byte) age);
                AllUsers.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  AllUsers;
    }

    public void cleanUsersTable() {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME,PASSWORD))  {
            Statement createStatement = connection.createStatement();
            createStatement.execute("use usersdb;");
            createStatement.execute("TRUNCATE TABLE" + " "+ NameTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    }


