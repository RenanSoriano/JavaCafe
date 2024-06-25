package main.java.com.cafe;



import java.time.LocalDateTime;
import java.util.List;

public class Invoice {
    private int saleId;
    private LocalDateTime saleTime;
    private List<OrderItem> items;
    private double totalCost;
    private double tax;

    public Invoice(int saleId, LocalDateTime saleTime, List<OrderItem> items, double totalCost, double tax) {
        this.saleId = saleId;
        this.saleTime = saleTime;
        this.items = items;
        this.totalCost = totalCost;
        this.tax = tax;
    }

    public int getSaleId() {
        return saleId;
    }

    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getTax() {
        return tax;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sale ID: ").append(saleId).append("\n");
        sb.append("Date: ").append(saleTime.toLocalDate()).append("\n");
        sb.append("Time: ").append(saleTime.toLocalTime()).append("\n");
        sb.append("Items:\n");
        for (OrderItem item : items) {
            sb.append(item.getProductName()).append(" x").append(item.getQuantity())
                    .append(" @ ").append(item.getPrice()).append(" each = ")
                    .append(item.getTotalPrice()).append("\n");
        }
        sb.append("Total cost before tax: ").append(totalCost - tax).append("\n");
        sb.append("Tax (0.3%): ").append(tax).append("\n");
        sb.append("Total cost: ").append(totalCost).append("\n");
        sb.append("----------\n");
        return sb.toString();
    }
}

