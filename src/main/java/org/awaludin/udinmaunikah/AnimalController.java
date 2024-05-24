package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.Animal;
import org.awaludin.udinmaunikah.Programming.Card;
import org.awaludin.udinmaunikah.Programming.GameObject;

import java.io.IOException;

public class AnimalController {

    @FXML
    private Label aktifItems;

    @FXML
    private Label berat;

    @FXML
    private ImageView imag;

    @FXML
    private Text name;

    @FXML
    private ImageView panen;

    @FXML
    void closeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setAnimal(Card kartu) throws IOException {

        GameObject gameObject = kartu.convertToGameObject();
        Animal animal = (Animal) gameObject;

        String weight = String.valueOf(animal.GetWeight());
        Image image = new Image(String.valueOf(AnimalController.class.getResource(kartu.getImagePath())));
        String name = animal.GetName();

        this.berat.setText(weight);
        this.imag.setImage(image);
        this.name.setText(name);
    };
}
