package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Manages the embedded Apache Derby database.
 */
public class DatabaseManager {
    // The connection URL. It creates a "GameDB" folder in your project directory.
    private static final String DB_URL = "jdbc:derby:GameDB;create=true";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // "SQL Safe Table Drop" approach: Try to create the table.
            try {
                stmt.execute("CREATE TABLE GAME_DATA (ITEM_NAME VARCHAR(50) PRIMARY KEY, STAT_VALUE INT)");
                // Insert default battery level of 100 on the very first run
                stmt.execute("INSERT INTO GAME_DATA (ITEM_NAME, STAT_VALUE) VALUES ('Flashlight', 100)");
                System.out.println("Database initialized successfully.");
            } catch (Exception e) {
                // Table already exists. Ignore the error and continue safely.
            }
        } catch (Exception e) {
            System.out.println("Database Connection Error: " + e.getMessage());
        }
    }

    // BASIC READ: Gets the saved battery level
    public static int getBatteryLevel() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT STAT_VALUE FROM GAME_DATA WHERE ITEM_NAME = 'Flashlight'")) {
            
            if (rs.next()) {
                return rs.getInt("STAT_VALUE");
            }
        } catch (Exception e) {
            System.out.println("Read Error: " + e.getMessage());
        }
        return 100; // Fallback
    }

    // BASIC UPDATE: Saves the new battery level
    public static void updateBatteryLevel(int newLevel) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE GAME_DATA SET STAT_VALUE = ? WHERE ITEM_NAME = 'Flashlight'")) {
            
            pstmt.setInt(1, newLevel);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }
}