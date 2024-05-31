package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private Pane paneAnimal;

    @FXML
    void closeWindow(MouseEvent event) {
        GameController.mainPane.getChildren().remove(paneAnimal);
    }

    private Card kar;


    public void setAnimal(CardBrain.cardObj iskartu, Pane pane) throws IOException {
        paneAnimal = pane;

        Card kartu = iskartu.getCard();
        Petak petak = iskartu.getPreviousPetak();

        GameObject gameObject = kartu.convertToGameObject();
        Animal animal = (Animal) gameObject;

        String weight = String.valueOf(animal.GetWeight());
        Image image = new Image(String.valueOf(AnimalController.class.getResource(kartu.getImagePath())));
        String name = animal.GetName();

        this.aktifItems.setText(petak.getItemsToList());
        this.berat.setText(weight);
        this.imag.setImage(image);
        this.name.setText(name);

        kar = kartu;
    };

    public void panenMas(MouseEvent event) {
        if (kar != null) {
            Animal animal = (Animal) kar.convertToGameObject();

            if (animal.isReadyToHarvest())
            {
                Product hasilPanen = (Product) animal.Harvest();

                Card hasilPanenCard = new Card(hasilPanen);

                if (hasilPanenCard != null && GameManager.PlayerInterface.tryAddToHand(hasilPanenCard)) {
                    List<Card> car = new ArrayList<>();
                    car.add(hasilPanenCard);
                    GameController.gameC.isiDeck(car);
                    GameController.mainPane.getChildren().remove(paneAnimal);

                } else {
                    System.out.println("Deck aktif penuh");
                }
            } else{
                GameController.mainPane.getChildren().remove(paneAnimal);
                CardBrain.botNot("Not Ready!");
            }
        }
    }
}