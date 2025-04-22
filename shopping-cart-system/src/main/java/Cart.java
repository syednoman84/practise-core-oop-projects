import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private double manualDiscount; // by percent
    private Coupon appliedCoupon;

    public Cart() {
        this.items = new ArrayList<>();
        this.manualDiscount = 0.0;
    }

    public void addProduct(Product product, int quantity) {
        if (quantity > product.getStock()) {
            System.out.println("Not enough stock for " + product.getName());
            return;
        }

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                if (item.getQuantity() + quantity > product.getStock()) {
                    System.out.println("Cannot exceed available stock for " + product.getName());
                    return;
                }
                item.increaseQuantity(quantity);
                product.reduceStock(quantity);
                System.out.println("Updated quantity of " + product.getName());
                return;
            }
        }

        items.add(new CartItem(product, quantity));
        product.reduceStock(quantity);
        System.out.println("Added " + product.getName() + " to cart.");
    }

    public void removeProduct(String productId) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                item.getProduct().increaseStock(item.getQuantity()); // return to stock
                iterator.remove();
                System.out.println("Removed " + item.getProduct().getName() + " from cart.");
                return;
            }
        }
        System.out.println("Product with ID " + productId + " not found in cart.");
    }

    public void applyDiscount(double percent) {
        if (percent < 0 || percent > 100) {
            System.out.println("Invalid discount value.");
            return;
        }
        this.manualDiscount = percent;
        System.out.println("Applied " + percent + "% manual discount.");
    }

    public void applyCoupon(Coupon coupon) {
        this.appliedCoupon = coupon;
        System.out.println("Coupon " + coupon.getCode() + " applied. " +
                coupon.getDiscountPercent() + "% off!");
    }

    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        double finalDiscount = manualDiscount;
        if (appliedCoupon != null) {
            finalDiscount += appliedCoupon.getDiscountPercent();
        }
        return total * (1 - finalDiscount / 100);
    }

    public void showCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("Your Cart:");
        for (CartItem item : items) {
            System.out.println(" - " + item);
        }

        double total = getTotalAmount();
        double discount = manualDiscount + (appliedCoupon != null ? appliedCoupon.getDiscountPercent() : 0);
        System.out.printf("Total (after %.1f%% discount): $%.2f%n", discount, total);
    }

    public List<CartItem> getItems() {
        return items;
    }
}
