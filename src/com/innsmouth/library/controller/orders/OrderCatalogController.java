package com.innsmouth.library.controller.orders;

import com.innsmouth.library.controller.login.UserSingleton;
import com.innsmouth.library.controller.users.UserMenuInformController;
import com.innsmouth.library.data.dataobject.Order;
import com.innsmouth.library.domain.facade.OrdersFacade;
import com.innsmouth.library.domain.repository.derby.DerbyOrderRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderCatalogController implements Initializable {
    public final static String LAYOUT = "/com/innsmouth/library/view/orders/order_catalog.fxml";
    UserSingleton userSingleton = UserSingleton.getInstance();

    public static OrderCatalogController createInstance(Stage stage) {
        return new OrderCatalogController(sCreateFacade(), stage);
    }

    private static OrdersFacade sCreateFacade() {
        return new OrdersFacade(new DerbyOrderRepository());
    }

    @FXML
    private TextField searchOrder_userId;
    @FXML
    private TextField searchOrder_bookId;
    @FXML
    private Button searchOrder_searchBtn;
    @FXML
    private CheckBox searchOrder_returned;

    @FXML
    private TableView<Order> order_table;
    @FXML
    private TableColumn<Order, Long> col_userId;
    @FXML
    private TableColumn<Order, Long> col_bookId;
    @FXML
    private TableColumn<Order, String> col_tookDate;
    @FXML
    private TableColumn<Order, String> col_returnDate;
    @FXML
    private TableColumn<Order, String> col_returned;

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
    }

    private void configUI() {
        configTableView();
    }

    private void configTableView() {
        order_table.setItems(orderObservableList);

        col_userId.setCellValueFactory(new PropertyValueFactory<>    ("ordererId"));
        col_bookId.setCellValueFactory(new PropertyValueFactory<>    ("bookId"));
        col_tookDate.setCellValueFactory(new PropertyValueFactory<>  ("tookDate"));
        col_returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        col_returned.setCellValueFactory(new PropertyValueFactory<>  ("returned"));
    }

    private void logSearchStatements() {
        System.out.println("Search btn triggered, searching order:");
        System.out.println("User Id: " + getUserId());
        System.out.println("Book Id: " + getBookId());
        System.out.println("Returned: " + getReturned());
    }

    private Order createQuery() {
        Order result = new Order();
        result.setBookId(getBookId());
        result.setOrdererId(Long.parseLong(getUserId()));
        result.setReturned(String.valueOf(getReturned()));

        return result;
    }

    private void queryOrders() {
        Order query = createQuery();
        List<Order> resultList = facade.search(query);

        orderObservableList.setAll(resultList);
        resultList.forEach(System.out::println);
    }

    private void populateTableViewWithAllOrders() {
        List<Order> allUsers = facade.findAll();
        allUsers.forEach(System.out::println);

        orderObservableList.setAll(allUsers);
        order_table.setItems(orderObservableList);
    }

    public void onSearch(ActionEvent actionEvent) {
        logSearchStatements();
        queryOrders();
    }

    public void onShowAll(ActionEvent actionEvent) {
        populateTableViewWithAllOrders();
    }

    @FXML
    private void onMorePressed(ActionEvent actionEvent) {
        try {
            goToOrdersInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private long getSelectedId() {
        Order selectedUser = order_table.getSelectionModel().getSelectedItem();
        logSelectedOrder(selectedUser);

        if (selectedUser == null) return NOTHING_SELECTED;

        return selectedUser.getUniqueId();
    }

    private void logSelectedOrder(Order selectedUser) {
        System.out.println("selectedUser is ");
        System.out.println(selectedUser);

    }

    public void goToOrdersInfo() throws Exception {
        final long selectedId = getSelectedId();
        if (selectedId == NOTHING_SELECTED) {
            return;
        }

        Scene scene = generateOrdersInfoScene(selectedId);
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateOrdersInfoScene(long selectedId) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OrderInformController.LAYOUT));
        loader.setControllerFactory(t -> OrderInformController.createInstance(stage,selectedId));

        return new Scene(loader.load());
    }

   /* private Scene generateUsersInfoScene(long selectedId) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserMenuInformController.LAYOUT));
        loader.setControllerFactory(t -> UserMenuInformController.createInstance(stage, selectedId));

        return new Scene(loader.load());
    }*/

    private boolean getReturned() {
        return searchOrder_returned.isSelected();
    }

    private String getBookId() {
        return searchOrder_bookId.getText();
    }

    private String getUserId() {
        return searchOrder_userId.getText();
    }
}
