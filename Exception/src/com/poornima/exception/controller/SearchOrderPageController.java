package com.virtusa.exception.controller;


import com.virtusa.exception.db.DBConnection;
import com.virtusa.exception.util.SearchOrderTM;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchOrderPageController {
    public TextField txtSearch;
    public TableView<SearchOrderTM> tblSearchOrder;
    public AnchorPane root;
    private ArrayList<SearchOrderTM> orderArray = new ArrayList<>();

    public void initialize(){
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        tblSearchOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblSearchOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblSearchOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblSearchOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblSearchOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("orderTotal"));

        loadAllOrderDetails();

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<SearchOrderTM> searchOrder = tblSearchOrder.getItems();
                searchOrder.clear();
                for(SearchOrderTM orders : orderArray)
                    if(orders.getOrderId().contains(newValue)|| orders.getOrderDate().toString().contains(newValue)||
                    orders.getCustomerId().contains(newValue)||orders.getCustomerName().contains(newValue)){
                        searchOrder.add(orders);
                    }
            }
        });

    }

    private void loadAllOrderDetails() {
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement
                    ("SELECT o.OrderID,o.OrderDate,o.CustID,c.CustName,SUM(i.UnitPrice*od.OrderQTY)AS Total FROM (((\n" +
                    "    orders o INNER JOIN orderdetail od ON od.OrderID=o.OrderID)INNER JOIN customer c ON c.CustID=o.CustID)\n" +
                    "    INNER JOIN  item i ON i.ItemCode=od.ItemCode) GROUP BY o.OrderID");
            ResultSet rst = pstm.executeQuery();
            ObservableList<SearchOrderTM> orders = tblSearchOrder.getItems();
            orders.clear();
            while (rst.next()){
                String orderId = rst.getString(1);
                Date orderDate = rst.getDate(2);
                String customerId = rst.getString(3);
                String customerName = rst.getString(4);
                double orderTotal = rst.getDouble(5);
                SearchOrderTM orderSearch = new SearchOrderTM(orderId,orderDate,customerId,customerName,orderTotal);
                orderArray.add(orderSearch);
                orders.add(orderSearch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void tblOrders_OnMouseClicked(MouseEvent mouseEvent) {
        if (tblSearchOrder.getSelectionModel().getSelectedItem() == null){
            return;
        }
        if (mouseEvent.getClickCount() == 2){
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/PlaceOrderPage.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PlaceOrderPageController controller = (PlaceOrderPageController) fxmlLoader.getController();
            controller.initializeWithSearchOrderForm(tblSearchOrder.getSelectionModel().getSelectedItem().getOrderId());
            Scene orderScene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(orderScene);
            stage.centerOnScreen();
            stage.show();
        }

    }

    public void btnSearchNavigate_onAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainFormPage.fxml"));
        Scene SearchOrderScene = new Scene(root);
        Stage mainStage = (Stage)(this.root.getScene().getWindow());
        mainStage.setScene(SearchOrderScene);
        mainStage.centerOnScreen();
    }
}
