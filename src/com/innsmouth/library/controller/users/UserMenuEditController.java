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

public class UserMenuEditController implements Initializable{

    @FXML
    private TextField userMenu_PhoneNumber;
    @FXML
    private TextField userMenu_Email;
    @FXML
    private TextField userMenu_Address;
    @FXML
    private TextField userMenu_ID;
    @FXML
    private TextField userMenu_Name;

    public void onDeletePressed(ActionEvent actionEvent) {

    }

    public void onBackPressed(ActionEvent actionEvent) {

    }

    public void onConfirm(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
