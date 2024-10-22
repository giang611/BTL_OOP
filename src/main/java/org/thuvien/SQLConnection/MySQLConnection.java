package org.prj.projectt.SQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {


    private static final String URL = "jdbc:mysql://localhost:3306/quanlithuvien";

    private static final String USER = "root";
    private static final String PASSWORD = "3876676";


    public static Connection connect() {
        Connection connection = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: Unable to connect to the database.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {

        Connection connection = connect();

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

