package com.innsmouth.library.controller.books;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class BookMenuInformController implements Initializable {
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


    public BookMenuInformController(BookRepositoryFacade facade) {
        this.facade = facade;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabels();
    }

    public void setLabels(){
        Book result;
        result = facade.selectBookByID(2);
        menuInform_Ammount.setText(String.valueOf(result.getCopiesPresent()));
        menuInform_Year.setText(String.valueOf(result.getPublishYear()));
        menuInform_Genre.setText(result.getGenre());
        menuInform_Author.setText(result.getAuthor());
        menuInform_Annotation.setText(result.getAnnotation());
        menuInform_Title.setText(result.getTitle());
    }

    public void onAddBook(ActionEvent actionEvent) {
    }

    public void onOrderBook(ActionEvent actionEvent) {
    }

    public void onEditBook(ActionEvent actionEvent) {
    }
}
