import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileManager {
    // Database connection parameters
    static String url = "YOUR MYSQL DATABASE URL";
    static String username = "YOUR DATABASE USERNAME";
    static String password = "YOUR DATABASE PASSWORD";

    public void printRecords() {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            // Print header
            System.out.println(" ------------------------------------------------------------------ ");
            System.out.println("|Item ID|Description | Unit Price  | Qty in Stock   | Total Value  |");
            System.out.println("|-------|------------|-------------|----------------|--------------|");

            // Prepare SQL query
            String sql = "SELECT * FROM items";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Print data
            while (rs.next()) {
                int id = rs.getInt("Item_Id");
                String description = rs.getString("description");
                double unitPrice = rs.getDouble("Unit_Price");
                int qty = rs.getInt("qty");
                double totalValue = rs.getDouble("Total_Value");

                System.out.printf("| %05d |%10s  |£%12.2f| %12d   | £%12.2f|%n", id, description, unitPrice, qty, totalValue);
            }

            System.out.println(" ------------------------------------------------------------------ ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTransactions() {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            System.out.println(" --------------------------------------------------------------------------------------------------- ");
            System.out.println("|    Date    |   Time   |Item ID| Description | Unit Price  | Qty in Stock| Total Value | Activity  |");
            System.out.println("|------------|----------|-------|-------------|-------------|-------------|-------------|-----------|");

            // Prepare SQL query
            String sql = "SELECT * FROM transactions";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Print data
            while (rs.next()) {
                String date = rs.getString("date");
                String time = rs.getString("time");
                int id = rs.getInt("Item_Id");
                String description = rs.getString("description");
                double unitPrice = rs.getDouble("Unit_price");
                int qty = rs.getInt("qty");
                double totalValue = rs.getDouble("Total_Value");
                String activity = rs.getString("activity");

                // Print the result
                System.out.printf("| %-10s | %-8s | %05d |%-12s | £%10.2f | %9d   | £%10.2f | %-9s |%n",
                        date, time, id, description, unitPrice, qty, totalValue, activity);
            }

            System.out.println(" --------------------------------------------------------------------------------------------------- ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
