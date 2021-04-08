package com.innsmouth.library.controller.users;

import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.facade.UserRepositoryFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserCatalogController implements Initializable {

    @FXML
    private TextField searchUser_name;
    @FXML
    private TextField searchUser_email;
    @FXML
    private TextField searchUser_address;
    @FXML
    private TextField searchUser_number;

    @FXML
    private TableView<User> user_table;
    @FXML
    private TableColumn<User, String> col_name;
    @FXML
    private TableColumn<User, String> col_email;
    @FXML
    private TableColumn<User, String> col_address;
    @FXML
    private TableColumn<User, Long> col_number;

    private final UserRepositoryFacade facade;

    private final ObservableList<User> userObservableList = FXCollections.observableArrayList();

    public UserCatalogController(UserRepositoryFacade facade, Stage stage) {
        this.facade = facade;
        stage.setOnCloseRequest(e -> facade.close());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        populateTableViewWithAllUsers();
    }

    private void configUI() {
        configTableView();
    }

    private void configTableView() {
        user_table.setItems(userObservableList);

        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_number.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    private String getName() {
        return searchUser_name.getText();
    }

    private String getEmail() {
        return searchUser_email.getText();
    }

    private Long getNumber() {
        return Long.parseLong(searchUser_number.getText());
    }

    private String getAddress() {
        return searchUser_address.getText();
    }

    private void logSearchStatements() {
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Number: " + getNumber());
        System.out.println("Address: " + getAddress());
    }

    private UserQuery createQuery() {
        UserQuery result = new UserQuery();
        result.setName(getName());
        result.setEmail(getEmail());
        result.setAddress(getAddress());
        result.setNumber(getNumber());

        return result;
    }

    private void queryUsers() {
        UserQuery query = createQuery();
        List<User> resultList = facade.search(query);

        userObservableList.setAll(resultList);
        resultList.forEach(System.out::println);
    }

    private void populateTableViewWithAllUsers() {
        List<User> allUsers = facade.findAll();
        allUsers.forEach(System.out::println);

        userObservableList.setAll(allUsers);
        user_table.setItems(userObservableList);
    }

    public void onSearch(/*ActionEvent actionEvent*/) {
        logSearchStatements();
        queryUsers();
    }

    public void onShowAll(/*ActionEvent actionEvent*/) {
        populateTableViewWithAllUsers();
    }
}
