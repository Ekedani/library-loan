package com.innsmouth.library.controller.books;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookMenuEditController implements Initializable {
    private final Stage stage;
    private final long selectedBookId;
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

    public BookMenuEditController(BookRepositoryFacade facade, Stage stage, long selectedBookId) {
        this.facade = facade;
        this.selectedBookId = selectedBookId;
        this.stage = stage;
        stage.setOnCloseRequest(t -> facade.close());
    }

    private void configUI() {
        configIntTextField();
        setBookInfo();
    }

    private void setBookInfo() {
        Book selectedBook = facade.selectBookByID(selectedBookId);
        bookMenu_author.setText(selectedBook.getAuthor());
        bookMenu_genre.setText(selectedBook.getGenre());
        bookMenu_year.setText(String.valueOf(selectedBook.getPublishYear()));
        bookMenu_title.setText(selectedBook.getTitle());
        bookMenu_amount.setText(String.valueOf(selectedBook.getCopiesPresent()));
        bookMenu_annotation.setText(selectedBook.getAnnotation());
    }

    private void configIntTextField() {
        bookMenu_year.setTextFormatter(new IntTextFormatter());
        bookMenu_amount.setTextFormatter(new IntTextFormatter());
    }

    private void deleteBook(){
        Book query = create();
        facade.deleteBook(query);

        try {
            goToCatalog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editBook() {
        Book query = create();
        facade.updateBook(query);
    }

    private Book create() {
        Book result = new Book();
        result.setBookID(selectedBookId);
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
        try {
            goBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        logAddStatements();
        deleteBook();
    }

    private void goToCatalog() throws IOException {
        Scene scene = generateCatalogScene(selectedBookId);
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateCatalogScene(long selectedBookId) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/books/book_catalog.fxml"));
        loader.setControllerFactory(t -> createCatalogBookController(stage, selectedBookId));

        return new Scene(loader.load());
    }

    public void goBack() throws Exception {
        Scene scene = generateInformScene(selectedBookId);
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateInformScene(final long selectedId) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/books/book_menu_inform.fxml"));
        loader.setControllerFactory(t -> createEditBookController(stage, selectedId));

        return new Scene(loader.load());
    }

    private BookMenuInformController createEditBookController(Stage stage, final long selectedId) {
        return new BookMenuInformController(createFacade(), stage, selectedId);
    }

    private BookCatalogController createCatalogBookController(Stage stage, final long selectedId) {
        return new BookCatalogController(createFacade(), stage, selectedId);
    }

    private BookRepositoryFacade createFacade() {
        return new BookRepositoryFacade(new DerbyBookRepository());
    }

}

