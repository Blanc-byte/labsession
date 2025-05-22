package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/comlab";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null; // ✅ Missing field added

    // Private constructor to prevent instantiation
    public DBConnector() {}

    // Get the single connection instance
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Error connecting to the database.");
            }
        }
        return connection;
    }
}
