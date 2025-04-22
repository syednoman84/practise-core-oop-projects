import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderHistoryUtil {
    private static final String ORDER_FILE = Paths.get("shopping-cart-system", "data", "orders.csv").toString();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void saveOrder(Cart cart, PaymentStrategy paymentStrategy) {
        System.out.println("Writing order history to: " + ORDER_FILE);
        File file = new File(ORDER_FILE);
        file.getParentFile().mkdirs(); // Create "data" directory if it doesn't exist

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (file.length() == 0) {
                bw.write("timestamp,product_id,product_name,quantity,unit_price,total_price,payment_method\n");
            }

            String paymentType = paymentStrategy instanceof CreditCardPayment ? "CreditCard"
                    : paymentStrategy instanceof PayPalPayment ? "PayPal"
                    : "Unknown";

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            for (CartItem item : cart.getItems()) {
                Product product = item.getProduct();
                int qty = item.getQuantity();
                double unitPrice = product.getPrice();
                double total = qty * unitPrice;

                bw.write(String.format("%s,%s,%s,%d,%.2f,%.2f,%s\n",
                        timestamp,
                        product.getId(),
                        product.getName(),
                        qty,
                        unitPrice,
                        total,
                        paymentType
                ));
            }

        } catch (IOException e) {
            System.out.println("Failed to save order: " + e.getMessage());
        }
    }


    public static void viewOrderHistory() {
        File file = new File(ORDER_FILE);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // header
            if (line == null) {
                System.out.println("No order history found.");
                return;
            }

            System.out.printf("%-20s %-10s %-20s %-8s %-10s %-12s %-15s%n",
                    "Timestamp", "ID", "Product Name", "Qty", "Unit Price", "Total", "Payment");
            System.out.println("---------------------------------------------------------------------------------------------");

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 7) {
                    System.out.printf("%-20s %-10s %-20s %-8s $%-9s $%-11s %-15s%n",
                            tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading order history: " + e.getMessage());
        }
    }
}
