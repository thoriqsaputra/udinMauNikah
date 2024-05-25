package org.awaludin.udinmaunikah;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.awaludin.udinmaunikah.Programming.*;

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

    private CardBrain.cardObj cardObj;

    private Pane paneMane;

    private GameController controller;


    @FXML
    public void setProduct(CardBrain.cardObj kart, Pane paneManeh) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));

        // Load the FXML file to initialize the controller
        Parent root = fxmlLoader.load();

        // Now get the controller
        controller = fxmlLoader.getController();

        kar = kart.getCard();
        cardObj = kart;

        paneMane = paneManeh;

        GameObject gameObject = kar.convertToGameObject();
        Product product = (Product) gameObject;

        String weightGain = String.valueOf(product.getWeight());
        Image imag = new Image(String.valueOf(AnimalController.class.getResource(kar.getImagePath())));
        String name = product.GetName();
        String harga = String.valueOf(product.getPrice());

        this.weightGain.setText(weightGain);
        this.imag.setImage(imag);
        this.name.setText(name);
        this.harga.setText(harga);
    };

    @FXML
    void sell(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        GameManager.sellItems(cardObj.getGameObject());

        paneMane.getChildren().remove(cardObj);

        controller.setPlayer();

        botNot("Sold to shop!");
    }

    public void botNot(String message){
        Group myGroup = (Group) paneMane.lookup("#boardError");
        Text text = (Text) myGroup.lookup("#error");


        text.setText(message);
        if (myGroup != null) {
            // Change opacity to 1
            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(myGroup.opacityProperty(), 0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(myGroup.opacityProperty(), 1))
            );

            // Wait for 3 seconds
            PauseTransition wait = new PauseTransition(Duration.seconds(2));

            // Change opacity back to 0
            Timeline fadeOut = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(myGroup.opacityProperty(), 1)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(myGroup.opacityProperty(), 0))
            );

            // Sequentially play the animations
            fadeIn.setOnFinished(event -> wait.play());
            wait.setOnFinished(event -> fadeOut.play());

            fadeIn.play();
        } else {
            System.out.println("Group with fx:id 'myGroup' not found.");
        }
    }

}
