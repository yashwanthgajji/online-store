package common.services;

import common.models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface ProductService extends Remote {
    public void addNewProduct(Product product) throws RemoteException;
    public void removeProduct(UUID productID) throws RemoteException;
    public boolean updateItemDescription(UUID productID, String newDesc) throws RemoteException;
    public boolean updateItemPrice(UUID productID, double newPrice) throws RemoteException;
    public boolean updateItemQuantity(UUID productID, int newQuantity) throws RemoteException;
    public List<Product> getAllProducts() throws RemoteException;
    public Product getProduct(UUID productID) throws RemoteException;
}
