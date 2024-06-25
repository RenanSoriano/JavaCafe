package main.java.com.cafe;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Controls the logic between the GUI and the data

public class Controller {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField saleQuantityField;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableView<OrderItem> orderItemsTable;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemProductIdColumn;
    @FXML
    private TableColumn<OrderItem, String> orderItemProductNameColumn;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemQuantityColumn;
    @FXML
    private TableColumn<OrderItem, Double> orderItemPriceColumn;
    @FXML
    private TableColumn<OrderItem, Double> orderItemTotalPriceColumn;
    @FXML
    private TableView<Sale> saleTable;
    @FXML
    private TableColumn<Sale, Integer> saleIdColumn;
    @FXML
    private TableColumn<Sale, Double> saleTotalCostColumn;
    @FXML
    private TableColumn<Sale, LocalDateTime> saleTimeColumn;
    @FXML
    private TableView<Product> saleProductTable;
    @FXML
    private TableColumn<Product, Integer> saleProductIdColumn;
    @FXML
    private TableColumn<Product, String> saleProductNameColumn;
    @FXML
    private TableColumn<Product, Double> saleProductPriceColumn;
    @FXML
    private TableColumn<Product, Integer> saleProductQuantityColumn;

    private ProductDAO productDAO = new ProductDAO();
    private SaleDAO saleDAO = new SaleDAO();
    private ObservableList<Product> productList;
    private ObservableList<OrderItem> orderItemList;
    private ObservableList<Sale> saleList;


    //initializes the controller and the loads the data from products and sale
    @FXML
    public void initialize() {
        try {
            List<Product> products = DataStorage.loadProducts();
            productDAO.getAllProducts().addAll(products);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            List<Sale> sales = DataStorage.loadSales();
            saleDAO.getAllSales().addAll(sales);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        orderItemProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        orderItemProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        orderItemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderItemTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        saleIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        saleTotalCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        saleTimeColumn.setCellValueFactory(new PropertyValueFactory<>("saleTime"));

        saleProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        saleProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        saleProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        saleProductQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        productList = FXCollections.observableArrayList(productDAO.getAllProducts());
        orderItemList = FXCollections.observableArrayList(new ArrayList<>());
        saleList = FXCollections.observableArrayList(saleDAO.getAllSales());

        productTable.setItems(productList);
        orderItemsTable.setItems(orderItemList);
        saleTable.setItems(saleList);
        saleProductTable.setItems(productList); // Sincronizar a tabela de produtos na aba de vendas

        checkStockLevels(); // Verificar níveis de estoque na inicialização
    }


    //adds a product to the inventory
    @FXML
    private void handleAddProduct() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            if (!name.isEmpty() && price > 0 && quantity > 0) {
                Product product = new Product(0, name, price, quantity);
                productDAO.addProduct(product);
                updateProductList();
                DataStorage.saveProducts(productDAO.getAllProducts());
                checkStockLevels(); // Verificar níveis de estoque após adicionar produto
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid price and quantity.");
        } catch (IOException e) {
            showAlert("Data Storage Error", "Failed to save product data.");
        }
    }


    //updates a product in the inventory
    @FXML
    private void handleUpdateProduct() {
        try {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                selectedProduct.setName(nameField.getText());
                selectedProduct.setPrice(Double.parseDouble(priceField.getText()));
                selectedProduct.setQuantity(Integer.parseInt(quantityField.getText()));
                productDAO.updateProduct(selectedProduct);
                updateProductList();
                DataStorage.saveProducts(productDAO.getAllProducts());
                checkStockLevels(); // Verificar níveis de estoque após atualizar produto
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid price and quantity.");
        } catch (IOException e) {
            showAlert("Data Storage Error", "Failed to save product data.");
        }
    }


    //deletes a product in inventory
    @FXML
    private void handleDeleteProduct() {
        try {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                productDAO.deleteProduct(selectedProduct.getId());
                updateProductList();
                DataStorage.saveProducts(productDAO.getAllProducts());
                checkStockLevels(); // Verificar níveis de estoque após deletar produto
            }
        } catch (IOException e) {
            showAlert("Data Storage Error", "Failed to save product data.");
        }
    }


    //adds product to order
    @FXML
    private void handleAddOrderItem() {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(saleQuantityField.getText());
            Product product = productDAO.getProductById(productId);
            if (product != null && quantity > 0 && quantity <= product.getQuantity()) {
                OrderItem orderItem = new OrderItem(productId, product.getName(), quantity, product.getPrice());
                orderItemList.add(orderItem);
                updateOrderItemList();
            } else {
                showAlert("Invalid Input", "Product not found or insufficient stock.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid product ID and quantity.");
        }
    }


    //completes the transaction, updates inventory, saves invoice
    @FXML
    private void handleCompleteSale() {
        try {
            if (!orderItemList.isEmpty()) {
                double totalCost = orderItemList.stream().mapToDouble(OrderItem::getTotalPrice).sum();
                Sale sale = new Sale(0, new ArrayList<>(orderItemList), totalCost, LocalDateTime.now());
                saleDAO.addSale(sale);
                updateSaleList();

                for (OrderItem orderItem : orderItemList) {
                    Product product = productDAO.getProductById(orderItem.getProductId());
                    if (product != null) {
                        product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                        productDAO.updateProduct(product);
                    }
                }
                updateProductList();
                DataStorage.saveProducts(productDAO.getAllProducts());
                DataStorage.saveSales(saleDAO.getAllSales());

                Invoice invoice = sale.generateInvoice();
                DataStorage.saveInvoice(invoice);

                orderItemList.clear();
                updateOrderItemList();
                checkStockLevels(); // Verificar níveis de estoque após completar venda
            }
        } catch (IOException e) {
            showAlert("Data Storage Error", "Failed to save data.");
        }
    }


    //shows sales report/invoices
    @FXML
    private void handleSalesReport() {
        try (BufferedReader reader = new BufferedReader(new FileReader("invoices.txt"))) {
            StringBuilder reportContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                reportContent.append(line).append("\n");
            }
            showReport("Sales Report", reportContent.toString());
        } catch (IOException e) {
            showAlert("File Error", "Failed to read the sales report.");
        }
    }

    private void updateProductList() {
        productList.setAll(productDAO.getAllProducts());
        productTable.refresh();  // Atualizar a tabela de produtos na aba de produtos
        saleProductTable.refresh();  // Atualizar a tabela de produtos na aba de vendas
    }

    private void updateOrderItemList() {
        orderItemsTable.refresh();
    }

    private void updateSaleList() {
        saleList.setAll(saleDAO.getAllSales());
    }

    private void checkStockLevels() {
        StringBuilder lowStockProducts = new StringBuilder();
        for (Product product : productList) {
            if (product.getQuantity() <= 5) {
                lowStockProducts.append("Product: ").append(product.getName())
                        .append(", Quantity: ").append(product.getQuantity()).append("\n");
            }
        }
        if (lowStockProducts.length() > 0) {
            showAlert("Low Stock Alert", lowStockProducts.toString());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showReport(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

