package com.example.kurs;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderDetails {
    private final SimpleIntegerProperty orderId;
    private final SimpleStringProperty productName;
    private final SimpleIntegerProperty productQuantity;
    private final SimpleDoubleProperty productPrice;

    public OrderDetails(int orderId, String productName, int productQuantity, double productPrice) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.productName = new SimpleStringProperty(productName);
        this.productQuantity = new SimpleIntegerProperty(productQuantity);
        this.productPrice = new SimpleDoubleProperty(productPrice);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public int getProductQuantity() {
        return productQuantity.get();
    }

    public double getProductPrice() {
        return productPrice.get();
    }
}





