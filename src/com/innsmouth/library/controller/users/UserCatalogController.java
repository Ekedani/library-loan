package com.innsmouth.library.controller.users;

import com.innsmouth.library.controller.login.UserSingleton;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.domain.facade.UserRepositoryFacade;
import com.innsmouth.library.domain.repository.derby.DerbyUserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserCatalogController implements Initializable {
    public final static String LAYOUT = "/com/innsmouth/library/view/users/user_catalog.fxml";
    UserSingleton userSingleton = UserSingleton.getInstance();

    public static UserCatalogController createInstance(Stage stage) {
        return new UserCatalogController(sCreateFacade(), stage);
    }

    private static UserRepositoryFacade sCreateFacade() {
        return new UserRepositoryFacade(new DerbyUserRepository());
    }

    private final UserRepositoryFacade facade;
    private final Stage stage;
    private final long NOTHING_SELECTED = -1;

    private long selectedId = NOTHING_SELECTED;

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


    private final ObservableList<User> userObservableList = FXCollections.observableArrayList();

    public UserCatalogController(UserRepositoryFacade facade, Stage stage) {
        this.facade = facade;
        this.stage = stage;
        stage.setOnCloseRequest(e -> facade.close());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
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
        String text = searchUser_number.getText();

        if (text.isEmpty()) return 0l;

        return Long.parseLong(text);
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

    private User create() {
        User result = new User();
        result.setName(getName());
        result.setEmail(getEmail());
        result.setAddress(getAddress());
        result.setPhoneNumber(getNumber());

        return result;
    }

    private void queryUsers() {
        User query = create();
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

    @FXML
    private void onMorePressed(ActionEvent actionEvent) {
        updateSelectedId();

        try {
            goToUsersInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSelectedId() {

    }

    private long getSelectedId() {
        User selectedUser = user_table.getSelectionModel().getSelectedItem();
        logSelectedBook(selectedUser);

        if (selectedUser == null) return NOTHING_SELECTED;

        return selectedUser.getReaderId();
    }

    private void logSelectedBook(User selectedUser) {
            System.out.println("selectedUser is ");
            System.out.println(selectedUser);

    }

    public void goToUsersInfo() throws Exception {
        final long selectedId = getSelectedId();
        if (selectedId == NOTHING_SELECTED) {
            return;
        }

        Scene scene = generateUsersInfoScene(selectedId);
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateUsersInfoScene(long selectedId) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserMenuInformController.LAYOUT));
        loader.setControllerFactory(t -> UserMenuInformController.createInstance(stage,selectedId));

        return new Scene(loader.load());
    }
}
