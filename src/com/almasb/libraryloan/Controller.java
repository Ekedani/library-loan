package com.almasb.libraryloan;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {


    @FXML
    private ListView<Book> listView;

    private BookRepositoryFacade model;

    public Controller(BookRepositoryFacade model, Stage stage) {
        this.model = model;
        stage.setOnCloseRequest(e -> model.close());
    }

    public void initialize() {
        //choiceBox.getItems().setAll(BookSearchType.values());
        //choiceBox.getSelectionModel().selectFirst();
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
