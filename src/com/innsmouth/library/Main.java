package com.innsmouth.library;


import com.innsmouth.library.controller.login.LoginController;
import com.innsmouth.library.controller.main.MainMenuController;
import com.innsmouth.library.controller.users.AddUserController;
import com.innsmouth.library.controller.users.UserCatalogController;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.domain.facade.LoginFacade;
import com.innsmouth.library.domain.facade.UserRepositoryFacade;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import com.innsmouth.library.domain.repository.derby.DerbyLoginRepository;
import com.innsmouth.library.domain.repository.derby.DerbyUserRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/login/login.fxml"));
        loader.setControllerFactory(t -> createController(stage));

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }


    private LoginController createController(Stage stage) {
        return new LoginController(new LoginFacade(new DerbyLoginRepository()), stage);
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
