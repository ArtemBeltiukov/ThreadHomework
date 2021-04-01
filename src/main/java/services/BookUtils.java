package services;

import model.BookRequest;

import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BookUtils {
    private final Logger log = Logger.getLogger(getClass().getName());
    private static AtomicInteger threadN = new AtomicInteger(0);


    public BookRequest createRequest() {
        String symbols = "abcdefghijklmnopqrstuvwxyz";
        String hotelName = new Random().ints(5, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        int randomStars = 1 + (int) (Math.random() * 5);
        return new BookRequest(Date.from(Instant.now()), hotelName, randomStars);
    }

    public synchronized void sendRequest(BookRequest bookRequest) {
        int curPos = threadN.incrementAndGet();
        while (curPos > 5) {
            try {
                log.info(curPos + " start waiting");
                wait();
                curPos-=5;
                log.info(curPos + " wake up");
            } catch (InterruptedException e) {
                log.log(Level.WARNING, "Interrupted!", e);
                Thread.currentThread().interrupt();
            }
        }

        log.log(Level.INFO, bookRequest.toString());
        try {
            waitAnswer();
        } catch (InterruptedException e) {
            log.log(Level.WARNING, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
        log.info(curPos + " notify");

        notifyAll();
    }

    private void waitAnswer() throws InterruptedException {
        Thread.sleep(5000);
    }

}
