package com.almasb.libraryloan.booklist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TEst extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("book_list.fxml"));
        loader.setControllerFactory(t -> new TableController());

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
