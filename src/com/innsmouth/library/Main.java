package com.innsmouth.library;

import com.innsmouth.library.controller.books.AddBookController;
import com.innsmouth.library.controller.books.BookMenuEditController;
import com.innsmouth.library.controller.books.BookMenuInformController;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.controller.books.BookCatalogController;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/books/book_catalog.fxml"));
        loader.setControllerFactory(t -> createController(stage));

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    private BookCatalogController createController(Stage stage) {
        return new BookCatalogController(createLibrary(), stage);
    }

    private BookRepositoryFacade createLibrary() {
        return new BookRepositoryFacade(new DerbyBookRepository());
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
