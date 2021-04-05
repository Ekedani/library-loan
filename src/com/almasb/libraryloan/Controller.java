package com.almasb.libraryloan;

import com.almasb.libraryloan.booklist.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ChoiceBox<BookSearchType> choiceBox;

    @FXML
    private ListView<Book> listView;

    private RepositoryFacade model;

    public Controller(RepositoryFacade model, Stage stage) {
        this.model = model;
        stage.setOnCloseRequest(e -> model.close());
    }

    public void initialize() {
        choiceBox.getItems().setAll(BookSearchType.values());
        choiceBox.getSelectionModel().selectFirst();
    }

    public void onSearch(ActionEvent event) {
        String param = ((TextField)event.getSource()).getText();
        //listView.getItems().setAll(model.search());
    }

    public void onLoan() {
        model.loanBook(listView.getSelectionModel().getSelectedItem().getBookID());
    }

    public void onReturn() {
        model.returnBook(listView.getSelectionModel().getSelectedItem().getBookID());
    }
}
