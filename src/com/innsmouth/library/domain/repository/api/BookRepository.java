package com.innsmouth.library.domain.repository.api;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.domain.repository.base.Repository;

import java.util.List;

public interface BookRepository extends Repository {
    long insertBook(BookQuery book);

    boolean updateBook(BookQuery book);

    boolean deleteBook(BookQuery book);

    Book selectBookById(long ID);

    List<Book> findBookByProperty(BookQuery query);

    List<Book> findAll();
}
