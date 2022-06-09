package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "greenfrog13$";

    public static Connection getConnection() {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }}
