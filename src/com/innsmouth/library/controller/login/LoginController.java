package com.innsmouth.library.controller.login;

import com.innsmouth.library.data.dataobject.UserReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    UserSingleton userSingleton = UserSingleton.getInstance();

    @FXML
    private Button login_library;
    @FXML
    private Button login_user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onUserLogin(ActionEvent actionEvent) {
        UserReader reader = new UserReader(getID());
        userSingleton.setCurrentUser(reader);
    }

    private long getID() {
        return 1;
    }

    @FXML
    private void onLibraryLogin(ActionEvent actionEvent) {

    }
}
