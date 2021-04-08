package com.innsmouth.library.controller.users;

import com.innsmouth.library.data.query.UserQuery;
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
    private Label userMenu_ID;
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
        userMenu_ID.setText(String.valueOf(selectedUserID));
        //configUI(); //TODO: Сделать некоторые поля интовыми
    }

    private void setUserInfo() {
        //TODO: фикс фасада + перходы по кнопкам
        User selectedUser = facade.selectBookByID(selectedUserID);
        userMenu_Name.setText(selectedUser.getName());
        userMenu_Address.setText(selectedUser.getAddress());
        userMenu_PhoneNumber.setText(String.valueOf(selectedUser.getPhoneNumber()));
        userMenu_Email.setText(selectedUser.getEmail());
    }

    private UserQuery createQuery() {
        UserQuery result = new UserQuery();
        result.setId(selectedBookId);
        result.setName(getNameText());
        result.setAddress(getAddressText());
        result.setEmail(getEmailText());
        result.setNumber(getNumberText());
        return result;
    }

    private String getNameText() {
        return userMenu_Name.getText();
    }

    private String getEmailText() {
        return userMenu_Email.getText();
    }

    private String getAddressText() {
        return userMenu_Email.getText();
    }

    private int getNumberText() {
        String numberText = userMenu_PhoneNumber.getText();
        if (numberText.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(numberText);
    }
}
