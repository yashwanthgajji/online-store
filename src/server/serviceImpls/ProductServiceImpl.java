package server.serviceImpls;

import common.models.Product;
import server.repos.ProductRepo;
import common.services.ProductService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductServiceImpl extends UnicastRemoteObject implements ProductService {

    private final ProductRepo productRepo = ProductRepo.getProductRepoInstance();

    public ProductServiceImpl() throws RemoteException {
    }

    @Override
    public void addNewProduct(Product product) throws RemoteException {
        productRepo.insertProduct(product);
    }

    @Override
    public void removeProduct(UUID productID) throws RemoteException {
        productRepo.deleteProductByID(productID);
    }

    @Override
    public boolean updateItemDescription(UUID productID, String newDesc) throws RemoteException {
        return productRepo.updateProductDescriptionByID(productID, newDesc);
    }

    @Override
    public boolean updateItemPrice(UUID productID, double newPrice) throws RemoteException {
        return productRepo.updateProductPriceByID(productID, newPrice);
    }

    @Override
    public boolean updateItemQuantity(UUID productID, int newQuantity) throws RemoteException {
        return productRepo.updateProductQuantityByID(productID, newQuantity);
    }

    @Override
    public List<Product> getAllProducts() throws RemoteException {
        List<Product> allProducts = new ArrayList<>();
        Map<UUID, Product> productMap = productRepo.getALlProducts();
        for (Map.Entry<UUID, Product> productEntry: productMap.entrySet()) {
            allProducts.add(productEntry.getValue());
        }
        return allProducts;
    }

    @Override
    public Product getProduct(UUID productID) throws RemoteException {
        return productRepo.getProductByID(productID);
    }
}
