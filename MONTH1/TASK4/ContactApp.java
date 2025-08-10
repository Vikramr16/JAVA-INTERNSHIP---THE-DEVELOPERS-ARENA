import java.io.*;
import java.util.Scanner;

class Contact {
    private String name;
    private long phone;

    public Contact(String name, long phone) {
        this.name = name;
        this.phone = phone;
    }

    public String toFileString() {
        return name + "," + phone;
    }

    public static Contact fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 2) return null;
        return new Contact(parts[0], Long.parseLong(parts[1]));
    }

    public String toString() {
        return "Name: " + name + ", Phone: " + phone;
    }
}

public class ContactApp {
    static final String FILE_NAME = "contacts.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;

        System.out.println("\nWelcome to Your Contact App!");

        while (choice != 3) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> addContact();
                    case 2 -> viewContacts();
                    case 3 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Error: Invalid input.");
            }
        }
    }

    static void addContact() {
        try {
            System.out.print("Enter name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter phone: ");
            long phone = Long.parseLong(sc.nextLine().trim());

            Contact contact = new Contact(name, phone);

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bw.write(contact.toFileString());
            bw.newLine();
            bw.close();

            System.out.println("Contact added successfully !!.");

        } catch (IOException e) {
            System.out.println("Error writing to file.");
        } catch (NumberFormatException e) {
            System.out.println("Phone number must be digits only.");
        }
    }

    static void viewContacts() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("No contacts found.");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                Contact c = Contact.fromFileString(line);
                if (c != null) {
                    System.out.println(c);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No contacts found.");
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
