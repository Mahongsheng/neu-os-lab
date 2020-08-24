package Lab1;

/**
 * @version 1.1
 *
 * @author 马洪升 20175188
 *
 * @since 2018.12.13
 *
 * This is a product class, it has a product name.
 */

public class Product {
    private String productName;

    public Product(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                '}';
    }
}
