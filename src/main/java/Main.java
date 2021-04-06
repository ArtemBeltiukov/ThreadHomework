import services.BlockingQueue;
import services.BookUtils;
import services.RequestRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Logger log = Logger.getLogger(Main.class.getName());
        Thread consumer = new Thread(() -> {
            blockingQueue.putTask(new RequestRunner(new BookUtils().createRequest()));
        });

        Thread producer = new Thread(() -> {
            Runnable task = blockingQueue.getTask();
            if (task != null)
                task.run();
        });

        for (int i = 0; i < 9; i++) {
            executorService.execute(producer);
        }
        for (int i = 0; i < 6; i++) {
            executorService.execute(consumer);
        }

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.info("interrupted" + e);
            Thread.currentThread().interrupt();
            ;
        }
        executorService.shutdown();
    }
}
