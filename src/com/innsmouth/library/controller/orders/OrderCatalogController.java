package com.innsmouth.library.controller.orders;

import com.innsmouth.library.controller.login.UserSingleton;
import com.innsmouth.library.data.dataobject.Order;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.domain.facade.OrdersFacade;
import com.innsmouth.library.domain.repository.derby.DerbyOrderRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderCatalogController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    /*public final static String LAYOUT = "/com/innsmouth/library/view/users/user_catalog.fxml";
    UserSingleton userSingleton = UserSingleton.getInstance();
    @FXML
    private TextField searchOrder_userId;
    @FXML
    private TextField searchOrder_bookId;
    @FXML
    private Button searchOrder_searchBtn;

    @FXML
    private TableView<Order> order_table;
    @FXML
    private TableColumn<Order, String> col_userId;
    @FXML
    private TableColumn<Order, String> col_bookId;
    @FXML
    private TableColumn<Order, String> col_tookDate;
    @FXML
    private TableColumn<Order, String> col_returnDate;
    @FXML
    private TableColumn<Order, String> col_returned;

    public static OrderCatalogController createInstance(Stage stage) {
        return new OrderCatalogController(sCreateFacade(), stage);
    }

    private static OrdersFacade sCreateFacade() {
        return new OrdersFacade(new DerbyOrderRepository());
    }

    private final OrdersFacade facade;
    private final Stage stage;
    private final long NOTHING_SELECTED = -1;

    private long selectedId = NOTHING_SELECTED;

    private final ObservableList<Order> orderObservableList = FXCollections.observableArrayList();

    public OrderCatalogController(OrdersFacade facade, Stage stage) {
        this.facade = facade;
        this.stage = stage;
        stage.setOnCloseRequest(e -> facade.close());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        populateTableViewWithAllOrders();
    }

    private void configUI() {
        configTableView();
    }

    private void configTableView() {
        order_table.setItems(orderObservableList);

        col_userId.setCellValueFactory(new PropertyValueFactory<>    ("readerId"));
        col_bookId.setCellValueFactory(new PropertyValueFactory<>    ("bookId;"));
        col_tookDate.setCellValueFactory(new PropertyValueFactory<>  ("take_date"));
        col_returnDate.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        col_returned.setCellValueFactory(new PropertyValueFactory<>  ("returned"));
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
        result.setNumber(getNumber());

        return result;
    }

    private void queryUsers() {
        User query = create();
        List<User> resultList = facade.search(query);

        orderObservableList.setAll(resultList);
        resultList.forEach(System.out::println);
    }

    private void populateTableViewWithAllOrders() {
        List<User> allUsers = facade.findAll();
        allUsers.forEach(System.out::println);

        orderObservableList.setAll(allUsers);
        order_table.setItems(orderObservableList);
    }

    public void onSearch(*//*ActionEvent actionEvent*//*) {
        logSearchStatements();
        queryUsers();
    }

    public void onShowAll(*//*ActionEvent actionEvent*//*) {
        populateTableViewWithAllOrders();
    }

    @FXML
    private void onMorePressed(ActionEvent actionEvent) {
        updateSelectedId();

        try {
            //goToUsersInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSelectedId() {

    }

    private long getSelectedId() {
        User selectedUser = order_table.getSelectionModel().getSelectedItem();
        logSelectedBook(selectedUser);

        if (selectedUser == null) return NOTHING_SELECTED;

        return selectedUser.getReaderId();
    }

    private void logSelectedBook(User selectedUser) {
        System.out.println("selectedUser is ");
        System.out.println(selectedUser);

    }*/

  /*  public void goToUsersInfo() throws Exception {
        final long selectedId = getSelectedId();
        if (selectedId == NOTHING_SELECTED) {
            return;
        }

        Scene scene = generateUsersInfoScene(selectedId);
        stage.setScene(scene);
        stage.show();
    }*/

    /*private Scene generateUsersInfoScene(long selectedId) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserMenuInformController.LAYOUT));
        loader.setControllerFactory(t -> UserMenuInformController.createInstance(stage,selectedId));

        return new Scene(loader.load());
    }*/
}
