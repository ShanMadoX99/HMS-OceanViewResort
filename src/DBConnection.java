import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Hold one single connection object
    private static Connection conn = null;

    // Private constructor prevents creating new DBConnection objects
    private DBConnection() {}

    // Public method to get the single connection
    public static Connection getConnection() {
        if (conn == null) { // only create once
            try {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/hotel_db", // your DB name
                        "root",                               // your username
                        "MCPShan99@Mysql2026"                        // your password
                );
                System.out.println("✅ Database connected successfully!");
            } catch (SQLException e) {
                System.err.println("❌ Database connection failed: " + e.getMessage());
            }
        }
        return conn;
    }
}