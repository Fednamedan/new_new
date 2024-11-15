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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Skip {
    @FXML
    private ListView<ProductData> deliver;
    @FXML
    private TextField poisk;

    @FXML
    private Button order;

    DataBase db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DataBase();
        loadallproduct();
    }

    void loadallproduct() {
        try {
            List<ProductData> ls = db.getProduct();
            ObservableList<ProductData> originalList = FXCollections.observableArrayList(ls);
            FilteredList<ProductData> filteredList = new FilteredList<>(originalList, p -> true);
            deliver.setItems(filteredList);

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

            deliver.setCellFactory(stringListView -> {
                ListCell<ProductData> cell = new Towar();
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
                    showOrderDetails(HelloController.iduser);
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
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
