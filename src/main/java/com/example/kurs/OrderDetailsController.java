package com.example.kurs;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class OrderDetailsController {
    @FXML
    private TableView<OrderDetails> orderDetailsTable;

    @FXML
    private TableColumn<OrderDetails, String> productNameColumn;

    @FXML
    private TableColumn<OrderDetails, Integer> productQuantityColumn;

    @FXML
    private TableColumn<OrderDetails, Double> productPriceColumn;

    public void initialize() {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }


    public void loadOrderDetails(List<OrderDetails> orderDetailsList) {
        orderDetailsTable.getItems().setAll(orderDetailsList);
    }


}








