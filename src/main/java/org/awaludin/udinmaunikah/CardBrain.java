package org.awaludin.udinmaunikah;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.awaludin.udinmaunikah.Programming.*;

public class CardBrain {

    private ArrayList<Petak> petaks = new ArrayList<>();
    private Pane pane;
    private double mouseAnchorX;
    private double mouseAnchorY;

    public CardBrain(ArrayList<Petak> petaks, Pane pane) {

        this.petaks = petaks;
        this.pane = pane;
    }

    public void makeDraggable(cardObj node){
        ArrayList<Petak> targets = this.petaks;
        final boolean[] yesKah = {false};
        double[] initialPosition = new double[2];

        node.setOnMousePressed(mouseEvent -> {
            // Click set to front
            node.toFront();
            // If left click go
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {

                // Set initialize position
                if (!yesKah[0]){
                    initialPosition[0] = node.getLayoutX();
                    initialPosition[1] = node.getLayoutY();
                    yesKah[0] = true;
                }

                mouseAnchorX = mouseEvent.getX();
                mouseAnchorY = mouseEvent.getY();
            }
        });

        node.setOnMouseDragged(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {

                node.setCursor(Cursor.CLOSED_HAND);
                node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
                node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
            }
        });

        node.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                node.setCursor(Cursor.DEFAULT);
                // set false first
                boolean placed = false;
                // itterate throough all the placeHolders/ladang
                for (Petak p : targets) {
                    // Get the rectangle
                    Rectangle r = p.getRectangle();
                    // if the mouse is over the rectangle, is enabled (for bonus purposes), and not have card in it
                    if (r.getBoundsInParent().contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())
                            && p.isEnabled() && p.isEmpty()) {
                        // place is valid
                        placed = true;

                        // Set the card to the position of slot
                        node.setLayoutX(r.getLayoutX());
                        node.setLayoutY(r.getLayoutY());

                        initialPosition[0] = r.getLayoutX();
                        initialPosition[1] = r.getLayoutY();

                        // set card on petak
                        p.setCardObj(node);

                        if (node.getPreviousPetak() != null) {
                            Petak petak = node.getPreviousPetak();
                            petak.setNull();
                        }

                        node.setPreviousPetak(p);

                        break;
                    }
                    if (r.getBoundsInParent().contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())
                    && !p.isEmpty() && p.isEnabled()
                    && node.previousPetak != p){
                        System.out.println("masuk");
                        GameObject go = node.getGameObject();
                        if (go instanceof Product){
                            Product pr = (Product) go;
                            GameObject go2 = p.getGameObject();

                            if (go2 instanceof Animal){
                                Animal animal = (Animal) go2;

                                if (animal.isEatAble(pr)){
                                    animal.Feed(pr.getWeight());
                                    botNot("Success: Weight now " + animal.GetWeight());
                                    pane.getChildren().remove(node);
                                }else {
                                    botNot("Cannot Feed That");
                                }

                            } else{
                                botNot("Not Animal");
                            }

                        } else if (go instanceof Item) {
                            System.out.println("bat");
                        }

                    }
                }
                // if not valid position place back to initialize spot
                if (!placed) {
                    node.setLayoutX(initialPosition[0]);
                    node.setLayoutY(initialPosition[1]);
                }
            }
        });
    }

    public void setGrid(Petak petak, Card kartu, Pane pane, CardBrain cb){
        Rectangle rec = petak.getRectangle();

        double layX = rec.getLayoutX();
        double layY = rec.getLayoutY();

        cardObj kar = new cardObj(kartu, petak, cb);

        petak.setCardObj(kar);

        kar.setLayoutY(layY);
        kar.setLayoutX(layX);

        pane.getChildren().add(kar);

        cb.makeDraggable(kar);
    }

    public void botNot(String message){

        Group myGroup = (Group) pane.lookup("#boardError");
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

    public static class cardObj extends Pane {
        private Card isiKartu;
        private Petak previousPetak;
        private GameObject gameObject;
        private CardBrain cardBrain;

        public cardObj(Card isiKartu, Petak previousPetak, CardBrain cardBrain){
            this.isiKartu = isiKartu;
            this.previousPetak = previousPetak;
            this.gameObject = this.isiKartu.convertToGameObject();
            this.cardBrain = cardBrain;

            String img = this.isiKartu.getImagePath();
            String name = this.gameObject.GetName();

            // Create the main card image view
            ImageView kard = new ImageView();
            kard.setFitHeight(114.0);
            kard.setFitWidth(112.0);
            kard.setPreserveRatio(true);
            kard.setImage(new Image(String.valueOf(GameController.class.getResource("Image/kard.png"))));

            System.out.println("Sss");

            System.out.println(img);

            // Create the text
            Text text = new Text(name);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setLayoutX(1.0);
            text.setWrappingWidth(76.48434448242188);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Snap ITC"));
            text.setLayoutY(92.0);
            text.setStrokeWidth(2.0);
            text.setText(name);

            System.out.println("Text created");

            // Create the icon image view
            ImageView icon = new ImageView();
            icon.setFitHeight(54.0);
            icon.setFitWidth(62.0);
            icon.setLayoutX(15.0);
            icon.setLayoutY(22.0);
            icon.setPickOnBounds(true);
            icon.setPreserveRatio(true);
            icon.setImage(new Image(String.valueOf(GameController.class.getResource(img))));

            System.out.println("Image icon");

            this.getChildren().addAll(kard, text, icon);

            this.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.SECONDARY){
                    try {

                        if (this.gameObject instanceof Animal){
                            dlgAnimal(isiKartu);
                        } else if (this.gameObject instanceof Plant) {
                            dlgPlant(isiKartu);
                        } else {
                            dlgProduct(this);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        public void setGameObject(GameObject gameObject){
            this.gameObject = gameObject;
        }

        public GameObject getGameObject() {
            return gameObject;
        }

        public void setCard(Card isiKartu){
            this.isiKartu = isiKartu;
        }

        public Card getCard(){
            return this.isiKartu;
        }

        public void setPreviousPetak(Petak petak){
            this.previousPetak = petak;
        }

        public Petak getPreviousPetak(){
            return this.previousPetak;
        }

        public void dlgAnimal(Card isiKartu) throws IOException {
            try{
                FXMLLoader dlgLoad = new FXMLLoader(getClass().getResource("Animal.fxml"));
                Parent root = dlgLoad.load();

                AnimalController animalController = dlgLoad.getController();

                animalController.setAnimal(isiKartu);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);

                stage.show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public void dlgPlant(Card isiKartu) throws IOException {
            try{
                FXMLLoader dlgLoad = new FXMLLoader(getClass().getResource("Plant.fxml"));
                Parent root = dlgLoad.load();

                PlantController plantController = dlgLoad.getController();

                plantController.setPlant(isiKartu);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public void dlgProduct(CardBrain.cardObj kar) throws IOException {
            try{
                FXMLLoader dlgProduct = new FXMLLoader(getClass().getResource("Product.fxml"));
                Parent root = dlgProduct.load();

                ProductController productController = dlgProduct.getController();

                productController.setProduct(kar, cardBrain.pane);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.setAlwaysOnTop(true);
                stage.show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }


}