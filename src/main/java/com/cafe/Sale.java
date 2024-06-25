package main.java.com.cafe;





import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Sale implements Serializable {
    private static final long serialVersionUID = 1L; // Defina um serialVersionUID fixo

    private int id;
    private List<OrderItem> items;
    private double totalCost;
    private LocalDateTime saleTime;

    public Sale(int id, List<OrderItem> items, double totalCost, LocalDateTime saleTime) {
        this.id = id;
        this.items = items;
        this.totalCost = totalCost;
        this.saleTime = saleTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(LocalDateTime saleTime) {
        this.saleTime = saleTime;
    }

    public Invoice generateInvoice() {
        double tax = totalCost * 0.003;
        return new Invoice(id, saleTime, items, totalCost + tax, tax);
    }
}

