package com.poornima.exception.controller;


import com.poornima.exception.db.DBConnection;
import com.poornima.exception.util.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlaceOrderPageController {
    public AnchorPane root;
    public TextField txtOrderDate;
    public TextField txtCustomerName;
    public ComboBox<CustomerTM> cmbCustomerId;
    public Label lblCusId;
    public ComboBox<ItemTM> cmbItemCode;
    public TextField txtDescription;
    public TextField txtQuantityOnHand;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public Button btnSave;
    public TableView<OrderDetailTM> tblOrder;
    public TableView<SearchOrderTM> tblSearchOrder;
    public Button btnPlaceOrder;
    public Label lblTotal;
    public Label lblOrderId;
    private boolean readOnly = false;
    static ArrayList<Order> ordersDB = new ArrayList<>();
    private ArrayList<SearchOrderTM> orderArray = new ArrayList<>();


    public void initialize(){

        // Basic initializations
        txtCustomerName.setEditable(false);
        txtQuantityOnHand.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtDescription.setEditable(false);

        // Let's set the date
        LocalDate today = LocalDate.now();
        txtOrderDate.setText(today.toString());

        // Let's load all the customer ids
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("SELECT CustID,CustName,CustAddress FROM Customer");
            ResultSet resultSet = pstm.executeQuery();
            cmbCustomerId.getItems().clear();
            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);

                CustomerTM customerTM = new CustomerTM(id,name,address);
                cmbCustomerId.getItems().add(customerTM);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                if(newValue == null){
                    txtCustomerName.clear();
                    return;
                }
                txtCustomerName.setText(newValue.getName());
            }
        });

        // Let's load all the item codes
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("SELECT ItemCode,Description,QtyOnHand,UnitPrice FROM Item");
            ResultSet rst = pstm.executeQuery();
            cmbItemCode.getItems().clear();
            while (rst.next()){
                String code = rst.getString(1);
                String description = rst.getString(2);
                String qtyOnHand = rst.getString(3);
                String unitPrice = rst.getString(4);

                ItemTM itemTM = new ItemTM(code,description,Integer.parseInt(qtyOnHand),Double.parseDouble(unitPrice));
                cmbItemCode.getItems().add(itemTM);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                if(newValue == null){
                    txtDescription.clear();
                    txtUnitPrice.clear();
                    txtQuantityOnHand.clear();
                    btnSave.setDisable(false);
                    return;
                }
                txtDescription.setText(newValue.getDescription());
                calculateQtyOnHand(newValue);
                txtUnitPrice.setText(newValue.getUnitPrice()+"");
            }
        });

        //Let's map columns
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("button"));

        btnSave.setDisable(false);

        tblOrder.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetailTM> observable, OrderDetailTM oldValue, OrderDetailTM selectedOrderDetail) {
            if(selectedOrderDetail == null){
                return;
            }
                String selectedItemCode=selectedOrderDetail.getCode();
                ObservableList<ItemTM>items=cmbItemCode.getItems();
                for(ItemTM item : items){
                    if(item.getCode().equals(selectedItemCode)){
                        cmbItemCode.getSelectionModel().select(item);
                        txtQty.setText(item.getQtyOnHand()+ "");
                        txtQty.setText(selectedOrderDetail.getQty()+ "");
                        btnSave.setText("Update");
                        cmbItemCode.setDisable(true);
                        Platform.runLater(()->{
                            txtQty.requestFocus();
                        });
                        break;
                    }
                }
            }
        });
            generateOrderId();
    }

    private void generateOrderId() {
        // Generate a new id
       int maxId = 0;
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement
                    ("SELECT * FROM orders ORDER BY OrderID DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()){
                String itemCode = rst.getString("OrderID");
                int id = Integer.parseInt(itemCode.replace("D", ""));
                if(maxId<id){
                    maxId=id;
                }
                maxId=maxId+1;
                String newid = "";
                if(maxId<10){
                    newid = "D00"+ maxId;
                }else if(maxId<100){
                    newid="D0"+maxId;
                }else{
                    newid="D"+maxId;
                }
                lblOrderId.setText(newid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnBackOrder_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainFormPage.fxml"));
        Scene PlaceOrderScene = new Scene(root);
        Stage mainStage = (Stage)(this.root.getScene().getWindow());
        mainStage.setScene(PlaceOrderScene);
        mainStage.centerOnScreen();
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
        btnSave_onAction(actionEvent);
    }

    public void btnSave_onAction(ActionEvent actionEvent) {
        //Let's do some validation
        if(txtQty.getText().trim().isEmpty()){
        new Alert(Alert.AlertType.ERROR,"Quantity can not be empty",ButtonType.OK).show();
        return;
        }else{
            int qty = Integer.parseInt(txtQty.getText());
            if(qty <1 || qty > Integer.parseInt(txtQuantityOnHand.getText())){
                new Alert(Alert.AlertType.ERROR,"Invalid Quantity",ButtonType.OK).show();
                return;
            }

            ItemTM selectedItem = cmbItemCode.getSelectionModel().getSelectedItem();
            ObservableList<OrderDetailTM> orderDetails = tblOrder.getItems();

           if(btnSave.getText().equals("Save")){
               boolean exist = false;
               for (OrderDetailTM orderDetail : orderDetails) {
                   if (orderDetail.getCode().equals(selectedItem.getCode())) {
                       exist = true;
                       orderDetail.setQty(orderDetail.getQty() + qty);
                       tblOrder.refresh();
                       break;
                   }
               }
                if(!exist){
                    Button btnDelete = new Button("Delete");
                    OrderDetailTM orderDetail = new OrderDetailTM(selectedItem.getCode(),
                            selectedItem.getDescription(),
                            qty,
                            selectedItem.getUnitPrice(), qty * selectedItem.getUnitPrice(), btnDelete);
                    btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            tblOrder.getSelectionModel().clearSelection();
                            cmbItemCode.getSelectionModel().clearSelection();
                            cmbItemCode.setDisable(false);
                            txtQty.clear();
                            orderDetails.remove(orderDetail);
                            cmbItemCode.requestFocus();
                        }
                    });
                    orderDetails.add(orderDetail);
                }
                calculateTotal();
                //cmbItemCode.getSelectionModel().clearSelection();
                //txtQty.clear();
                cmbItemCode.requestFocus();
           }else{
                //update
               OrderDetailTM selectedOrderDetail = tblOrder.getSelectionModel().getSelectedItem();
               selectedOrderDetail.setQty(qty);
               selectedOrderDetail.setTotal(qty* selectedOrderDetail.getUnitPrice());
               tblOrder.refresh();
               tblOrder.getSelectionModel().clearSelection();
               btnSave.setText("Save");
               cmbItemCode.setDisable(false);
               cmbItemCode.getSelectionModel().clearSelection();
               txtQty.clear();
               calculateTotal();
               cmbItemCode.requestFocus();
           }
        }

    }


    private void calculateQtyOnHand(ItemTM item) {
        txtQuantityOnHand.setText(item.getQtyOnHand() + "");
        ObservableList<OrderDetailTM> orderDetails = tblOrder.getItems();
        for (OrderDetailTM orderDetail : orderDetails) {
            if (orderDetail.getCode().equals(item.getCode())) {
                int displayQty = item.getQtyOnHand() - orderDetail.getQty();
                txtQuantityOnHand.setText(displayQty + "");
                break;
            }
        }
    }

    private void calculateTotal() {
        ObservableList<OrderDetailTM> orderDetails = tblOrder.getItems();
        double netTotal = 0;
        for(OrderDetailTM orderDetail : orderDetails){
            netTotal+=orderDetail.getTotal();
        }
        NumberFormat numberInstance= NumberFormat.getNumberInstance();
        numberInstance.setMaximumFractionDigits(2);
        numberInstance.setMinimumFractionDigits(2);
        numberInstance.setGroupingUsed(false);
        String formattedText = numberInstance.format(netTotal);
        lblTotal.setText(formattedText);

    }

    public void btnPlaceOrder_onAction(ActionEvent actionEvent) {
     //validation
        if(cmbCustomerId.getSelectionModel().getSelectedIndex()==-1){
            new Alert(Alert.AlertType.ERROR,"You need to select a customer",ButtonType.OK).show();
            cmbCustomerId.requestFocus();
            return;
        }

        if(tblOrder.getItems().size()==0){
            new Alert(Alert.AlertType.ERROR,"There is no order",ButtonType.OK).show();
            cmbItemCode.requestFocus();
            return;
        }
        //Let's save the order
        String orderId = lblOrderId.getText();


        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Orders(OrderID,OrderDate,CustID) VALUES (?,?,?)");
            preparedStatement.setObject(1,orderId);
            preparedStatement.setObject(2,LocalDate.now());
            preparedStatement.setObject(3,cmbCustomerId.getValue().getId());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Order Succeeded",ButtonType.OK).showAndWait();
                return;
            }else{
                System.out.println("SORRY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<OrderDetailTM> olOrderDetails = tblOrder.getItems();
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO OrderDetail (OrderID,ItemCode,OrderQTY)VALUES (?,?,?)");
            for(OrderDetailTM orderDetail : olOrderDetails){
                //Let's update the stock
                updateStockQty(orderDetail.getCode(),orderDetail.getQty());
                pstm.setObject(1,orderId);
                pstm.setObject(2,orderDetail.getCode());
                pstm.setObject(3,orderDetail.getQty());
                int affectedRows = pstm.executeUpdate();
                if(affectedRows == 0){
                    new Alert(Alert.AlertType.ERROR,"There's something wrong in the order",ButtonType.OK).show();
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new Alert(Alert.AlertType.INFORMATION, "Mudalali wade goda", ButtonType.OK).showAndWait();

        tblOrder.getItems().clear();
        txtQty.clear();
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        calculateTotal();
        generateOrderId();

    }

    private void updateStockQty(String itemCode, int qty) {
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement
                    ("UPDATE Item SET QtyOnHand=QtyOnHand - ? WHERE ItemCode =?");
            pstm.setObject(1,qty);
            pstm.setObject(2,itemCode);
            int affectedRows = pstm.executeUpdate();
            if(affectedRows == 0){
                new Alert(Alert.AlertType.ERROR,"Could not update the stock",ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    void initializeWithSearchOrderForm(String orderId) {
        lblOrderId.setText(orderId);
        readOnly = true;
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("SELECT o.OrderID,o.OrderDate,o.CustID,c.CustName,SUM(i.UnitPrice*od.OrderQTY)AS Total FROM (((\n" +
                    "    orders o INNER JOIN orderdetail od ON od.OrderID=o.OrderID)INNER JOIN customer c ON c.CustID=o.CustID)\n" +
                    "    INNER JOIN  item i ON i.ItemCode=od.ItemCode) GROUP BY o.OrderID");
            ResultSet rst = pstm.executeQuery();
            ObservableList<SearchOrderTM> orders = tblSearchOrder.getItems();
            orders.clear();
            while (rst.next()){
                String orderId1 = rst.getString(1);
                Date orderDate = rst.getDate(2);
                String customerId = rst.getString(3);
                String customerName = rst.getString(4);
                double orderTotal = rst.getDouble(5);
                SearchOrderTM orderSearch = new SearchOrderTM(orderId1,orderDate,customerId,customerName,orderTotal);
                orderArray.add(orderSearch);
                orders.add(orderSearch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


                cmbCustomerId.setDisable(true);
                cmbItemCode.setDisable(true);
                btnSave.setDisable(true);
                btnPlaceOrder.setVisible(false);


            }
        }


