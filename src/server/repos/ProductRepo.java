package server.repos;

import server.models.Product;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepo {
    private static ProductRepo productRepoInstance = null;
    public static synchronized ProductRepo getProductRepoInstance() {
        if (productRepoInstance == null) {
            productRepoInstance = new ProductRepo();
        }
        return productRepoInstance;
    }

    private final Map<UUID, Product> products;

    private ProductRepo() {
        products = new ConcurrentHashMap<>();
        Product s1 = new Product("Shirt", "Shirt", 13.56, 11);
        this.products.put(s1.getProductID(), s1);
        Product s2 = new Product("Hammer", "Hammer", 35.99, 7);
        this.products.put(s2.getProductID(), s2);
    }

    public void insertProduct(Product product) {
        products.put(product.getProductID(), product);
    }

    public Product getProductByID(UUID productID) {
        return products.get(productID);
    }

    public void deleteProductByID(UUID productID) {
        products.remove(productID);
    }

    public boolean updateProductDescriptionByID(UUID productID, String newDescription) {
        if (products.containsKey(productID)) {
            Product product = products.get(productID);
            product.setDescription(newDescription);
            products.put(productID, product);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateProductPriceByID(UUID productID, double newPrice) {
        if (products.containsKey(productID)) {
            Product product = products.get(productID);
            product.setPrice(newPrice);
            products.put(productID, product);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateProductQuantityByID(UUID productID, int newQuantity) {
        if (products.containsKey(productID)) {
            Product product = products.get(productID);
            product.setQuantityAvailable(newQuantity);
            products.put(productID, product);
            return true;
        } else {
            return false;
        }
    }

    public Map<UUID, Product> getALlProducts() {
        return products;
    }
}
