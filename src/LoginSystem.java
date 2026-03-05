import java.util.Scanner;

public class LoginSystem {
    public static boolean login() {
        Scanner sc = new Scanner(System.in);

        // Hardcoded username and password for now
        String correctUsername = "admin";
        String correctPassword = "1234";

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (username.equals(correctUsername) && password.equals(correctPassword)) {
            System.out.println("Login successful! Welcome to HMS Ocean View Resort.");
            return true;
        } else {
            System.err.println("Invalid username or password. Please try again.");
            return false;
        }
    }
}