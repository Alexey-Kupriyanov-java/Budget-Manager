package budget;

public class Purchase {
    private String category;
    private String name;
    private double price;

    public Purchase(String category, String name, double price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format(name + " $%.2f", price);
    }
}
