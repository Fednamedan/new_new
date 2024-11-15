package com.example.kurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Userroot {
    @FXML
    private ListView<ProductData> idspis;

    @FXML
    private Label price;
    @FXML
    private TextField poisk;

    @FXML
    private Button order;
    private Stage orderDetailsStage;


    public int k = 0;


    DataBase db = null;


    @FXML
    void initialize() throws IOException {
        db = new DataBase();
        loadallproduct();
    }


    void loadallproduct() {
        try {
            List<ProductData> ls = db.getProduct();
            ObservableList<ProductData> originalList = FXCollections.observableArrayList(ls);
            FilteredList<ProductData> filteredList = new FilteredList<>(originalList, p -> true);
            idspis.setItems(filteredList);

            poisk.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            idspis.setCellFactory(stringListView -> {
                ListCell<ProductData> cell = new Towar();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("add to order");
                MenuItem delItem = new MenuItem("delete from order");
                editItem.setOnAction(event -> {
                    ProductData selectedProduct = cell.getItem();
                    try {
                        db.insertOrder(db.getIdProduct(selectedProduct.getName(), selectedProduct.getMarka(), Integer.parseInt(selectedProduct.getCena())), db.selZakaz(), Integer.parseInt(selectedProduct.getCena()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    k = k + Integer.parseInt(selectedProduct.getCena());
                    price.setText("price :" + k);
                });
                delItem.setOnAction(event -> {
                    ProductData selectedProduct = cell.getItem();
                    try {
                        // Проверяем, существует ли продукт в заказе этого пользователя
                        if (db.productExistsInOrder(db.getIdProduct(selectedProduct.getName(), selectedProduct.getMarka(), Integer.parseInt(selectedProduct.getCena())), HelloController.iduser)) {
                            db.removeOrder(db.getIdProduct(selectedProduct.getName(), selectedProduct.getMarka(), Integer.parseInt(selectedProduct.getCena())), db.getCurrentOrderId(HelloController.iduser));
                            k = k - Integer.parseInt(selectedProduct.getCena());
                            if (k < 0) {
                                k = 0;
                            }
                            price.setText("price :" + k);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                contextMenu.getItems().addAll(editItem, delItem);
                cell.setContextMenu(contextMenu);
                return cell;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void orderload() {
        order.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (orderDetailsStage == null || !orderDetailsStage.isShowing()) {
                        showOrderDetails(HelloController.iduser);
                    } else {
                        orderDetailsStage.toFront();
                    }
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showOrderDetails(int userId) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDetails.fxml"));
        Parent root = loader.load();
        OrderDetailsController controller = loader.getController();
        List<OrderDetails> orderDetailsList = db.getOrderDetails(userId);
        controller.loadOrderDetails(orderDetailsList);
        orderDetailsStage = new Stage();
        orderDetailsStage.setScene(new Scene(root));
        orderDetailsStage.show();

    }
}


