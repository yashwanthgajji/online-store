package client.controllers;

import common.models.Product;
import common.services.ProductService;

import java.util.ArrayList;
import java.util.UUID;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void viewAllProducts() throws Exception {
        ArrayList<Product> products = productService.getAllProducts();
        System.out.println("********* PRODUCTS *********");
        StringBuilder sb = new StringBuilder();
        sb.append("S.NO")
                .append("\t").append("Product ID")
                .append("\t").append("Product Name")
                .append("\t").append("Description")
                .append("\t").append("Price")
                .append("\t").append("Stock Available")
                .append("\n");
        int i = 1;
        for (Product product: products) {
            sb.append(i)
                    .append("\t").append(product.getProductID())
                    .append("\t").append(product.getName())
                    .append("\t").append(product.getDescription())
                    .append("\t").append(product.getPrice())
                    .append("\t").append(product.getQuantityAvailable())
                    .append("\n");
            i++;
        }
        System.out.println(sb);
    }

    public void addNewProduct(String name, String desc, double price, int qty) throws Exception {
        Product product = new Product(name, desc, price, qty);
        productService.addNewProduct(product);
    }

    public void removeProduct(String productID) throws Exception {
        productService.removeProduct(UUID.fromString(productID));
    }

    public void updateItemDescription(String pid, String newDesc) throws Exception {
        productService.updateItemDescription(UUID.fromString(pid), newDesc);
    }

    public void updateItemPrice(String pid, double newPrice) throws Exception {
        productService.updateItemPrice(UUID.fromString(pid), newPrice);
    }


    public void updateItemQuantity(String pid, int newQty) throws Exception {
        productService.updateItemQuantity(UUID.fromString(pid), newQty);
    }
}
