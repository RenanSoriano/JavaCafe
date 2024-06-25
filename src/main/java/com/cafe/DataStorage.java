package main.java.com.cafe;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static final String PRODUCT_FILE = "products.dat";
    private static final String SALE_FILE = "sales.dat";
    private static final String INVOICE_FILE = "invoices.txt";

    public static void saveProducts(List<Product> products) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCT_FILE))) {
            oos.writeObject(products);
        }
    }

    public static List<Product> loadProducts() throws IOException, ClassNotFoundException {
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) {
            return new ArrayList<>(); // Retorna uma lista vazia se o arquivo não existir
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Product>) ois.readObject();
        }
    }

    public static void saveSales(List<Sale> sales) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALE_FILE))) {
            oos.writeObject(sales);
        }
    }

    public static List<Sale> loadSales() throws IOException, ClassNotFoundException {
        File file = new File(SALE_FILE);
        if (!file.exists()) {
            return new ArrayList<>(); // Retorna uma lista vazia se o arquivo não existir
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Sale>) ois.readObject();
        }
    }

    public static void saveInvoice(Invoice invoice) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVOICE_FILE, true))) {
            writer.write(invoice.toString());
            writer.write("----------\n");
        }
    }
}


