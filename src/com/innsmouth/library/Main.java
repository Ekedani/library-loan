package com.innsmouth.library;

import com.innsmouth.library.controller.books.AddBookController;
<<<<<<<HEAD
import com.innsmouth.library.controller.books.BookMenuEditController;
import com.innsmouth.library.controller.books.BookMenuInformController;
=======
import com.innsmouth.library.controller.login.LoginController;
>>>>>>>login
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.controller.books.BookCatalogController;
import com.innsmouth.library.domain.facade.LoginFacade;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import com.innsmouth.library.domain.repository.derby.DerbyLoginRepository;
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
        return new LoginController(createLibrary(), stage);
    }

    private LoginFacade createLibrary() {
        return new LoginFacade(new DerbyLoginRepository());
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
