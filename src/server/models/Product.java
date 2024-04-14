package server.models;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {
    private UUID productID;
    private String name;
    private String description;
    private double price;
    private int quantityAvailable;

    public Product(String name, String description, double price) {
        this.productID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = 0;
    }

    public Product(String name, String description, double price, int quantityAvailable) {
        this.productID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public UUID getProductID() {
        return productID;
    }

    public void setProductID(UUID productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}
