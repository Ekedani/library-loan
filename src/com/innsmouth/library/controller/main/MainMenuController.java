package com.innsmouth.library.controller.main;

import com.innsmouth.library.controller.books.BookCatalogController;
import com.innsmouth.library.controller.users.UserCatalogController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {
    public static final String LAYOUT = "/com/innsmouth/library/view/main/main_menu.fxml";

    public MainMenuController(Stage stage) {
        this.stage = stage;
    }

    public static MainMenuController createInstance(Stage stage) {
        return new MainMenuController(stage);
    }

    private final Stage stage;

    @FXML
    private void onUsersPressed(ActionEvent actionEvent) {
        try {
            goToUsersCatalog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBooksPressed(ActionEvent actionEvent) {
        try {
            goToBooksCatalog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onOrdersPressed(ActionEvent actionEvent) {
    }


    public void goToUsersCatalog() throws Exception {
        Scene scene = generateUsersCatalogScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateUsersCatalogScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserCatalogController.LAYOUT));
        //todo make it return users' catalog, not books'
        loader.setControllerFactory(t -> UserCatalogController.createInstance(stage));

        return new Scene(loader.load());
    }


    public void goToBooksCatalog() throws Exception {
        Scene scene = generateBookCatalogScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateBookCatalogScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BookCatalogController.LAYOUT));
        loader.setControllerFactory(t -> BookCatalogController.createInstance(stage));
        return new Scene(loader.load());
    }
}
