package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private Util() {

    }

    private static Connection connection;

    public static Connection establishConnection() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/database.properties")) {
            prop.load(fis);
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager
                    .getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
        } catch (SQLException | IOException e) {
            System.out.println("Connection was not established");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

