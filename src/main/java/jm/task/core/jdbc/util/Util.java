package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection connection;
    public static Connection establishConnection() throws SQLException, IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/java/jm/task/core/jdbc/util/Properties.properties");
        prop.load(fis);
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            try {
                connection = DriverManager
                        .getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("DataBase was not created");
            }
        } catch (SQLException e) {
            System.out.println("Driver was not found");
        }
        return connection;
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
