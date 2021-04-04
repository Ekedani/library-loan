package com.almasb.libraryloan.booklist;

import com.almasb.libraryloan.BookSearchType;
import com.almasb.libraryloan.DerbyBookDAO;
import com.almasb.libraryloan.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    private TableView<Book>   book_table;
    @FXML
    private TableColumn<Book, String> col_title;
    @FXML
    private TableColumn<Book, String> col_genre;
    @FXML
    private TableColumn<Book, String> col_author;
    @FXML
    private TableColumn<Book, Integer> col_year;

    ObservableList<Book> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String param = "Paul";
        Library model = new Library(new DerbyBookDAO());
        List<Book> bookList = (model.search(BookSearchType.AUTHOR, param));

        list.setAll(bookList);

        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("publishYear"));

        book_table.setItems(list);
    }
}
