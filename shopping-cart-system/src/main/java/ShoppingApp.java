import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class ShoppingApp {
    private static final String PRODUCT_FILE = Paths.get("shopping-cart-system", "data", "products.csv").toString();
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Product> storeProducts = new ArrayList<>();
    private static final Cart cart = new Cart();

    public static void main(String[] args) {
        System.out.println("Loading products from: " + PRODUCT_FILE);
        storeProducts.addAll(ProductFileUtil.loadProductsFromFile(PRODUCT_FILE));

        System.out.println("Loading products from code");
        initializeStore();

        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> listProducts();
                case 2 -> addToCart();
                case 3 -> removeFromCart();
                case 4 -> cart.showCart();
                case 5 -> applyDiscount();
                case 6 -> checkout();
                case 7 -> applyCoupon();
                case 8 -> OrderHistoryUtil.viewOrderHistory();
                case 0 -> {
                    ProductFileUtil.saveProductsToFile(PRODUCT_FILE, storeProducts);
                    System.out.println("Thank you for shopping with us!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("\n=== Shopping Cart Menu ===");
        System.out.println("1. View Store Products");
        System.out.println("2. Add Product to Cart");
        System.out.println("3. Remove Product from Cart");
        System.out.println("4. View Cart");
        System.out.println("5. Apply Discount");
        System.out.println("6. Checkout");
        System.out.println("7. Apply Coupon Code");
        System.out.println("8. View Order History");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void initializeStore() {
        storeProducts.add(new Product("P600", "Monitor", 900.00, 50));
        storeProducts.add(new Product("P700", "Hard-Drive", 300.00, 110));
        storeProducts.add(new Product("P800", "Camera", 350.00, 105));
        storeProducts.add(new Product("P900", "Airpods", 250.00,200));
        storeProducts.add(new Product("P1000", "AirMax", 450.00, 250));
    }

    private static void listProducts() {
        System.out.println("\nAvailable Products:");
        for (Product product : storeProducts) {
            System.out.println(product.getId() + ": " + product);
        }
    }

    private static void addToCart() {
        System.out.print("Enter Product ID to add: ");
        String id = scanner.nextLine();
        Product product = findProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = scanner.nextInt();
        scanner.nextLine();

        cart.addProduct(product, qty);
    }

    private static void removeFromCart() {
        System.out.print("Enter Product ID to remove: ");
        String id = scanner.nextLine();
        cart.removeProduct(id);
    }

    private static void applyDiscount() {
        System.out.print("Enter discount percentage: ");
        double percent = scanner.nextDouble();
        scanner.nextLine();

        cart.applyDiscount(percent);
    }

    private static void checkout() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty. Add items before checkout.");
            return;
        }

        System.out.println("\nChoose Payment Method:");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.print("Your choice: ");
        int method = scanner.nextInt();
        scanner.nextLine();

        PaymentStrategy strategy;
        switch (method) {
            case 1 -> {
                System.out.print("Enter Credit Card Number: ");
                String cc = scanner.nextLine();
                strategy = new CreditCardPayment(cc);
            }
            case 2 -> {
                System.out.print("Enter PayPal Email: ");
                String email = scanner.nextLine();
                strategy = new PayPalPayment(email);
            }
            default -> {
                System.out.println("Invalid payment method.");
                return;
            }
        }

        Order order = new Order(cart, strategy);
        order.processOrder();
    }

    private static Product findProductById(String id) {
        for (Product product : storeProducts) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }

    private static final Map<String, Coupon> availableCoupons = Map.of(
            "SAVE10", new Coupon("SAVE10", 10),
            "WELCOME15", new Coupon("WELCOME15", 15)
    );

    private static void applyCoupon() {
        System.out.print("Enter coupon code: ");
        String code = scanner.nextLine().toUpperCase();

        Coupon coupon = availableCoupons.get(code);
        if (coupon == null) {
            System.out.println("Invalid coupon code.");
            return;
        }

        cart.applyCoupon(coupon);
    }


}
