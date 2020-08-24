/**
 * @version 1.1
 *
 * @author 马洪升 20175188
 *
 * @since 2018.12.14
 *
 * Introduction:
 * This is a program that achieve producer and consumer by using semaphore. We have 5 classes.
 * This class is for testing the whole program.
 * Product class is an entity class. It defines product.
 * Producer class has producer name and its warehouse.
 * Consumer class has consumer name and its warehouse.
 * Warehouse class has a list. It has method: put and take.
 *
 * How are you ensure the upper/lower bounds:
 * I use a 'Not Full' lock to ensure the upper bound. A 'Not Empty' lock to ensure the lower bound.
 * Once we put a product into warehouse. 'Not Full' lock acquire. 'Not Empty' lock release.
 * Once we take a product from warehouse. 'Not Full' lock release. 'Not Empty' lock acquire.
 * Thus, when 'Not Full' lock acquire 10 times, it will wait consumer to consume.
 * When 'Not Empty' lock has never release, it will wait producer to produce.
 * Above all, we can ensure the upper/lower bounds.
 *
 * How are you preventing deadlock:
 * Because we use 'Not Full' lock and 'Not Empty' lock, it doesn't exist deadlock.
 *
 * How are you protecting critical sections:
 * I also use a 'Core' lock to ensure there is only one operation one time.
 * Produce or consume. When two or more threads need to access a shared
 * resource at the same time, the system needs a synchronization mechanism to ensure that only one thread at
 * a time uses the resource.
 *
 * Suggestion:
 * Maybe I can put all this class into a class to improve the readability.
 *
 * Thanks for your teaching!!!!
 *
 */

package Lab1;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSemaphore {
    // Create a warehouse as buffer.
    static Warehouse buffer = new Warehouse();
    public static void main(String[] args){
        // Create a thread pool.
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new Producer("Apple Factory", buffer));
        executor.execute(new Consumer("User", buffer));
        executor.shutdown();
    }
}