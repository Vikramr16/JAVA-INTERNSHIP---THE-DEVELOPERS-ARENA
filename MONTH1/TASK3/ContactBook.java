import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

class ContactDetails {
    String name;
    long phone;
    String email;

    public void addDetails(String name, long phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public long getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String toString() {
        return "Name: " + this.name + ", Phone: " + this.phone + ", Email: " + this.email;
    }
}

public class ContactBook {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<ContactDetails> contacts = new ArrayList<>();
        int choice = 0;

        System.out.println("\nWelcome to Your Contact Book!");

        while (choice != 4) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new contact");
            System.out.println("2. Delete a contact");
            System.out.println("3. Search for a contact");
            System.out.println("4. Exit");
            System.out.print("Your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 4: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter contact name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Enter contact phone number: ");
                    while (!sc.hasNextLong()) {
                        System.out.print("Invalid phone number. Please enter digits only: ");
                        sc.next();
                    }
                    long phone = sc.nextLong();
                    sc.nextLine();
                    System.out.print("Enter contact email address: ");
                    String email = sc.nextLine().trim();

                    ContactDetails newContact = new ContactDetails();
                    newContact.addDetails(name, phone, email);
                    contacts.add(newContact);
                    System.out.println("Contact added successfully.");
                }
                case 2 -> {
                    if (contacts.isEmpty()) {
                        System.out.println("Your contact book is empty. Nothing to delete.");
                        break;
                    }
                    System.out.print("Enter the name, email, or phone number of the contact to delete: ");
                    String input = sc.nextLine().trim();

                    Iterator<ContactDetails> iterator = contacts.iterator();
                    boolean removed = false;
                    while (iterator.hasNext()) {
                        ContactDetails c = iterator.next();
                        if (c.getName().equalsIgnoreCase(input) || c.getEmail().equalsIgnoreCase(input)
                                || String.valueOf(c.getPhone()).equals(input)) {
                            iterator.remove();
                            removed = true;
                            System.out.println("Contact successfully removed.");
                            break;
                        }
                    }
                    if (!removed) {
                        System.out.println("No matching contact found to delete.");
                    }
                }
                case 3 -> {
                    if (contacts.isEmpty()) {
                        System.out.println("Your contact book is empty. No contacts to search.");
                        break;
                    }
                    System.out.print("Enter the name, email, or phone number to search: ");
                    String input = sc.nextLine().trim();

                    boolean found = false;
                    for (ContactDetails c : contacts) {
                        if (c.getName().equalsIgnoreCase(input) || c.getEmail().equalsIgnoreCase(input)
                                || String.valueOf(c.getPhone()).equals(input)) {
                            System.out.println("Contact found: " + c);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("No contact found matching '" + input + "'.");
                    }
                }
                case 4 -> System.out.println("Thank you for using Contact Book. Goodbye!");
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
        sc.close();
    }
}
