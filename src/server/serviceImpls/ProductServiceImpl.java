package server.serviceImpls;

import common.models.Product;
import server.repos.ProductRepo;
import common.services.ProductService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProductServiceImpl extends UnicastRemoteObject implements ProductService {

    private ProductRepo productRepo = new ProductRepo();

    public ProductServiceImpl() throws RemoteException {
    }

    @Override
    public void addNewProduct(Product product) throws Exception {
        productRepo.insertProduct(product);
    }

    @Override
    public void removeProduct(UUID productID) throws Exception {
        productRepo.deleteProductByID(productID);
    }

    @Override
    public boolean updateItemDescription(UUID productID, String newDesc) throws Exception {
        return productRepo.updateProductDescriptionByID(productID, newDesc);
    }

    @Override
    public boolean updateItemPrice(UUID productID, double newPrice) throws Exception {
        return productRepo.updateProductPriceByID(productID, newPrice);
    }

    @Override
    public boolean updateItemQuantity(UUID productID, int newQuantity) throws Exception {
        return productRepo.updateProductQuantityByID(productID, newQuantity);
    }

    @Override
    public ArrayList<Product> getAllProducts() throws Exception {
        ArrayList<Product> allProducts = new ArrayList<>();
        ConcurrentHashMap<UUID, Product> productMap = productRepo.getALlProducts();
        for (ConcurrentHashMap.Entry<UUID, Product> productEntry: productMap.entrySet()) {
            allProducts.add(productEntry.getValue());
        }
        return allProducts;
    }

    @Override
    public Product getProduct(UUID productID) throws Exception {
        return productRepo.getProductByID(productID);
    }
}
