package com.almasb.libraryloan.booklist;

import com.almasb.libraryloan.DerbyBookRepository;
import com.almasb.libraryloan.RepositoryFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class TEst extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("book_list.fxml"));
        loader.setControllerFactory(t -> createController(stage));

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    private TableController createController(Stage stage) {
        return new TableController(createLibrary(), stage);
    }

    private RepositoryFacade createLibrary() {
        return new RepositoryFacade(new DerbyBookRepository());
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
