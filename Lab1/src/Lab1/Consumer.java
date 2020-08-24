package Lab1;

import java.util.Random;

/**
 * @version 1.1
 *
 * @author 马洪升 20175188
 *
 * @since 2018.12.13
 *
 * This is a consumer class, it has consumer name and warehouse.
 *
 * @param
 */

public class Consumer  implements Runnable {
    private String consumerName;
    private Warehouse warehouse;

    public Consumer(String consumerName, Warehouse warehouse) {
        this.consumerName = consumerName;
        this.warehouse = warehouse;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    @Override
    public void run() {
        int count = 0;
        while (true){
            try {
                // We consume a product.
                Product takeProduct = (Product)warehouse.take();
                System.out.println("**Consume** " + getConsumerName() + " consumes " + takeProduct.getProductName());
                count++;
                Random random = new Random();
                Thread.sleep(random.nextInt(2000));
                // When we consume 5 products, then we terminate.
                if (count == 20){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
