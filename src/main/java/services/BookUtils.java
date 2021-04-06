package services;

import model.BookRequest;

import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

public class BookUtils {
    public BookRequest createRequest() {
        String symbols = "abcdefghijklmnopqrstuvwxyz";
        String hotelName = new Random().ints(5, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        int randomStars = 1 + (int) (Math.random() * 5);
        return new BookRequest(Date.from(Instant.now()), hotelName, randomStars);
    }
}
