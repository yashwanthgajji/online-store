package common.services;

import common.models.Product;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.UUID;

public interface ProductService extends Remote {
    public void addNewProduct(Product product) throws Exception;
    public void removeProduct(UUID productID) throws Exception;
    public boolean updateItemDescription(UUID productID, String newDesc) throws Exception;
    public boolean updateItemPrice(UUID productID, double newPrice) throws Exception;
    public boolean updateItemQuantity(UUID productID, int newQuantity) throws Exception;
    public ArrayList<Product> getAllProducts() throws Exception;
    public Product getProduct(UUID productID) throws Exception;
}
