package main.java.com.cafe;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private List<Product> products = new ArrayList<>();
    private int currentId = 1;

    public void addProduct(Product product) {
        product.setId(currentId++);
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                return;
            }
        }
    }

    public void deleteProduct(int id) {
        products.removeIf(product -> product.getId() == id);
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}

