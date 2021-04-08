package com.innsmouth.library;

import com.innsmouth.library.controller.login.LoginController;
import com.innsmouth.library.domain.facade.LoginFacade;
import com.innsmouth.library.domain.repository.derby.DerbyLoginRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(LoginController.LAYOUT));

        loader.setControllerFactory(t -> LoginController.createInstance(stage));

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
