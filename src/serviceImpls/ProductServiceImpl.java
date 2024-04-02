package serviceImpls;

import models.Product;
import repos.ProductRepo;
import services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo = ProductRepo.getProductRepoInstance();

    @Override
    public void addNewProduct(Product product) {
        productRepo.insertProduct(product);
    }

    @Override
    public void removeProduct(UUID productID) {
        productRepo.deleteProductByID(productID);
    }

    @Override
    public boolean updateItemDescription(UUID productID, String newDesc) {
        return productRepo.updateProductDescriptionByID(productID, newDesc);
    }

    @Override
    public boolean updateItemPrice(UUID productID, double newPrice) {
        return productRepo.updateProductPriceByID(productID, newPrice);
    }

    @Override
    public boolean updateItemQuantity(UUID productID, int newQuantity) {
        return productRepo.updateProductQuantityByID(productID, newQuantity);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        Map<UUID, Product> productMap = productRepo.getALlProducts();
        for (Map.Entry<UUID, Product> productEntry: productMap.entrySet()) {
            allProducts.add(productEntry.getValue());
        }
        return allProducts;
    }

    @Override
    public Product getProduct(UUID productID) {
        return productRepo.getProductByID(productID);
    }
}
