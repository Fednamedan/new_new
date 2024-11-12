package com.example.kurs;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class Addproduct {
    @FXML
    private Button addbut;

    @FXML
    private TextField cena;

    @FXML
    private TextField codeproduct;

    @FXML
    private TextField marka;

    @FXML
    private TextField name;


    DataBase db = null;


    @FXML
    void initialize() {
        db = new DataBase();
        addbut.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    db.newProduct(name.getText(), marka.getText(), Integer.parseInt(cena.getText()), Integer.parseInt(codeproduct.getText()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }


            }


        });


    }
}
