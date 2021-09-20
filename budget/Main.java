package budget;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Budget budget = new Budget(scanner);

        while (true) {
            displayMenu();
            int input = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            if (input == 0) {
                System.out.println("Bye!");
                break;
            }

            switch (input) {
                case 1:
                    budget.addIncome();
                    break;
                case 2:
                    budget.addPurchases();
                    break;
                case 3:
                    budget.printPurchases();
                    break;
                case 4:
                    budget.printBalance();
                    break;
                case 5:
                    budget.save("C:\\purchases.txt");
                    break;
                case 6:
                    budget.load("C:\\purchases.txt");
                    break;
                case 7:
                    budget.sort();
                    break;

            }
            System.out.println();
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }
}
