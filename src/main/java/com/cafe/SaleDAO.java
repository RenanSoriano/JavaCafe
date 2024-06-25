package main.java.com.cafe;

import java.util.ArrayList;
import java.util.List;

public class SaleDAO {
    private List<Sale> sales = new ArrayList<>();
    private int currentId = 1;

    public void addSale(Sale sale) {
        sale.setId(currentId++);
        sales.add(sale);
    }

    public List<Sale> getAllSales() {
        return sales;
    }
}


