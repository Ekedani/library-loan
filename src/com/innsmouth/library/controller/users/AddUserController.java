package com.innsmouth.library.controller.users;

import com.innsmouth.library.controller.books.IntTextFormatter;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.facade.BookRepositoryFacade;
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
    private final BookRepositoryFacade facade;

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



    public AddUserController(BookRepositoryFacade facade, Stage stage) {
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
        UserQuery query = createQuery();
        facade.addNewBook(query);
    }

    private UserQuery createQuery() {
        UserQuery result = new UserQuery();
        result.setName(getName());
        result.setAddress(getAddress());
        result.setNumber(getNumber());
        result.setEmail(getEmail());
        result.setPassword(getPassword());
        result.setId();

        return result;
    }

    private String getName(){
        String name = userAdd_name.getText();
        return name;
    }

    private String getAddress(){
        String address = userAdd_address.getText();
        return address;
    }

    private long getNumber(){
        long number = Long.parseLong(userAdd_number.getText());
        return number;
    }

    private String getEmail(){
        String email = userAdd_email.getText();
        return email;
    }

    private String getPassword(){
        String password = userAdd_password.getText();
        return password;
    }

    private void logAddStatements() {
        System.out.println("name: " + getName());
        System.out.println("address: " + getAddress());
        System.out.println("number: " + getNumber());
        System.out.println("email: " + getEmail());
    }

}


