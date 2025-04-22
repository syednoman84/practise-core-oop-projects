public class Order {
    private Cart cart;
    private PaymentStrategy paymentStrategy;
    private boolean isPaid;

    public Order(Cart cart, PaymentStrategy paymentStrategy) {
        this.cart = cart;
        this.paymentStrategy = paymentStrategy;
        this.isPaid = false;
    }

    public void processOrder() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cannot process order. Cart is empty.");
            return;
        }

        double amount = cart.getTotalAmount();
        paymentStrategy.pay(amount);
        isPaid = true;

        OrderHistoryUtil.saveOrder(cart, paymentStrategy);

        System.out.println("Order has been placed and saved to history!");
    }

    public boolean isPaid() {
        return isPaid;
    }
}
