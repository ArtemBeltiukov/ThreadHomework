package services;

import model.BookRequest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestRunner implements Runnable {

    private BookRequest bookRequest;
    private Logger log = Logger.getLogger(getClass().getName());

    public RequestRunner(BookRequest bookRequest) {
        this.bookRequest = bookRequest;
    }

    @Override
    public void run() {

        log.log(Level.INFO, bookRequest.toString());
        try {
            waitAnswer();
        } catch (InterruptedException e) {
            log.log(Level.WARNING, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }

    private void waitAnswer() throws InterruptedException {
        Thread.sleep(100);
    }
}
