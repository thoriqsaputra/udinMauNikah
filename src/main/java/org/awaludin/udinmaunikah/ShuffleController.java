package org.awaludin.udinmaunikah;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private List<Card> cars;

    public boolean setShuffleCards(List<Card> cards) throws IOException {
        cars = new ArrayList<Card>(cards);

        for (int i = 0; i < cards.size(); i++) {
            String name = cards.get(i).convertToGameObject().GetName();
            Text text = switch(i) {
                case 0 -> Text1;
                case 1 -> Text2;
                case 2 -> Text3;
                case 3 -> Text4;
                default -> null; // Handle any other cases if necessary
            };
            if (text != null) {
                text.setText(name);
                Image img = new Image(getClass().getResourceAsStream(cards.get(i).getImagePath()));
                ImageView imageView = switch(i) {
                    case 0 -> imag1;
                    case 1 -> imag2;
                    case 2 -> imag3;
                    case 3 -> imag4;
                    default -> null; // Handle any other cases if necessary
                };
                if (imageView != null) {
                    imageView.setImage(img);
                    Group card = switch(i) {
                        case 0 -> card1;
                        case 1 -> card2;
                        case 2 -> card3;
                        case 3 -> card4;
                        default -> null; // Handle any other cases if necessary
                    };
                    if (card != null) {
                        int finalI = i;
                        card.setOnMouseClicked((event -> {
                            selectCard(cars.get(finalI), card);
                        }));
                        card.setOpacity(1);
                        card.setEffect(null);
                    }
                }
            }
        }

        return cards.size() >= 4;

    }

    void selectCard(Card card, Group group) {

        if(group.getEffect() == null){
            GameManager.PlayerInterface.takeCard(card);
            DropShadow ds = new DropShadow();
            ds.setColor(Color.WHITE);
            ds.setSpread(0.5);
            ds.setRadius(20);
            group.setEffect(ds);
        } else{
            GameManager.PlayerInterface.returnCard(card);
            group.setEffect(null);
        }

    }

    @FXML
    void closeWindow(MouseEvent event) throws IOException {

        List<Card> c = GameManager.PlayerInterface.getPickList()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        GameManager.PlayerInterface.endDraftPick();

        if (GameController.gameC!=null){
            GameController.gameC.removeShufflePane();
            GameController.gameC.isiDeck(c);
        }
    }

    @FXML
    void shuffle(MouseEvent event) throws IOException {
        GameManager.PlayerInterface.reroll();
        setShuffleCards(GameManager.PlayerInterface.getDraftList());
    }

}