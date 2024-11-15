package com.example.kurs;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateOrder {
    @FXML
    private ComboBox<String> citybox;

    @FXML
    private ComboBox<String> deliverymanbox;

    @FXML
    private Button create;

    @FXML
    private TextField address;
    @FXML
    private Button skip;

    DataBase db = null;


    @FXML
    void initialize() throws IOException {
        db = new DataBase();
        loadCities();
        loadDeliverymen();
    }




    void loadCities() {
        try {
            ArrayList<String> citys = db.getCity();
            citybox.getItems().addAll(citys);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    void loadDeliverymen() {
        citybox.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ObservableList<String> deliverymen = db.getSotrudnik(newValue);
                deliverymanbox.getItems().clear();
                deliverymanbox.getItems().addAll(deliverymen);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }



    @FXML
    void createzak(){

        create.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    db.insertZakazi(address.getText(),HelloController.iduser,db.getIdsotrudnik(deliverymanbox.getValue()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("userroot.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setTitle("products");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }
        });
    }



    @FXML
    void skip(){

        skip.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("skip.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setTitle("products");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                }

            });
    }
}












