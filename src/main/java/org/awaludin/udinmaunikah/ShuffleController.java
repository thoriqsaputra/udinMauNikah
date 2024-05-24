package org.awaludin.udinmaunikah;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.awaludin.udinmaunikah.Programming.Card;

import java.io.IOException;
import java.util.List;

public class ShuffleController {

    @FXML
    private Text Text1;

    @FXML
    private Text Text2;

    @FXML
    private Text Text3;

    @FXML
    private Text Text4;

    @FXML
    private Group card1;

    @FXML
    private Group card2;

    @FXML
    private Group card3;

    @FXML
    private Group card4;

    @FXML
    private ImageView imag1;

    @FXML
    private ImageView imag2;

    @FXML
    private ImageView imag3;

    @FXML
    private ImageView imag4;

    @FXML
    private Group retry;

    public void setShuffleCards(List<Card> cards) throws IOException {
        String name1 = cards.get(0).convertToGameObject().GetName();
        String name2 = cards.get(1).convertToGameObject().GetName();
        String name3 = cards.get(2).convertToGameObject().GetName();
        String name4 = cards.get(3).convertToGameObject().GetName();
        Text1.setText(name1);
        Text2.setText(name2);
        Text3.setText(name3);
        Text4.setText(name4);

        Image img1 = new Image(getClass().getResourceAsStream(cards.get(0).getImagePath()));
        Image img2 = new Image(getClass().getResourceAsStream(cards.get(1).getImagePath()));
        Image img3 = new Image(getClass().getResourceAsStream(cards.get(2).getImagePath()));
        Image img4 = new Image(getClass().getResourceAsStream(cards.get(3).getImagePath()));
        imag1.setImage(img1);
        imag2.setImage(img2);
        imag3.setImage(img3);
        imag4.setImage(img4);
    }



    @FXML
    void closeWindow(MouseEvent event) {

    }

    @FXML
    void shuffle(MouseEvent event) {

    }

}