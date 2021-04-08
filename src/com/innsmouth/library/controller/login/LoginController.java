package com.innsmouth.library.controller.login;

import com.innsmouth.library.controller.books.BookCatalogController;
import com.innsmouth.library.data.dataobject.LibrarianReader;
import com.innsmouth.library.data.dataobject.UserReader;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.domain.facade.LoginFacade;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private final Stage stage;
    private final LoginFacade facade;

    UserSingleton userSingleton = UserSingleton.getInstance();

    @FXML
    private Label login_email;
    @FXML
    private Label login_password;

    public LoginController(LoginFacade facade,Stage stage) {
        this.stage = stage;
        this.facade = facade;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { /* empty */}

    @FXML
    private void onLibraryLogin(ActionEvent actionEvent) {
        boolean credentialsValid = validateCredentials();

        if (credentialsValid){
            onLibraryCredentialsValid();
        } else {
            onInvalidCredentials();
        }
    }

    private void onLibraryCredentialsValid() {
        LibrarianReader reader = new LibrarianReader(getID());
        userSingleton.setCurrentUser(reader);

        onLoginFinish();
    }

    @FXML
    private void onUserLogin(ActionEvent actionEvent) {
        boolean credentialsValid = validateCredentials();

        if (credentialsValid){
            onReaderCredentialsValid();
        } else {
            onInvalidCredentials();
        }

        onLoginFinish();
    }

    private void onReaderCredentialsValid() {
        UserReader reader = new UserReader(getID());
        userSingleton.setCurrentUser(reader);
    }

    private void onLoginFinish() {
        goToMainScreen();
    }

    private void goToMainScreen() {
        try {
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeScene() throws Exception {
        Scene scene = generateScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/innsmouth/library/view/books/book_catalog.fxml"));
        loader.setControllerFactory(t -> createController(stage));

        return new Scene(loader.load());
    }

    private BookCatalogController createController(Stage stage) {
        return new BookCatalogController(createFacade(), stage);
    }

    private BookRepositoryFacade createFacade() {
        return new BookRepositoryFacade(new DerbyBookRepository());
    }

    private boolean validateCredentials() {
       return facade.credentialsValid(getEmail(), getPassword());
    }

    private String getPassword() {
        return login_password.getText();
    }

    private String getEmail() {
        return login_email.getText();
    }

    private long getID() {
        return 1; //todo add user repo
    }

    private void onInvalidCredentials() {
        showError("Invalid credentials");
    }

    private void showError(String msg) {
        System.out.println(msg);
    }


}
