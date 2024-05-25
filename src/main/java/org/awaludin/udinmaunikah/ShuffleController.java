package org.awaludin.udinmaunikah;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.Card;
import org.awaludin.udinmaunikah.Programming.GameManager;

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

    public boolean setShuffleCards(List<Card> cards) throws IOException {
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
                            selectCard(cards.get(finalI), card);
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

        List<Card> ss = GameManager.PlayerInterface.getPickList();


        for (Card car : ss) {
            System.out.println(car.convertToGameObject().GetName());
        }
    }


    @FXML
    void closeWindow(MouseEvent event) {
        GameManager.PlayerInterface.endDraftPick();

        List<Card> cards = GameManager.PlayerInterface.getHand();


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void shuffle(MouseEvent event) throws IOException {
        GameManager.PlayerInterface.reroll();
        setShuffleCards(GameManager.PlayerInterface.getDraftList());


    }

}