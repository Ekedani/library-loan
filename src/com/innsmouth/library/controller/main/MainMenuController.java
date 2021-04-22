package com.innsmouth.library.controller.main;

import com.innsmouth.library.controller.books.BookCatalogController;
import com.innsmouth.library.controller.login.UserSingleton;
import com.innsmouth.library.controller.orders.OrderCatalogController;
import com.innsmouth.library.controller.users.UserCatalogController;
import com.innsmouth.library.controller.users.UserMenuInformController;
import com.innsmouth.library.data.dataobject.LibrarianReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    public static final String LAYOUT = "/com/innsmouth/library/view/main/main_menu.fxml";
    private final UserSingleton userSingleton = UserSingleton.getInstance();

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
        try {
            goToOrdersCatalog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToOrdersCatalog() throws Exception {
        Scene scene = generateOrdersCatalogScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateOrdersCatalogScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OrderCatalogController.LAYOUT));
        loader.setControllerFactory(t -> OrderCatalogController.createInstance(stage));

        return new Scene(loader.load());
    }

    public void goToUsersCatalog() throws Exception {
        Scene scene;

        if (userSingleton.getCurrentUser() instanceof LibrarianReader) {
            scene = generateUsersCatalogScene();
        } else {
            scene = generateUserInfoScene();
        }
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateUserInfoScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserMenuInformController.LAYOUT));
        loader.setControllerFactory(t -> UserMenuInformController.createInstance(stage, userSingleton.getCurrentUser().getId()));

        return new Scene(loader.load());
    }

    private Scene generateUsersCatalogScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserCatalogController.LAYOUT));
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
