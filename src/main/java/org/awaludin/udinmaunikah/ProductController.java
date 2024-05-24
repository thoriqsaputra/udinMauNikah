package org.awaludin.udinmaunikah;


import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.Animal;
import org.awaludin.udinmaunikah.Programming.Card;
import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.Product;

import java.io.IOException;

public class ProductController {

    @FXML
    private Label harga;

    @FXML
    private Label weightGain;

    @FXML
    private Text name;

    @FXML
    private ImageView imag;

    @FXML
    void closeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Card kar;

    @FXML
    public void setProduct(Card kartu) throws IOException {

        GameObject gameObject = kartu.convertToGameObject();
        Product product = (Product) gameObject;

        String weightGain = String.valueOf(product.getWeight());
        Image imag = new Image(String.valueOf(AnimalController.class.getResource(kartu.getImagePath())));
        String name = product.GetName();
        String harga = String.valueOf(product.getPrice());

        this.weightGain.setText(weightGain);
        this.imag.setImage(imag);
        this.name.setText(name);
        this.harga.setText(harga);

        kar = kartu;
    };

    @FXML
    void eat(MouseEvent event) {

    }

    @FXML
    void sell(MouseEvent event) {

    }

}
