import java.sql.*;
import java.util.Scanner;

public class HMSOceanViewResort {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        if (!login()) {
            System.out.println("Invalid login. Exiting...");
            return;
        }

        int choice;
        do {
            System.out.println("\n--- HMS Ocean View Resort ---");
            System.out.println("1. Add New Reservation");
            System.out.println("2. Display Reservation Details");
            System.out.println("3. List All Reservations"); // new option
            System.out.println("4. Calculate and Print Bill");
            System.out.println("5. Help");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addReservation();
                case 2 -> displayReservation();
                case 3 -> listAllReservations();
                case 4 -> calculateBill();
                case 5 -> help();
                case 6 -> {
                    System.out.println("Exiting system... Goodbye!");
                    System.exit(0);  // ✅ ends the program immediately
                }
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    private static boolean login() {
        System.out.print("Enter username: ");
        String user = sc.next();
        System.out.print("Enter password: ");
        String pass = sc.next();

        // Simple hardcoded login for demo
        return user.equals("admin") && pass.equals("1234");
    }

    private static void addReservation() {
        try {
            Connection conn = DBConnection.getConnection();

            System.out.print("Guest ID: ");
            int guestId = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Room Type (Deluxe/Standard): ");
            String roomType = sc.nextLine();
            if (roomType.isEmpty()) {
                System.out.println("❌ Room type cannot be empty!");
                return;
            }

            System.out.print("Check-in (YYYY-MM-DD): ");
            String checkIn = sc.nextLine();
            System.out.print("Check-out (YYYY-MM-DD): ");
            String checkOut = sc.nextLine();

            if (checkIn.compareTo(checkOut) >= 0) {
                System.out.println("❌ Check-out date must be after check-in date!");
                return;
            }

            String sql = "INSERT INTO Reservations (guest_id, room_type, check_in, check_out) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, guestId);
            ps.setString(2, roomType);
            ps.setString(3, checkIn);
            ps.setString(4, checkOut);
            ps.executeUpdate();

            System.out.println("✅ Reservation added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private static void displayReservation() {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.print("Enter Reservation ID: ");
            int resId = sc.nextInt();

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
                System.out.println("Reservation not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listAllReservations() {
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
            e.printStackTrace();
        }
    }

    private static void calculateBill() {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.print("Enter Reservation ID: ");
            int resId = sc.nextInt();

            String sql = "SELECT check_in, check_out, room_type FROM Reservations WHERE reservation_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, resId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date checkIn = rs.getDate("check_in");
                Date checkOut = rs.getDate("check_out");
                String roomType = rs.getString("room_type");

                long nights = (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
                double rate = roomType.equalsIgnoreCase("Deluxe") ? 10000 : 5000;
                double total = nights * rate;

                System.out.println("Total Bill: Rs. " + total);
            } else {
                System.out.println("Reservation not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void help() {
        System.out.println("\n--- Help Section ---");
        System.out.println("1. Add New Reservation - Enter guest ID, room type, and dates to create a reservation.");
        System.out.println("2. Display Reservation Details - View details of a single reservation by entering its ID.");
        System.out.println("3. List All Reservations - Show all reservations currently stored in the system.");
        System.out.println("4. Calculate and Print Bill - Calculate the total bill for a reservation based on room type and stay duration.");
        System.out.println("5. Help - Display this guide.");
        System.out.println("6. Exit - Safely close the system.");

    }
}
