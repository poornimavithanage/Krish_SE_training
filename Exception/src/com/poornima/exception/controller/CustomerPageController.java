package com.poornima.exception.controller;


import com.poornima.exception.db.DBConnection;
import com.poornima.exception.util.CustomerTM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerPageController {
    static ArrayList<CustomerTM> customersDB = new ArrayList<>();
    public TextField txtCusId;
    public TextField txtCusName;
    public TextField txtCusAddress;
    public Button buttonSave;
    public Button buttonDelete;
    public TableView<CustomerTM> tblCustomer;
    public Button buttonAdd;
    public Button btnBack;
    public AnchorPane root;

    public void initialize(){
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllCustomers();
        buttonDelete.setDisable(true);

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM selectedCustomer) {
                buttonDelete.setDisable(selectedCustomer==null); // why use null
            }
        });

    }

    private void loadAllCustomers(){
        try {
            Statement stm= DBConnection.getInstance().getConnection().createStatement(); //why we use statement
            ResultSet rst = stm.executeQuery("SELECT CustID,CustName,CustAddress FROM Customer");
            ObservableList<CustomerTM>customers = tblCustomer.getItems(); //why use observable list
            customers.clear();
            while (rst.next()){
                String id = rst.getString("CustID");
                String name = rst.getString("CustName");
                String address = rst.getString("CustAddress");
                customers.add(new CustomerTM(id,name,address));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAddNew_onAction(ActionEvent actionEvent) {
        buttonSave.setDisable(false);
        ObservableList<CustomerTM> customers = tblCustomer.getItems();
        if (customers.size() == 0) {
            txtCusId.setText("C001");
        } else {
            //Todo:Generate ID
            CustomerTM lastCustomer = customers.get(customers.size() - 1);
            String lastCustomerId = lastCustomer.getId();
            String number = lastCustomerId.substring(1, 4);
            int newId = Integer.parseInt(number) + 1;
            if (newId < 10) {
                txtCusId.setText("C00" + newId);
            } else if (newId < 100) {
                txtCusId.setText("C0" + newId);
            } else {
                txtCusId.setText("C" + newId);
            }

        }

        txtCusName.setDisable(false);
        txtCusAddress.setDisable(false);
        buttonSave.setDisable(false);
        txtCusName.requestFocus();
    }

    public void btnSave_onAction(ActionEvent actionEvent) {
        //validation
        if (txtCusId.getText().trim().isEmpty() || txtCusName.getText().trim().isEmpty() || txtCusName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Can not proceed with empty fields", ButtonType.OK).show();
            return;
        }

        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        double salary = 80000;

        try {
            /* Regular Statement
            Statement stm = connection.createStatement();
            int affectedRows = stm.executeUpdate("INSERT INTO Customer VALUES ('" + id + "','" + name + "','" + address + "')");*/
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT into Customer(custID,CustName,CustAddress,Salary) VALUES (?,?,?,?)");
            pstm.setObject(1,id);
            pstm.setObject(2,name);
            pstm.setObject(3,address);
            pstm.setObject(4,salary);
            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0){
                loadAllCustomers();
                tblCustomer.getSelectionModel().clearSelection();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to add",ButtonType.OK).show();
                txtCusName.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnDelete_onAction(ActionEvent actionEvent) {
        String id = tblCustomer.getSelectionModel().getSelectedItem().getId();
        try {
            /* Regular JDBC Statement
            Statement stm = connection.createStatement();
            int affectedRows = stm.executeUpdate("DELETE FROM Customer WHERE id='" + id +"'");*/

            PreparedStatement pstm = (PreparedStatement) DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Customer WHERE CustID=?");
            pstm.setObject(1, id);
            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0){
                loadAllCustomers();
                tblCustomer.getSelectionModel().clearSelection();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to delete",ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnNavigateCustomer_onActions(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainFormPage.fxml"));
        Scene customerScene = new Scene(root);
        Stage mainStage = (Stage)(this.root.getScene().getWindow());
        mainStage.setScene(customerScene);
        mainStage.centerOnScreen();
    }
}
