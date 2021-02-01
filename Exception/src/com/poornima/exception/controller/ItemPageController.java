package com.virtusa.exception.controller;


import com.virtusa.exception.db.DBConnection;
import com.virtusa.exception.util.ItemTM;
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

public class ItemPageController {
    public AnchorPane root;
    public Button btnAddNew;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtQuantityOnHand;
    public Button btnSave;
    public Button btnDelete;
    public TableView<ItemTM> tblItems;
    public TextField txtUnitPrice;

    public void initialize(){
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        loadAllItems();
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        //txtItemCode.setDisable(true);
        txtDescription.setDisable(true);
        txtQuantityOnHand.setDisable(true);
        txtUnitPrice.setDisable(true);

        tblItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM selectedItems) {
               btnDelete.setDisable(selectedItems==null);

            }
        });

    }

    private void loadAllItems(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement(); //why not use prepared statement
            ResultSet rst = stm.executeQuery("SELECT ItemCode,Description,QtyOnHand,UnitPrice FROM Item"); //execute the query or update query
            ObservableList<ItemTM>Items = tblItems.getItems();
            Items.clear();
            while (rst.next()){
                String code = rst.getString("ItemCode");
                String description = rst.getString("Description");
                String qtyOnHand = rst.getString("QtyOnHand");
                String unitPrice = rst.getString("UnitPrice");
                Items.add(new ItemTM(code,description,Integer.parseInt(qtyOnHand),Double.parseDouble(unitPrice)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAddNew_onAction(ActionEvent actionEvent) {
        txtItemCode.clear();
        txtDescription.clear();
        txtQuantityOnHand.clear();
        txtUnitPrice.clear();
        tblItems.getSelectionModel().clearSelection();
        txtDescription.setDisable(false);
        txtQuantityOnHand.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtDescription.requestFocus();
        btnSave.setDisable(false);

        // Generate a new id
        int maxCode = 0;
        for (ItemTM item : tblItems.getItems()) {
            int code = Integer.parseInt(item.getCode().replace("P", ""));
            if (code > maxCode) {
                maxCode = code;
            }
        }
        maxCode = maxCode + 1;
        String code = "";
        if (maxCode < 10) {
            code = "P00" + maxCode;
        } else if (maxCode < 100) {
            code = "P0" + maxCode;
        } else {
            code = "P" + maxCode;
        }
        txtItemCode.setText(code);

    }

    public void btnSave_onAction(ActionEvent actionEvent) {
        //validation
        if(txtItemCode.getText().trim().isEmpty() || txtDescription.getText().trim().isEmpty() ||
        txtQuantityOnHand.getText().trim().isEmpty() || txtUnitPrice.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Can not proceed with empty fields", ButtonType.OK).show();
            return;
        }
        String code = txtItemCode.getText();
        String description = txtDescription.getText();
        String qtyOnHand = txtQuantityOnHand.getText();
        String unitPrice = txtUnitPrice.getText();

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement
                    ("INSERT INTO Item(ItemCode,Description,QtyOnHand,UnitPrice) VALUES (?,?,?,?)");
            pstm.setObject(1,code);
            pstm.setObject(2,description);
            pstm.setObject(3,qtyOnHand);
            pstm.setObject(4,unitPrice);
            int affectedRows = pstm.executeUpdate();

            if(affectedRows>0){
                loadAllItems();
                tblItems.getSelectionModel().clearSelection();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to add",ButtonType.OK).show();
                txtDescription.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnDelete_onAction(ActionEvent actionEvent) {
        String code = tblItems.getSelectionModel().getSelectedItem().getCode();
        try {
            PreparedStatement pstm = (PreparedStatement) DBConnection.getInstance().getConnection().prepareStatement
                    ("DELETE FROM Item WHERE ItemCode=?");
            pstm.setObject(1,code);
            int affectedRows = pstm.executeUpdate();
            if(affectedRows >0){
                loadAllItems();
                tblItems.getSelectionModel().clearSelection();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to delete",ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnBackItem_onAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainFormPage.fxml"));
        Scene ItemScene = new Scene(root);
        Stage mainStage = (Stage)(this.root.getScene().getWindow());
        mainStage.setScene(ItemScene);
        mainStage.centerOnScreen();
    }
}
