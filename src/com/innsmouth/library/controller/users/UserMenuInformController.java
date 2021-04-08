package com.innsmouth.library.controller.users;

import com.innsmouth.library.controller.login.UserSingleton;
import com.innsmouth.library.domain.facade.UserRepositoryFacade;
import com.innsmouth.library.domain.repository.derby.DerbyUserRepository;
import javafx.event.ActionEvent;
import com.innsmouth.library.data.dataobject.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuInformController implements Initializable {
    public final static String LAYOUT = "/com/innsmouth/library/view/users/user_menu_inform.fxml";

    public static UserMenuInformController createInstance(Stage stage, long selectedUserId) {
        return new UserMenuInformController(sCreateFacade(), stage, selectedUserId);
    }

    private static UserRepositoryFacade sCreateFacade() {
        return new UserRepositoryFacade(new DerbyUserRepository());
    }


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

    public UserMenuInformController(UserRepositoryFacade facade, Stage stage, long selectedUserId) {
        this.stage = stage;
        this.selectedUserId = selectedUserId;
        this.facade = facade;
    }

    public void onEditUser(ActionEvent actionEvent) {
        try {
            goToUsersEdit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(ActionEvent actionEvent) {
        try {
            goToCatalog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabels();
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


    public void goToUsersEdit() throws Exception {
        Scene scene = generateUserEditScene(selectedUserId);
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateUserEditScene(long selectedId) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserMenuEditController.LAYOUT));
        loader.setControllerFactory(t -> UserMenuEditController.createInstance(stage,selectedId));

        return new Scene(loader.load());
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
