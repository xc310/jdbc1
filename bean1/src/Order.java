import java.sql.Date;

public class Order {
    private int orderId;
    private String orderName;
    private Date orderDare;

    public Order() {
    }

    public Order(int orderId, String orderName, Date orderDare) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDare = orderDare;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Date getOrderDare() {
        return orderDare;
    }

    public void setOrderDare(Date orderDare) {
        this.orderDare = orderDare;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderDare=" + orderDare +
                '}';
    }
}