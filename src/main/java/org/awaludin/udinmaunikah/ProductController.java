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



    private Card kar;

    private CardBrain.cardObj cardObj;

    private Pane paneProduct;

    @FXML
    void closeWindow(MouseEvent event) {
        GameController.mainPane.getChildren().remove(paneProduct);
    }

    @FXML
    public void setProduct(CardBrain.cardObj kart, Pane paneProduc) throws IOException {

        paneProduct = paneProduc;

        kar = kart.getCard();
        cardObj = kart;

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

        GameManager.sellItems(cardObj.getGameObject());

        GameManager.PlayerInterface.useCardT(cardObj.getCard());

        Petak p = cardObj.getPreviousPetak();

        p.setNull();

        GameController.mainPane.getChildren().remove(cardObj);

        GameController.gameC.setPlayer();

        GameController.mainPane.getChildren().remove(paneProduct);

        botNot("Sold to shop!");
    }

    public void botNot(String message){
        Group myGroup = (Group) GameController.mainPane.lookup("#boardError");
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
