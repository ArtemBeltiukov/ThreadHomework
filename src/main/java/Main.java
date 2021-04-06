import services.BlockingQueue;
import services.BookUtils;
import services.RequestRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

    public static final int NUM_OF_CONSUMERS = 10;
    public static final int NUM_OF_PRODUCERS = 10;

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        ExecutorService executorService1 = Executors.newFixedThreadPool(20);

        Logger log = Logger.getLogger(Main.class.getName());

        for (int i = 0; i < NUM_OF_PRODUCERS; i++) {
            executorService.execute(() -> blockingQueue.putTask(new RequestRunner(new BookUtils().createRequest())));
        }

        for (int i = 0; i < NUM_OF_CONSUMERS; i++) {
            executorService1.execute(blockingQueue::getTask);
        }

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            executorService1.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.info("interrupted" + e);
            Thread.currentThread().interrupt();
        }
        executorService1.shutdown();
        executorService.shutdown();
    }
}
