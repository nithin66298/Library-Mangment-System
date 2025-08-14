import java.io.*;
import java.util.*;

class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private double price;

    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Price: " + price;
    }
}

public class LibraryManagementSystem {
    private static final String FILE_NAME = "library.dat";
    private static ArrayList<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Update Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addBook(sc);
                case 2 -> viewBooks();
                case 3 -> searchBook(sc);
                case 4 -> deleteBook(sc);
                case 5 -> updateBook(sc);
                case 6 -> saveData();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }

    private static void addBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        books.add(new Book(id, title, author, price));
        System.out.println("Book added successfully!");
    }

    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }

    private static void searchBook(Scanner sc) {
        System.out.print("Enter title or author to search: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword) ||
                b.getAuthor().toLowerCase().contains(keyword)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("No matching book found.");
    }

    private static void deleteBook(Scanner sc) {
        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();
        boolean removed = books.removeIf(b -> b.getId() == id);
        if (removed) System.out.println("Book deleted successfully!");
        else System.out.println("Book not found.");
    }

    private static void updateBook(Scanner sc) {
        System.out.print("Enter Book ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Book b : books) {
            if (b.getId() == id) {
                System.out.print("Enter new Title: ");
                b.setTitle(sc.nextLine());
                System.out.print("Enter new Author: ");
                b.setAuthor(sc.nextLine());
                System.out.print("Enter new Price: ");
                b.setPrice(sc.nextDouble());
                System.out.println("Book updated successfully!");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            books = (ArrayList<Book>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
            System.out.println("Data saved successfully. Goodbye!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
