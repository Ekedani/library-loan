package com.innsmouth.library.controller.books;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {
    private final BookRepositoryFacade facade;

    @FXML
    private TextField addBook_title;
    @FXML
    private TextField addBook_author;
    @FXML
    private TextField addBook_genre;
    @FXML
    private TextField addBook_year;
    @FXML
    private TextField addBook_amount;
    @FXML
    private TextArea addBook_annotation;



    public AddBookController(BookRepositoryFacade facade, Stage stage) {
        this.facade = facade;
        stage.setOnCloseRequest(e -> facade.close());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }

    private void configUI() {
        configIntTextField();
    }

    private void configIntTextField() {
        addBook_year.setTextFormatter(new IntTextFormatter());
        addBook_amount.setTextFormatter(new IntTextFormatter());
    }

    @FXML
    public void onAddBook(ActionEvent actionEvent) {
        logAddStatements();
        addBook();
    }


    private void addBook() {
        Book query = create();
        facade.addNewBook(query);
    }

    private Book create() {
        Book result = new Book();
        result.setAuthor(getAuthorText());
        result.setTitle(getTitleText());
        result.setPublishYear(getYearText());
        result.setGenre(getGenreText());
        result.setAnnotation(getAnnotationText());
        result.setCopiesPresent(getAmount());
        result.setCopiesGiven(0);

        return result;
    }

    private int getAmount() {
        String amountText = addBook_amount.getText();
        if (amountText.isEmpty()){
            return 0;
        }
        return Integer.parseInt(amountText);
    }

    private String getAnnotationText() {
        return addBook_annotation.getText();
    }

    private String getTitleText() {
        return addBook_title.getText();
    }

    private String getAuthorText() {
        return addBook_author.getText();
    }

    private int getYearText() {
        String yearText = addBook_year.getText();
        if (yearText.isEmpty()){
            return 0;
        }
        return Integer.parseInt(yearText);
    }

    private String getGenreText() {
        return addBook_genre.getText();
    }

    private void logAddStatements() {
        System.out.println("title: " + getTitleText());
        System.out.println("author: " + getAuthorText());
        System.out.println("year: " + getYearText());
        System.out.println("genre: " + getGenreText());
    }

}

