package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // NOTE: Update these with your specific database credentials
    private static final String URL = "jdbc:h2:mem:student_db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            // Initialize Database Schema
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 java.sql.Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS students (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "first_name VARCHAR(50) NOT NULL, " +
                        "last_name VARCHAR(50) NOT NULL, " +
                        "email VARCHAR(100) NOT NULL, " +
                        "course VARCHAR(100) NOT NULL, " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ")";
                stmt.execute(sql);
                System.out.println("H2 Database initialized successfully.");
            } catch (SQLException e) {
                System.err.println("Error initializing H2 database: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
