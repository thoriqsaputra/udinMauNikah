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
import org.awaludin.udinmaunikah.Programming.Animal;
import org.awaludin.udinmaunikah.Programming.Card;
import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.Product;
import org.awaludin.udinmaunikah.Programming.GameManager;

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

    private GameController gameController;

    public void setAnimal(Card kartu, Pane pane, GameController gameController) throws IOException {

        gameController = gameController;
        paneAnimal = pane;

        GameObject gameObject = kartu.convertToGameObject();
        Animal animal = (Animal) gameObject;

        String weight = String.valueOf(animal.GetWeight());
        Image image = new Image(String.valueOf(AnimalController.class.getResource(kartu.getImagePath())));
        String name = animal.GetName();

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

                if (hasilPanenCard != null && GameManager.PlayerInterface.getHand().size() < 6) {
                    GameManager.PlayerInterface.tryAddToHand(hasilPanenCard);
                    List<Card> car = new ArrayList<>();
                    car.add(hasilPanenCard);
                    gameController.isiDeck(car);
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