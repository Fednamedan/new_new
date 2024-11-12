package com.example.kurs;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class Registration {
    @FXML
    private TextField cityfieldreg;

    @FXML
    private TextField fullnamefield;

    @FXML
    private TextField loginfieldreg;

    @FXML
    private TextField mobphonefieldreg;

    @FXML
    private TextField passwordfieldreg;

    @FXML
    private Button setregistrationbut;



    DataBase db = null;



    @FXML

    void regis() {
        db = new DataBase();
        setregistrationbut.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    db.insertRegis(fullnamefield.getText(), cityfieldreg.getText(), mobphonefieldreg.getText(), loginfieldreg.getText(), passwordfieldreg.getText() );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}









