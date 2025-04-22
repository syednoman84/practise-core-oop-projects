import java.io.*;
import java.util.*;

public class ProductFileUtil {
    public static List<Product> loadProductsFromFile(String path) {
        List<Product> products = new ArrayList<>();
        File file = new File(path);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 4) {
                    String id = tokens[0];
                    String name = tokens[1];
                    double price = Double.parseDouble(tokens[2]);
                    int stock = Integer.parseInt(tokens[3]);
                    products.add(new Product(id, name, price, stock));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load products: " + e.getMessage());
        }

        return products;
    }


    public static void saveProductsToFile(String filename, List<Product> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("id,name,price,stock\n");
            for (Product product : products) {
                bw.write(String.format("%s,%s,%.2f,%d\n",
                        product.getId(), product.getName(), product.getPrice(), product.getStock()));
            }
        } catch (IOException e) {
            System.out.println("Failed to save products: " + e.getMessage());
        }
    }
}
