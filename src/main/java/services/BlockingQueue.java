package services;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockingQueue {

    private ArrayList<Runnable> tasks = new ArrayList<>(5);
    private Logger log = Logger.getLogger(getClass().getName());

    public int getSize() {
        return tasks.size();
    }

    public synchronized Runnable getTask() {
        log.info("get element");
        while (tasks.isEmpty() && !Thread.interrupted()) {
            try {
                log.info("waiting. Is empty");
                wait(10000);
                log.info("continue");
            } catch (InterruptedException e) {
                log.log(Level.INFO, "Interrupted!", e);
                Thread.currentThread().interrupt();
            }
            if (tasks.isEmpty()) {
                Thread.currentThread().interrupt();
            }
        }

        if (!tasks.isEmpty()) {
            Runnable task = tasks.get(0);
            try {
                waitAnswer();
            } catch (InterruptedException e) {
                log.info("interrupted" + e);
                Thread.currentThread().interrupt();
            }
            tasks.remove(task);
            notifyAll();
            return task;
        }
        return null;
    }

    public synchronized void putTask(Runnable task) {
        log.info("put element");
        while (getSize() >= 5) {
            try {
                log.info("waiting. More than 5 elements! ");
                wait(10000);
            } catch (InterruptedException e) {
                log.info("interrupted" + e);
                Thread.currentThread().interrupt();
            }
            if (getSize() >= 5) {
                Thread.currentThread().interrupt();
            }
        }
        if (getSize() < 5) {
            tasks.add(task);
            notifyAll();
        }
    }

    private void waitAnswer() throws InterruptedException {
        Thread.sleep(100);
    }
}
