package com.innsmouth.library.data.dataobject;

public class Order {
    private long uniqueId;
    private long   ordererId;
    private String bookId;
    private String tookDate;
    private String returnDate;
    private String returned;

    public long getOrdererId() {
        return ordererId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setOrdererId(long ordererId) {
        this.ordererId = ordererId;
    }

    public String getTookDate() {
        return tookDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + uniqueId +
                ", readerId=" + ordererId +
                ", bookId='" + bookId + '\'' +
                ", take_date='" + tookDate + '\'' +
                ", return_date='" + returnDate + '\'' +
                ", returned='" + returned + '\'' +
                '}';
    }

    public void setTookDate(String tookDate) {
        this.tookDate = tookDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }
}
