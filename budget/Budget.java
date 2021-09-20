package budget;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Budget {
    private final Scanner scanner;
    private final List<Purchase> purchases;
    private double balance;

    public Budget(Scanner scanner) {
        this.scanner = scanner;
        purchases = new ArrayList<>();
    }

    public void addIncome() {
        double income = 0;

        System.out.println("Enter income:");
        income = scanner.nextDouble();

        balance += income;

        System.out.println("Income was added!");
    }

    public void addPurchases() {
        String category;
        String name;
        double price;

        while (true) {
            displayAddMenu();
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 5) {
                break;
            }

            switch (input) {
                case 1:
                    category = "Food";
                    break;
                case 2:
                    category = "Clothes";
                    break;
                case 3:
                    category = "Entertainment";
                    break;
                default:
                    category = "Other";
                    break;
            }

            System.out.println("\nEnter purchase name:");
            name = scanner.nextLine();
            System.out.println("Enter its price:");
            price = scanner.nextDouble();


            purchases.add(new Purchase(category, name, price));
            System.out.println("Purchase was added!");
            System.out.println();

            balance -= price;
        }
    }

    public void printPurchases() {
        if (purchases.size() == 0) {
            System.out.println("The purchases list is empty");
            return;
        }

        while (true) {
            displayPrintMenu();
            int input = scanner.nextInt();
            String categoryName = "All";
            List<Purchase> category = new ArrayList<>(purchases);
            scanner.nextLine();
            System.out.println();

            if (input == 6) {
                break;
            }

            switch (input) {
                case 1:
                    category = extractCategory("Food");
                    break;
                case 2:
                    category = extractCategory("Clothes");
                    break;
                case 3:
                    category = extractCategory("Entertainment");
                    break;
                case 4:
                    category = extractCategory("Other");
                    break;
            }
            if (category.size() == 0) {
                System.out.println("The purchases list is empty\n");
            } else {
                System.out.println(categoryName + ":");
                printPurchases(category);
            }
        }
    }

    public void printBalance() {
        System.out.printf("Balance: $%.2f\n", balance);
    }

    public void save(String fileName) {
        File file = new File(fileName);
        try (PrintWriter printer = new PrintWriter(file)) {
            printer.println(balance);
            for (Purchase purchase : purchases) {
                printer.println(purchase.getCategory());
                printer.println(purchase.getName());
                printer.println(purchase.getPrice());
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
        System.out.println("Purchases were saved!");
    }

    public void load(String fileName) {
        File file = new File(fileName);
        try (Scanner loader = new Scanner(file)) {
            balance = Double.parseDouble(loader.nextLine());
            while (loader.hasNext()) {
                String category = loader.nextLine();
                String name = loader.nextLine();
                double price = Double.parseDouble(loader.nextLine());
                purchases.add(new Purchase(category, name, price));
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
        System.out.println("Purchases were loaded!");
    }

    public void sort() {
        while (true) {
            displaySortMenu();
            int input = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            if (input == 4) {
                break;
            }

            switch (input) {
                case 1:
                    if (purchases.size() == 0) {
                        System.out.println("The purchases list is empty\n");
                    } else {
                        System.out.println("All:");
                        purchases.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
                        printPurchases(purchases);
                    }
                    break;
                case 2:
                    ArrayList<Type> types = new ArrayList<>();
                    types.add(new Type("Food - $", getTotal("Food")));
                    types.add(new Type("Clothes - $", getTotal("Clothes")));
                    types.add(new Type("Entertainment - $", getTotal("Entertainment")));
                    types.add(new Type("Other - $", getTotal("Other")));
                    types.sort((o1, o2) -> Double.compare(o2.getTotal(), o1.getTotal()));

                    System.out.println("Types:");
                    for (Type type : types) {
                        System.out.printf("%s%.2f\n", type.getName(), type.getTotal());
                    }
                    System.out.println();
                    break;
                case 3:
                    displaySortTypeMenu();
                    input = scanner.nextInt();
                    String categoryName = "All";
                    List<Purchase> category = new ArrayList<>(purchases);
                    scanner.nextLine();
                    System.out.println();
                    switch (input) {
                        case 1:
                            category = extractCategory("Food");
                            break;
                        case 2:
                            category = extractCategory("Clothes");
                            break;
                        case 3:
                            category = extractCategory("Entertainment");
                            break;
                        case 4:
                            category = extractCategory("Other");
                            break;
                    }
                    if (category.size() == 0) {
                        System.out.println("The purchases list is empty\n");
                    } else {
                        category.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
                        System.out.println(categoryName + ":");
                        printPurchases(category);
                    }
                    break;
            }
        }
    }

    private ArrayList<Purchase> extractCategory(String category) {
        ArrayList<Purchase> result = new ArrayList<>();
        for (Purchase purchase : purchases) {
            if (purchase.getCategory().equals(category)) {
                result.add(purchase);
            }
        }
        return result;
    }

    private void printPurchases(List<Purchase> category) {
        double total = 0;
        for (Purchase purchase : category) {
            System.out.println(purchase);
            total += purchase.getPrice();
        }
        System.out.printf("Total sum: $%.2f\n\n", total);
    }

    private double getTotal(String category) {
        double total = 0;
        for (Purchase purchase : purchases) {
            if (purchase.getCategory().equals(category) || "All".equals(category)) {
                total += purchase.getPrice();
            }
        }
        return total;
    }

    private void displayAddMenu() {
        System.out.println("Choose the type of purchases");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
    }

    private void displayPrintMenu() {
        System.out.println("Choose the type of purchases");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
    }

    private void displaySortMenu() {
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
    }

    private void displaySortTypeMenu() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
    }
}
