package com.innsmouth.library.controller.users;

import com.innsmouth.library.controller.books.IntTextFormatter;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
import com.innsmouth.library.domain.facade.UserRepositoryFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {
    private final UserRepositoryFacade facade;

    @FXML
    private TextField userAdd_name;
    @FXML
    private TextField userAdd_address;
    @FXML
    private TextField userAdd_number;
    @FXML
    private TextField userAdd_email;
    @FXML
    private TextField userAdd_password;


    public AddUserController(UserRepositoryFacade facade, Stage stage) {
        this.facade = facade;
        stage.setOnCloseRequest(e -> facade.close());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }

    private void configUI() {
        configIntTextField();
    }

    private void configIntTextField() {
        userAdd_number.setTextFormatter(new IntTextFormatter());
    }

    @FXML
    public void onAddUser(ActionEvent actionEvent) {
        logAddStatements();
        addUser();
    }


    private void addUser() {
        User query = create();
        facade.addUser(query);
    }

    private User create() {
        User result = new User();
        result.setName(getName());
        result.setAddress(getAddress());
        result.setPhoneNumber(getNumber());
        result.setEmail(getEmail());
        result.setPassword(getPassword());

        return result;
    }

    private String getName(){
        return userAdd_name.getText();
    }

    private String getAddress(){
        return userAdd_address.getText();
    }

    private long getNumber(){
        return Long.parseLong(userAdd_number.getText());
    }

    private String getEmail(){
        return userAdd_email.getText();
    }

    private String getPassword(){
        return userAdd_password.getText();
    }

    private void logAddStatements() {
        System.out.println("name: " + getName());
        System.out.println("address: " + getAddress());
        System.out.println("number: " + getNumber());
        System.out.println("email: " + getEmail());
    }

}


