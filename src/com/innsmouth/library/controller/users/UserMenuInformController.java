package com.innsmouth.library.controller.users;

import com.innsmouth.library.domain.facade.UserRepositoryFacade;
import javafx.event.ActionEvent;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.domain.repository.derby.DerbyUserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuInformController implements Initializable {

    private final Stage stage;
    private final long selectedUserId;
    private final UserRepositoryFacade facade;

    @FXML
    private Label userMenu_PhoneNumber;
    @FXML
    private Label userMenu_Email;
    @FXML
    private Label userMenu_Address;
    @FXML
    private Label userMenu_ID;
    @FXML
    private Label userMenu_Name;

    public void onEditUser(ActionEvent actionEvent) {
    }

    public void onBackPressed(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabels();
    }

    private void setLabels() {
        User result;
        result = facade.selectUserByID(selectedUserId);
        userMenu_PhoneNumber.setText(String.valueOf(result.getNumber()));
        userMenu_Email.setText(result.getEmail());
        userMenu_Address.setText(result.getAddress());
        userMenu_ID.setText(String.valueOf(result.getId()));
        userMenu_Name.setText(result.getName());
    }
}
