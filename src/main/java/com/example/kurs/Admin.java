package com.example.kurs;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Admin {
    @FXML
    private ListView<ProductData> idspis;
    @FXML
    private TextField markaedit;

    @FXML
    private TextField nameedit;

    @FXML
    private TextField cenaedit;

    @FXML
    private Button editprod;

    public int idProd;
    DataBase db = null;



    @FXML
    void initialize() {
        db = new DataBase();
        loadInfo();
    }

    void loadInfo() {
        try {
            List<ProductData> ls = db.getProduct();
            idspis.getItems().addAll(ls);
            idspis.setCellFactory(stringListView -> {
                ListCell<ProductData> cell = new Towar();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("add product");
                MenuItem deleteItem = new MenuItem("delete product");
                MenuItem redactItem = new MenuItem("edit product");
                editItem.setOnAction(event -> {
                    ProductData item = cell.getItem();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addproduct.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load(), 250, 400);
                        stage.setTitle("new product");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                });

                deleteItem.setOnAction(event -> {
                    ProductData item = cell.getItem();
                    try {
                        db.delProduct(item.getName(), item.getMarka(), Integer.parseInt(item.getCena()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }


                });

                redactItem.setOnAction(event -> {
                    ProductData item = cell.getItem();
                    nameedit.setText(item.getName());
                    cenaedit.setText(item.getCena());
                    markaedit.setText(item.getMarka());
                    try {
                        idProd = db.getIdProduct(item.getName(),item.getMarka() , Integer.parseInt(item.getCena()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                contextMenu.getItems().addAll(editItem, deleteItem , redactItem);
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
                return cell;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void edit(){
        editprod.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    db.UpdateProduct(nameedit.getText(), markaedit.getText(), Integer.parseInt(cenaedit.getText()), idProd );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }










}
