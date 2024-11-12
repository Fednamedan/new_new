package com.example.kurs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Towar extends ListCell<ProductData> {
    @FXML
    private AnchorPane idContainer;

    @FXML
    private VBox idStr;

    @FXML
    private Label marka;
    @FXML
    private ImageView idimg;

    @FXML
    private Label nazv;

    @FXML
    private Label cena;


    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(ProductData student, boolean empty) {
        super.updateItem(student, empty);
        if (empty || student == null) {
            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("towar.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            nazv.setText(student.getName());
            marka.setText(student.getMarka());
            cena.setText(student.getCena());
            String photoPath = student.getPhoto();

            if (photoPath == null) {
                // Load default image
                Image defaultImage = new Image("nullphoto.jpg");
                idimg.setImage(defaultImage);
            } else {
                File file = new File(photoPath);
                try {
                    String urlImage = file.toURI().toURL().toString();
                    Image image = new Image(urlImage);
                    idimg.setImage(image);
                } catch (MalformedURLException ignored) {}
            }


            setText(null);
            setGraphic(idContainer);
        }

    }
}
