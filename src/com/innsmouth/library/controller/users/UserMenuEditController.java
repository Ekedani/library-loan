package com.innsmouth.library.controller.users;

import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.facade.UserRepositoryFacade;
import javafx.event.ActionEvent;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.domain.repository.derby.DerbyUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuEditController implements Initializable {
    public final static String LAYOUT = "/com/innsmouth/library/view/users/user_menu_edit.fxml";

    public static UserMenuEditController createInstance(Stage stage, long selectedUserId) {
        return new UserMenuEditController(sCreateFacade(), stage, selectedUserId);
    }

    private static UserRepositoryFacade sCreateFacade() {
        return new UserRepositoryFacade(new DerbyUserRepository());
    }

    private final Stage stage;
    private final long selectedUserId;
    private final UserRepositoryFacade facade;

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

    public UserMenuEditController(UserRepositoryFacade sCreateFacade, Stage stage, long selectedUserId) {
        this.facade = sCreateFacade;
        this.stage = stage;
        this.selectedUserId = selectedUserId;
    }

    public void onDeletePressed(ActionEvent actionEvent) {
        facade.deleteUser(selectedUserId);
        try {
            goToCatalog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToCatalog() throws Exception {
        Scene scene = generateUserCatalogScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateUserCatalogScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserCatalogController.LAYOUT));
        loader.setControllerFactory(t -> UserCatalogController.createInstance(stage));

        return new Scene(loader.load());
    }


    public void onBackPressed(ActionEvent actionEvent) {
        try {
            goToUserInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToUserInfo() throws Exception {
        Scene scene = generateUserInfoScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateUserInfoScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserMenuInformController.LAYOUT));
        loader.setControllerFactory(t -> UserMenuInformController.createInstance(stage, selectedUserId));

        return new Scene(loader.load());
    }

    public void onConfirm(ActionEvent actionEvent) {
        editUser();
    }

    private void editUser() {
        UserQuery query = createQuery();
        facade.updateUser(query);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserInfo();
        //configUI();
    }

    private void setUserInfo() {
        User selectedUser = facade.selectUserByID(selectedUserId);
        userMenu_ID.setText(String.valueOf(selectedUser.getReaderId()));
        userMenu_Name.setText(selectedUser.getName());
        userMenu_Address.setText(selectedUser.getAddress());
        userMenu_PhoneNumber.setText(String.valueOf(selectedUser.getPhoneNumber()));
        userMenu_Email.setText(selectedUser.getEmail());
    }

    private UserQuery createQuery() {
        UserQuery result = new UserQuery();
        result.setId(selectedUserId);
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
        return userMenu_Address.getText();
    }

    private int getNumberText() {
        String numberText = userMenu_PhoneNumber.getText();
        if (numberText.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(numberText);
    }
}
