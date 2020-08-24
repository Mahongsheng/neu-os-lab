package Lab1;
import java.util.concurrent.Semaphore;

/**
 * @version 1.1
 *
 * @author 马洪升 20175188
 *
 * @since 2018.12.14
 *
 *
 * This is a warehouse class and we use semaphore method here.
 *
 * We create a not full semaphore in case the warehouse is full.
 * If so, we will wait the consumer to consume a product and then we produce again.
 *
 * We create a not empty semaphore in case the warehouse is empty.
 * If so, we will wait the producer to produce a product and then we consume again.
 *
 * There is also a core semaphore to ensure: one time one operation.
 */

public class Warehouse {

    // We create a lock in case the warehouse is full.
    final Semaphore notFull = new Semaphore(10);
    // We create a lock in case the warehouse is empty.
    final Semaphore notEmpty = new Semaphore(0);
    // We create a core lock ensure one time is only a produce operation or consume operation.
    final Semaphore core = new Semaphore(1);
    // We set the capacity of the warehouse.
    final Object[] items = new Object[10];
    // We set the position of put and take. We use count to express the number of the product in warehouse.
    int putnum, takenum, count = 0;

    /*
    This is a method to put a product into the warehouse.
     */
    public void put(Object o) throws InterruptedException {
        // NotFull lock gets the power.
        notFull.acquire();
        // Core lock gets the power.
        core.acquire();
        try {
            items[putnum] = o;
            // If the position of put is going to beyond the size of the warehouse, we initialize it.
            if(++putnum == items.length){
                putnum = 0;
                count++;
            }
        }finally {
            // Release the core lock.
            core.release();
            // Increase the power of notEmpty in order to consume more products.
            notEmpty.release();
        }
    }

    /*
    This is a method to take a product from the warehouse.
     */
    public Object take() throws InterruptedException {
        // NotEmpty lock gets the power.
        notEmpty.acquire();
        // Core lock gets the power.
        core.acquire();
        try {
            Object o = items[takenum];
            items[takenum] = null;
            if (++takenum == items.length)
                takenum = 0;
                count--;
            return o;
        } finally {
            // Release the core lock.
            core.release();
            // Increase the power of notFull in order to add more products.
            notFull.release();
        }
    }
}

