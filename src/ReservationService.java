import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationService {

    // Add a new reservation
    public void addReservation(int guestId, String roomType, String checkIn, String checkOut) {
        try {
            // ✅ Validation check before inserting
            if (checkIn.compareTo(checkOut) >= 0) {
                throw new IllegalArgumentException("❌ Check-out date must be after check-in date!");
            }

            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO Reservations (guest_id, room_type, check_in, check_out) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, guestId);
            ps.setString(2, roomType);
            ps.setString(3, checkIn);
            ps.setString(4, checkOut);
            ps.executeUpdate();
            System.out.println("✅ Reservation added successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Error adding reservation: " + e.getMessage());
        }
    }

    // Display one reservation
    public void displayReservation(int resId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Reservations WHERE reservation_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, resId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Reservation ID: " + rs.getInt("reservation_id"));
                System.out.println("Guest ID: " + rs.getInt("guest_id"));
                System.out.println("Room Type: " + rs.getString("room_type"));
                System.out.println("Check-in: " + rs.getDate("check_in"));
                System.out.println("Check-out: " + rs.getDate("check_out"));
            } else {
                System.out.println("❌ Reservation not found!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error displaying reservation: " + e.getMessage());
        }
    }

    // List all reservations
    public void listAllReservations() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Reservations";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- All Reservations ---");
            while (rs.next()) {
                System.out.println("Reservation ID: " + rs.getInt("reservation_id") +
                        ", Guest ID: " + rs.getInt("guest_id") +
                        ", Room Type: " + rs.getString("room_type") +
                        ", Check-in: " + rs.getDate("check_in") +
                        ", Check-out: " + rs.getDate("check_out"));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error listing reservations: " + e.getMessage());
        }
    }
}