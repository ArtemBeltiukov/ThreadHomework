package model;

import java.util.Date;

public class BookRequest {

    private Date date;
    private String hotel;
    private int stars;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public int getStars() {
        return stars;
    }

    @Override
    public String toString() {
        return "BookRequest{" +
                "date=" + date +
                ", hotel='" + hotel + '\'' +
                ", stars=" + stars +
                '}';
    }

    public void setStars(int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Stars must be between 1 and 5");
        }
        this.stars = stars;
    }

    public BookRequest(Date date, String hotel, int stars) {
        this.date = date;
        this.hotel = hotel;
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Stars must be between 1 and 5");
        }
        this.stars = stars;
    }
}
