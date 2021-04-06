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

public class BookMenuEditController implements Initializable {
    private final BookRepositoryFacade facade;

    @FXML
    private Label menuInform_Ammount;
    @FXML
    private Label addBook_title;
    @FXML
    private Label menuInform_Year;
    @FXML
    private Label menuInform_Genre;
    @FXML
    private Label menuInform_Author;
    @FXML
    private Label menuInform_Title;
    @FXML
    private Label menuInform_Annotation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }

}

