package common.services;

import common.models.Product;

import java.rmi.Remote;
import java.util.List;
import java.util.UUID;

public interface ProductService extends Remote {
    public void addNewProduct(Product product);
    public void removeProduct(UUID productID);
    public boolean updateItemDescription(UUID productID, String newDesc);
    public boolean updateItemPrice(UUID productID, double newPrice);
    public boolean updateItemQuantity(UUID productID, int newQuantity);
    public List<Product> getAllProducts();
    public Product getProduct(UUID productID);
}