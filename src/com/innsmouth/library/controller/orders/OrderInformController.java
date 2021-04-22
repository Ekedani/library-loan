package com.innsmouth.library.controller.orders;

import com.innsmouth.library.controller.users.UserCatalogController;
import com.innsmouth.library.controller.users.UserMenuEditController;
import com.innsmouth.library.data.dataobject.Order;
import com.innsmouth.library.domain.facade.OrdersFacade;
import com.innsmouth.library.domain.repository.derby.DerbyOrderRepository;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Queue;
import java.util.ResourceBundle;

public class OrderInformController implements Initializable {
    public final static String LAYOUT = "/com/innsmouth/library/view/orders/order_inform.fxml";

    public static OrderInformController createInstance(Stage stage, long selectedUserId) {
        return new OrderInformController(sCreateFacade(), stage, selectedUserId);
    }

    private static OrdersFacade sCreateFacade() {
        return new OrdersFacade(new DerbyOrderRepository());
    }

    private final Stage stage;
    private final long selectedOrderId;
    private final OrdersFacade facade;

    @FXML
    private Label orderId;
    @FXML
    private Label userId;
    @FXML
    private Label tookDate;
    @FXML
    private Label returnDate;
    @FXML
    private Label isReturned;
    @FXML
    private Label bookId;

    public OrderInformController(OrdersFacade facade, Stage stage, long selectedOrderId) {
        this.stage = stage;
        this.selectedOrderId = selectedOrderId;
        this.facade = facade;
    }

    public void onOrderReturned(ActionEvent actionEvent) {
        facade.returnOrder(selectedOrderId);
        isReturned.setText("true");
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
        Scene scene = generateOrdersCatalogScene();
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateOrdersCatalogScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OrderCatalogController.LAYOUT));
        loader.setControllerFactory(t -> OrderCatalogController.createInstance(stage));

        return new Scene(loader.load());
    }


    public void goToUsersEdit() throws Exception {
        Scene scene = generateUserEditScene(selectedOrderId);
        stage.setScene(scene);
        stage.show();
    }

    private Scene generateUserEditScene(long selectedId) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UserMenuEditController.LAYOUT));
        loader.setControllerFactory(t -> UserMenuEditController.createInstance(stage, selectedId));

        return new Scene(loader.load());
    }

    private void setLabels() {
        Order result;
        result = facade.selectOrderByID(selectedOrderId);
        orderId.setText      (String.valueOf(result.getUniqueId()));
        userId.setText       (String.valueOf(result.getOrdererId()));
        tookDate.setText     (result.getTookDate());
        returnDate.setText   (result.getReturnDate());
        isReturned.setText   (result.getReturned());
        bookId.setText       (result.getBookId());
    }

    @FXML
    private void onOrderDelete(ActionEvent actionEvent) {
        Order order = new Order();
        order.setUniqueId(selectedOrderId);
        facade.deleteOrder(order);
        goToCatalog();
    }
}
