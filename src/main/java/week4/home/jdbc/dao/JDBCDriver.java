package week4.home.jdbc.dao;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.apache.log4j.Logger;
import week4.home.jdbc.main.AppStaticValues;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCDriver {
    private static Logger log = Logger.getLogger(JDBCDriver.class.getName());
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(JDBCDriver.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            log.info("File not found");
        }
    }

    private static Connection connection;
    private static boolean checker = false;

    public static Connection getConnection() {
        if (checker) {
            return getTestConnection();
        }
        return getCon("main");
    }

    public static Connection getTestConnection() {
        return getCon("test");
    }

    private static Connection getCon(String type) {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            switch (type) {
                case "main":
                    connection = DriverManager.getConnection(properties.getProperty("db.url"),
                            properties.getProperty("db.username"), properties.getProperty("db.password"));
                    break;
                case "test":
                    connection = DriverManager.getConnection(properties.getProperty("db.test.url"),
                            properties.getProperty("db.test.username"), properties.getProperty("db.test.password"));
                    checker = true;
                    break;
            }

        } catch (SQLException e) {
            log.warn(AppStaticValues.ERROR_QUERY_PROBLEM);
        }
        return connection;
    }
}
