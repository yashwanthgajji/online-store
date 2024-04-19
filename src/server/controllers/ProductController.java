package server.controllers;

import common.Requests;
import server.models.Product;
import server.serviceImpls.ProductServiceImpl;
import server.services.ProductService;

import java.util.List;
import java.util.UUID;

public class ProductController implements MainController {

    private final ProductService productService;

    public ProductController() {
        this.productService = new ProductServiceImpl();
    }

    @Override
    public Object handleRequest(Requests request, String[] args) {
        switch (request) {
            case View_All_Products: {
                List<Product> products = productService.getAllProducts();
                StringBuilder sb = new StringBuilder();
                sb.append("********* PRODUCTS *********\n");
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
                return sb.toString();
            }
            case Add_New_Product: {
                Product product = new Product(args[0], args[1], Double.parseDouble(args[2]), Integer.parseInt(args[3]));
                productService.addNewProduct(product);
                break;
            }
            case Remove_Product: {
                productService.removeProduct(UUID.fromString(args[0]));
                break;
            }
            case Update_Item_Description: {
                productService.updateItemDescription(UUID.fromString(args[0]), args[1]);
                break;
            }
            case Update_Item_Price: {
                productService.updateItemPrice(UUID.fromString(args[0]), Double.parseDouble(args[1]));
                break;
            }
            case Update_Item_Quantity: {
                productService.updateItemQuantity(UUID.fromString(args[0]), Integer.parseInt(args[1]));
                break;
            }
        }
        return null;
    }
}
