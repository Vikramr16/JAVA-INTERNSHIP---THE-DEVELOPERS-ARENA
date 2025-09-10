package month2;
import java.sql.*;
import java.util.Scanner;

class ContactDetails {
    String name;
    int phone;
    String email;

    public void addDetails(String name, int phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() { return this.name; }
    public int getPhone() { return this.phone; }
    public String getEmail() { return this.email; }

    public String toString() {
        return this.name + " " + this.phone + " " + this.email;
    }
}

public class ContactBookManager {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/login_schema",
                "root",
                "Kannan@2704"
            );

            Scanner sc = new Scanner(System.in);
            int choice = 0;

            while (choice != 5) {
                System.out.println("\n--- Contact Book Menu ---");
                System.out.println("1. Add Contact");
                System.out.println("2. Delete Contact");
                System.out.println("3. Search Contact");
                System.out.println("4. Show All Contacts");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter name: ");
                        String name = sc.next();
                        System.out.print("Enter phone: ");
                        int phone = sc.nextInt();
                        System.out.print("Enter email: ");
                        String email = sc.next();

                        String sql = "INSERT INTO contactBook(name, email, phone) VALUES (?, ?, ?)";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, name);
                        pstmt.setString(2, email);
                        pstmt.setInt(3, phone);

                        int rows = pstmt.executeUpdate();
                        if (rows > 0) {
                            System.out.println("Contact saved in DB.");
                        }
                    }

                    case 2 -> {
                        System.out.print("Enter name/email/phone to delete: ");
                        String input = sc.next();

                        String sql = "DELETE FROM contactBook WHERE name=? OR email=? OR phone=?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, input);
                        pstmt.setString(2, input);
                        try {
                            pstmt.setInt(3, Integer.parseInt(input));
                        } catch (NumberFormatException e) {
                            pstmt.setInt(3, -1); 
                        }

                        int rows = pstmt.executeUpdate();
                        if (rows > 0) {
                            System.out.println("Contact deleted.");
                        } else {
                            System.out.println("Contact not found.");
                        }
                    }

                    case 3 -> {
                        System.out.print("Enter name/email/phone to search: ");
                        String input = sc.next();

                        String sql = "SELECT * FROM contactBook WHERE name=? OR email=? OR phone=?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, input);
                        pstmt.setString(2, input);
                        try {
                            pstmt.setInt(3, Integer.parseInt(input));
                        } catch (NumberFormatException e) {
                            pstmt.setInt(3, -1);
                        }

                        ResultSet rs = pstmt.executeQuery();
                        boolean found = false;
                        while (rs.next()) {
                            System.out.println("Found contact: "
                                    + rs.getString("name") + " "
                                    + rs.getInt("phone") + " "
                                    + rs.getString("email"));
                            found = true;
                        }
                        if (!found) {
                            System.out.println("Contact not found.");
                        }
                    }

                    case 4 -> {
                        String sql = "SELECT * FROM contactBook";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery();

                        System.out.println("\n--- Contact List ---");
                        while (rs.next()) {
                            String name = rs.getString("name");
                            String email = rs.getString("email");
                            int phone = rs.getInt("phone");

                            System.out.println(name + " " + phone + " " + email);
                        }
                    }

                    case 5 -> System.out.println("Exiting...");

                    default -> System.out.println("Invalid choice. Try again.");
                }
            }

            sc.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
