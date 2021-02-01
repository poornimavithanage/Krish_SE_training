package com.virtusa.exception.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormPageController {
    public Button btnCustomer;
    public Button btnItems;
    public Button btnOrder;
    public Button btnSearch;
    public AnchorPane root;

    public void btnAddCustomer_onAction (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/CustomerPage.fxml"));
        Scene customerScene = new Scene(root);
        Stage mainStage = (Stage)(this.root.getScene().getWindow());
        mainStage.setScene(customerScene);
        mainStage.centerOnScreen();
        
    }

    public void btnAddItems_onAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/ItemPage.fxml"));
        Scene ItemScene = new Scene(root);
        Stage mainStage = (Stage)(this.root.getScene().getWindow());
        mainStage.setScene(ItemScene);
        mainStage.centerOnScreen();

    }

    public void btnAddOrder_onAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderPage.fxml"));
        Scene PlaceOrderScene = new Scene(root);
        Stage mainStage = (Stage)(this.root.getScene().getWindow());
        mainStage.setScene(PlaceOrderScene);
        mainStage.centerOnScreen();

    }

    public void btnSearch_onAction(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/SearchOrderPage.fxml"));
        Scene SearchOrderScene = new Scene(root);
        Stage mainStage = (Stage)(this.root.getScene().getWindow());
        mainStage.setScene(SearchOrderScene);
        mainStage.centerOnScreen();
    }
}
