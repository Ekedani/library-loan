package com.innsmouth.library.controller.books;

import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.data.dataobject.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.ResourceBundle;

public class BookMenuInformController implements Initializable {
    private final BookRepositoryFacade facade;

    @FXML
    private TextField bookMenu_title;
    @FXML
    private TextField bookMenu_author;
    @FXML
    private TextField bookMenu_year;
    @FXML
    private TextField bookMenu_genre;
    @FXML
    private TextField bookMenu_amount;
    @FXML
    private TextArea bookMenu_annotation;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }
}
