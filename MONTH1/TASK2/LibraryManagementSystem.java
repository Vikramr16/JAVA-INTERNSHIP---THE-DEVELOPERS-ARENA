import java.util.Scanner;

abstract class LibraryItem {
    private String title;
    private boolean borrowed;

    public LibraryItem(String title) {
        this.title = title;
        this.borrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    protected void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public abstract void display();

    public void borrow() {
        if (!borrowed) {
            setBorrowed(true);
            System.out.println("'" + title + "' book has been borrowed.");
        } else {
            System.out.println("'" + title + "' is already borrowed.");
        }
    }

    public void returnItem() {
        if (borrowed) {
            setBorrowed(false);
            System.out.println("'" + title + "' book has been returned.");
        } else {
            System.out.println("'" + title + "' was not borrowed.");
        }
    }
}

class Book extends LibraryItem {
    private String author;

    public Book(String title, String author) {
        super(title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public void display() {
        System.out.println(getTitle() + " by " + author + (isBorrowed() ? " [Borrowed]" : " [Available]"));
    }
}

class Library {
    private LibraryItem[] items;
    private int count;

    public Library(int capacity) {
        items = new LibraryItem[capacity];
        count = 0;
    }

    public void addItem(LibraryItem item) {
        if (count < items.length) {
            items[count] = item;
            count++;
            System.out.println(item.getTitle() + " book added successfully!!");
        } else {
            System.out.println("Library is full! Cannot add more items.");
        }
    }

    public void showAllItems() {
        if (count == 0) {
            System.out.println("No items in the library.");
            return;
        }
        System.out.println("\nLibrary Items:");
        for (int i = 0; i < count; i++) {
            items[i].display();
        }
    }

    public void borrowItem(String title) {
        for (int i = 0; i < count; i++) {
            if (items[i].getTitle().equalsIgnoreCase(title)) {
                items[i].borrow();
                return;
            }
        }
        System.out.println("Item titled '" + title + "' not found.");
    }

    public void returnItem(String title) {
        for (int i = 0; i < count; i++) {
            if (items[i].getTitle().equalsIgnoreCase(title)) {
                items[i].returnItem();
                return;
            }
        }
        System.out.println("Item titled '" + title + "' not found.");
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library(100);

        int choice;

        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Show All Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();

                    Book book = new Book(title, author);
                    library.addItem(book);
                    break;

                case 2:
                    library.showAllItems();
                    break;

                case 3:
                    System.out.print("Enter book title to borrow: ");
                    String borrowTitle = scanner.nextLine();
                    library.borrowItem(borrowTitle);
                    break;

                case 4:
                    System.out.print("Enter book title to return: ");
                    String returnTitle = scanner.nextLine();
                    library.returnItem(returnTitle);
                    break;

                case 0:
                    System.out.println("Exiting... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
