package com.innsmouth.library.domain.repository.api;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.repository.base.Repository;

import java.util.List;

public interface BookRepository extends Repository {
    long insertBook(Book book);

    boolean updateBook(Book book);

    boolean deleteBook(Book book);

    Book selectBookById(long ID);

    List<Book> findBookByProperty(Book query);

    List<Book> findAll();
}
