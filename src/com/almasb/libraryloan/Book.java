package com.almasb.libraryloan;

import java.util.Arrays;
import java.util.List;

public class Book {

    private long bookID;
    private String title;
    private String genre;
    private List<String> authors;
    private int publishYear;
    private String annotation;
    private int copiesGiven;
    private int copiesPresent;
    //private boolean available;

    public String getAuthors() {
        String result = "";
        for (String author : authors)
            result += author + "_";
        return result.substring(0, result.length() - 1);
    }

    public void setAuthors(String authors) {
        this.authors = Arrays.asList(authors.split("_"));
    }

    public final long getBookID() {
        return bookID;
    }

    public final void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final int getPublishYear() {
        return publishYear;
    }

    public final void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

   /* public final boolean isAvailable() {
        return available;
    }

    public final void setAvailable(boolean available) {
        this.available = available;
    }*/

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Book [uniqueID=");
        builder.append(bookID);
        builder.append(", name=");
        builder.append(title);
        builder.append(", authors=");
        builder.append(authors);
        builder.append(", publishedYear=");
        builder.append(publishYear);
        //builder.append(", available=");
        //builder.append(available);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authors == null) ? 0 : authors.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + publishYear;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (authors == null) {
            if (other.authors != null)
                return false;
        } else if (!authors.equals(other.authors))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (publishYear != other.publishYear)
            return false;
        return true;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public int getCopiesGiven() {
        return copiesGiven;
    }

    public void setCopiesGiven(int copiesGiven) {
        this.copiesGiven = copiesGiven;
    }

    public int getCopiesPresent() {
        return copiesPresent;
    }

    public void setCopiesPresent(int copiesPresent) {
        this.copiesPresent = copiesPresent;
    }
}
