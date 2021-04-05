package com.almasb.libraryloan;

import com.almasb.libraryloan.booklist.Book;

import java.util.List;

public class RepositoryFacade {

    private final BookRepository repository;

    public RepositoryFacade(BookRepository repository) {
        this.repository = repository;
        try {
            repository.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewBook(String name, String author, int year) {
        Book book = new Book();
        //book.setAvailable(true);
        book.setTitle(name);
        book.setAuthor(author);
        book.setPublishYear(year);

        repository.insertBook(book);
    }

    public List<Book> findAll(){
        return repository.findAll();
    }
    
    public void loanBook(long uniqueID) {
        /*List<Book> books = repository.findBookByProperty(BookSearchType.ID);
        if (books.size() > 0) {
            //books.get(0).setAvailable(false);
            repository.updateBook(books.get(0));
        }*/
    }

    public void returnBook(long uniqueID) {
        /*List<Book> books = repository.findBookByProperty(BookSearchType.ID);
        if (books.size() > 0) {
            //books.get(0).setAvailable(true);
            repository.updateBook(books.get(0));
        }*/
    }

    public List<Book> search(BookQuery query) {
        return repository.findBookByProperty(query);
    }

    public void close() {
        try {
            repository.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
