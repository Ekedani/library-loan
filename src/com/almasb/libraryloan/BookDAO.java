package com.almasb.libraryloan;

import java.util.List;

public interface BookDAO extends DAO {
   long insertBook(Book book);
   boolean updateBook(Book book);
   boolean deleteBook(Book book);

   List<Book> findBookByProperty(BookSearchType searchType, Object value);
   List<Book> findAll();
}
