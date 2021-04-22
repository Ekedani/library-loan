package com.innsmouth.library.controller.login;

import com.innsmouth.library.controller.books.BookCatalogController;
import com.innsmouth.library.controller.main.MainMenuController;
import com.innsmouth.library.data.dataobject.LibrarianReader;
import com.innsmouth.library.data.dataobject.UserReader;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.domain.facade.LoginFacade;
import com.innsmouth.library.domain.repository.derby.DerbyBookRepository;
import com.innsmouth.library.domain.repository.derby.DerbyLoginRepository;
import com.sun.javaws.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static final String LAYOUT = "/com/innsmouth/library/view/login/login.fxml";

    public static LoginController createInstance(Stage stage) {
        return new LoginController(sCreateFacade(), stage);
    }

    private static LoginFacade sCreateFacade() {
        return new LoginFacade(new DerbyLoginRepository());
    }

    private final Stage stage;
    private final LoginFacade facade;

    UserSingleton userSingleton = UserSingleton.getInstance();

    @FXML
    private TextField login_email;
    @FXML
    private TextField login_password;

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

    }

    private void onReaderCredentialsValid() {
        UserReader reader = new UserReader(getID());
        userSingleton.setCurrentUser(reader);
        onLoginFinish();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MainMenuController.LAYOUT));
        loader.setControllerFactory(t -> MainMenuController.createInstance(stage));

        return new Scene(loader.load());
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
