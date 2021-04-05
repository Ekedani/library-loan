package com.almasb.libraryloan;

import com.almasb.libraryloan.booklist.Book;

import java.util.List;

public interface BookRepository extends Repository {
   long insertBook(Book book);
   boolean updateBook(Book book);
   boolean deleteBook(Book book);

   List<Book> findBookByProperty(BookQuery query);
   List<Book> findAll();
}
