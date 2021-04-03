package com.almasb.libraryloan;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_title.setCellValueFactory(new PropertyValueFactory<>(""));
    }
}
