package com.innsmouth.library.data.dataobject;

public class Order {
    private long orderId;
    private long readerId;
    private String book;
    private String take_date;
    private String return_date;

    public long getReaderId() {
        return readerId;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setReaderId(long readerId) {
        this.readerId = readerId;
    }

    public String getTake_date() {
        return take_date;
    }

    public void setTake_date(String take_date) {
        this.take_date = take_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
