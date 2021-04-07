package com.innsmouth.library.domain.facade;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.domain.repository.api.BookRepository;

import java.util.List;

public class BookRepositoryFacade {

    private final BookRepository repository;

    public BookRepositoryFacade(BookRepository repository) {
        this.repository = repository;
        try {
            repository.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBook(BookQuery bookQuery) {
        repository.updateBook(bookQuery);
    }

    public void addNewBook(BookQuery bookQuery) {
        repository.insertBook(bookQuery);
    }

    public void deleteBook(BookQuery bookQuery) {repository.deleteBook(bookQuery);}

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
