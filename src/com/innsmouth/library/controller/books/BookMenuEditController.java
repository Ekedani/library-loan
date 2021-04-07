package com.innsmouth.library.controller.books;

import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class BookMenuEditController implements Initializable {
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

    public BookMenuEditController(BookRepositoryFacade facade) {
        this.facade = facade;
    }

    private void configUI() {
        configIntTextField();
    }

    private void configIntTextField() {
        bookMenu_year.setTextFormatter(new IntTextFormatter());
        bookMenu_amount.setTextFormatter(new IntTextFormatter());
    }

    private void deleteBook(){
        BookQuery query = createQuery();
        facade.deleteBook(query);
    }

    private void editBook() {
        BookQuery query = createQuery();
        facade.updateBook(query);
    }



    private BookQuery createQuery() {
        BookQuery result = new BookQuery();
        result.setBookID(101);
        result.setAuthor(getAuthorText());
        result.setTitle(getTitleText());
        result.setPublishYear(getYearText());
        result.setGenre(getGenreText());
        result.setAnnotation(getAnnotationText());
        result.setCopiesPresent(getAmount());
        return result;
    }

    private int getAmount() {
        String amountText = bookMenu_amount.getText();
        if (amountText.isEmpty()){
            return 0;
        }
        return Integer.parseInt(amountText);
    }

    private String getAnnotationText() {
        return bookMenu_annotation.getText();
    }

    private String getTitleText() {
        return bookMenu_title.getText();
    }

    private String getAuthorText() {
        return bookMenu_author.getText();
    }

    private int getYearText() {
        String yearText = bookMenu_year.getText();
        if (yearText.isEmpty()){
            return 0;
        }
        return Integer.parseInt(yearText);
    }

    private String getGenreText() {
        return bookMenu_genre.getText();
    }

    private void logAddStatements() {
        System.out.println("title: " + getTitleText());
        System.out.println("author: " + getAuthorText());
        System.out.println("year: " + getYearText());
        System.out.println("genre: " + getGenreText());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }


    public void onConfirmBook(ActionEvent actionEvent) {
        logAddStatements();
        editBook();
    }

    public void onBack(ActionEvent actionEvent) {

    }

    public void onDelete(ActionEvent actionEvent) {
        logAddStatements();
        deleteBook();
    }
}

