package com.example.kurs;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private Button buttonentrance;

    @FXML
    private TextField loginfield;

    @FXML
    private TextField passwordfield;

    @FXML
    private Button registerbutton;


    static int iduser;
    DataBase db = null;


    @FXML
    void initialize() {
        db = new DataBase();

        buttonentrance.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (!loginfield.getText().trim().equals("") & !passwordfield.getText().trim().equals("")) {
                        int a = db.getUser(loginfield.getText(), passwordfield.getText());
                        iduser = db.getIDuser(loginfield.getText(), passwordfield.getText());
                        if (a == 1) {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createorder.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
                            stage.setTitle("order");
                            stage.getIcons().add(new Image("file:img/icon.png"));
                            stage.setScene(scene);
                            stage.show();
                        }
                        if (a == 2) {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                            stage.setTitle("admin field");
                            stage.getIcons().add(new Image("file:img/icon.png"));
                            stage.setScene(scene);
                            stage.show();
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        registerbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 250, 400);
                    stage.setTitle("registration");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });


    }
}


