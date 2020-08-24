package Lab1;

import java.util.Random;

/**
 * @version 1.1
 *
 * @author 马洪升 20175188
 *
 * @since 2018.12.13
 *
 * This is a producer class, it has producer name and warehouse.
 */

public class Producer implements Runnable{
    private String producerName;
    private Warehouse warehouse;

    public Producer(String producerName, Warehouse warehouse) {
        this.producerName = producerName;
        this.warehouse = warehouse;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    @Override
    public void run() {
        int i = 1;
        int count = 0;
        while (true){
            try {
                Product newProduct = new Product("MacBook" + i);
                // Put the product into warehouse.
                warehouse.put(newProduct);
                System.out.println("**Produce** " + newProduct.getProductName());
                i++;
                count++;
                // We use a random time to sleep.
                Random random = new Random();
                Thread.sleep(random.nextInt(2000));
                // When we produce 5 product then we terminate.
                if (count == 20){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
