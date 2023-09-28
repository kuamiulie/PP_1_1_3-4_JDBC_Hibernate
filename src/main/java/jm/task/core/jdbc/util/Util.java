package jm.task.core.jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;

public class Util {
    public static Connection establishConnection() throws SQLException, IOException {
        BlockingDeque<Connection> pool = null;
        //Connection connection = pool.take();
        Properties Props = new Properties();

        Connection connection = null;
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();//использование ДРАЙВЕРА обеспечивает совместимость кода с БД при смене SQLБД.
            DriverManager.registerDriver(driver);// То есть Драйвер унифицирует код для всех видов БД. Для каждой БД существует свой JDBC драйвер
            connection = DriverManager.getConnection(Props.getProperty("url"),Props.getProperty("username"),Props.getProperty("password"));
            if (!connection.isClosed()) {
                System.out.println("Соединение установлено");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ДРАЙВЕР НЕ УСТАНОВИЛСЯ");
        }
        return connection;

    }
}
