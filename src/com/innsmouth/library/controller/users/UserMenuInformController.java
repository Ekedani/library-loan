package com.innsmouth.library.controller.users;

import com.innsmouth.library.domain.facade.UserRepositoryFacade;
import javafx.event.ActionEvent;
import com.innsmouth.library.data.dataobject.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    public UserMenuInformController(Stage stage, long selectedUserId, UserRepositoryFacade facade) {
        this.stage = stage;
        this.selectedUserId = selectedUserId;
        this.facade = facade;
    }

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
        userMenu_PhoneNumber.setText(String.valueOf(result.getPhoneNumber()));
        userMenu_Email.setText(result.getEmail());
        userMenu_Address.setText(result.getAddress());
        userMenu_ID.setText(String.valueOf(result.getReaderId()));
        userMenu_Name.setText(result.getName());
    }
}
