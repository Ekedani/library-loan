package com.innsmouth.library.controller.books;

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

import java.net.URL;
import java.util.ResourceBundle;

public class BookMenuInformController implements Initializable {
    public final static String LAYOUT = "/com/innsmouth/library/view/books/book_menu_inform.fxml";

    private final Stage stage;
    private final long selectedBookId;
    private final BookRepositoryFacade facade;

    @FXML
    private Label menuInform_Amount;
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
    @FXML
    private Button bookMenu_backBtn;


    public BookMenuInformController(BookRepositoryFacade facade, Stage stage, long selectedBookId) {
        this.facade = facade;
        stage.setOnCloseRequest(e -> facade.close());
        this.stage = stage;
        this.selectedBookId = selectedBookId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabels();
    }

    public void setLabels(){
        Book result;
        result = facade.selectBookByID(selectedBookId);
        menuInform_Amount.setText(String.valueOf(result.getCopiesPresent()));
        menuInform_Year.setText(String.valueOf(result.getPublishYear()));
        menuInform_Genre.setText(result.getGenre());
        menuInform_Author.setText(result.getAuthor());
        menuInform_Annotation.setText(result.getAnnotation());
        menuInform_Title.setText(result.getTitle());
    }

    @FXML
    private void onMoreClicked(ActionEvent actionEvent) {
        try {
            goBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack() throws Exception {
        Scene scene = generateEditScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateEditScene() throws Exception {
        Stage stage = (Stage) bookMenu_backBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/books/book_catalog.fxml"));
        loader.setControllerFactory(t -> createEditBookController(stage));

        return new Scene(loader.load());
    }

    private BookCatalogController createEditBookController(Stage stage) {
        return new BookCatalogController(createFacade(), stage, selectedBookId);
    }

    private BookRepositoryFacade createFacade() {
        return new BookRepositoryFacade(new DerbyBookRepository());
    }

    public void onBackPressed(ActionEvent actionEvent) {
        try {
            goBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onOrderBook(ActionEvent actionEvent) {
    }

    public void onEditBook(ActionEvent actionEvent) {
        try {
            goToEdit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToEdit() throws Exception {
        Scene scene = generateEditScene(selectedBookId);
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateEditScene(final long selectedId) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/books/book_menu_edit.fxml"));
        loader.setControllerFactory(t -> createEditBookController(stage, selectedId));

        return new Scene(loader.load());
    }

    private BookMenuEditController createEditBookController(Stage stage, final long selectedId) {
        return new BookMenuEditController(createFacade(), stage, selectedId);
    }
}
