package org.awaludin.udinmaunikah;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.Toko;

import java.io.IOException;
import java.util.Map;


public class ShopController {

    @FXML
    private GridPane productGrid;

    @FXML
    private Pane shopMain;

    private Pane poine;

    public void setProductGrid() {

        productGrid.getChildren().clear();

        Map<GameObject, Integer> products = Toko.getListItems();

        productGrid.getChildren().clear();
        try{
            int columns = 0;
            int rows = 1;

            for(Map.Entry<GameObject, Integer> entry : products.entrySet()){

                GameObject item = entry.getKey();
                Integer quantity = entry.getValue();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("shopDeck.fxml"));

                Pane deck = fxmlLoader.load();

                deck.setOnMouseClicked((event -> {
                    try {
                        FXMLLoader fxmlLoader2 = new FXMLLoader();
                        fxmlLoader2.setLocation(getClass().getResource("ShopDlg.fxml"));

                        Pane parent = fxmlLoader2.load();

                        ShopDlgController controller = fxmlLoader2.getController();

                        poine = parent;

                        controller.setDlgBuy(item, parent);
                        controller.setShpcontroller(this);

                        shopMain.getChildren().add(parent);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));

                ShopDeckController controller = fxmlLoader.getController();

                controller.setDeck(item, quantity);

                if (columns == 3){
                    columns = 0;
                    rows++;
                }

                productGrid.add(deck, columns++, rows);
                GridPane.setMargin(deck, new Insets(10));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeShop(){
        shopMain.getChildren().remove(poine);
    }

    public void botNot(String message){
        Group myGroup = (Group) shopMain.lookup("#boardError");
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

    public void goBack(MouseEvent mouseEvent) {

        System.out.println("WOO");
        try {
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            Scene previousScene = Application.popScene();

            if (previousScene != null) {
                stage.setScene(previousScene);
                stage.show();
            }

            GameController.gameC.setPlayer();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}