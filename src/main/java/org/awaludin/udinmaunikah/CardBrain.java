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

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javafx.util.Duration;
import org.awaludin.udinmaunikah.Programming.*;

public class CardBrain {

    private ArrayList<Petak> petaks;
    private double mouseAnchorX;
    private double mouseAnchorY;

    public CardBrain(ArrayList<Petak> petaks) {
        this.petaks = petaks;
    }

    public void makeDraggable(cardObj node){
        ArrayList<Petak> targets = new ArrayList<>(this.petaks);
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
            node.setCursor(Cursor.DEFAULT);
            GameObject go = node.getGameObject();

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                System.out.println("ENEMY: " + GameController.enemy);
                if (GameController.enemy) {
                    int idx = (GameManager.getTurnCounter() == 0) ? 1 : 0;
                    Ladang l = GameManager.getLadangList().get(idx);
                    List<Petak> lp = l.getList();

                    for (Petak p : lp) {
                        Rectangle r = p.getRectangle();
                        if (r.getBoundsInParent().contains(mouseEvent.getSceneX(), mouseEvent.getSceneY()) && p.isEnabled()) {
                            if (go instanceof Item item) {
                                Effect effect = item.getEffect();
                                if (!(effect instanceof Effect.Delay) && !(effect instanceof Effect.Destroy)
                                        && !(effect instanceof Effect.Layout)) {
                                    CardBrain.botNot("Nuh uh");
                                    node.setLayoutX(initialPosition[0]);
                                    node.setLayoutY(initialPosition[1]);
                                    return;
                                }

                                if (p.isEmpty() && !(effect instanceof Effect.Layout)){
                                    break;
                                }

                                if (node.getPreviousPetak() != null) {
                                    Petak petak = node.getPreviousPetak();
                                    petak.setNull();
                                    if (petak.getRectangle().getId().startsWith("DA")) {
                                        GameManager.PlayerInterface.useCardT(node.getCard());
                                    }
                                }

                                GameController.mainPane.getChildren().remove(node);
                                p.setItem(item);
                                return;
                            } else {
                                CardBrain.botNot("Nuh uh");
                                node.setLayoutX(initialPosition[0]);
                                node.setLayoutY(initialPosition[1]);
                                return;
                            }
                        }
                    }
                }

                // set false first
                boolean placed = false;
                // itterate throough all the placeHolders/ladang
                for (Petak p : targets) {
                    // Get the rectangle
                    Rectangle r = p.getRectangle();
                    // if the mouse is over the rectangle, is enabled (for bonus purposes), and not have card in it
                    if (r.getBoundsInParent().contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())
                            && p.isEnabled() && p.isEmpty()) {
                        // if product is being placed break;
                        if (go instanceof Product || go instanceof Item) {

                            break;
                        }
                        // place is valid
                        placed = true;

                        // Set the card to the position of slot
                        node.setLayoutX(r.getLayoutX());
                        node.setLayoutY(r.getLayoutY());

                        initialPosition[0] = r.getLayoutX();
                        initialPosition[1] = r.getLayoutY();

                        // set card on petak
                        p.setCardObj(node);

                        // if previous petak is not null set it to null
                        if (node.getPreviousPetak() != null) {
                            Petak petak = node.getPreviousPetak();
                            petak.setNull();
                            // Remove card from deck if placing a card to ladang
                            if (petak.getRectangle().getId().startsWith("DA")) {
                                GameManager.PlayerInterface.useCardT(node.getCard());
                            }
                        }
                        // set the previous petak with the placed petak
                        node.setPreviousPetak(p);

                        break;
                    }

                    if (r.getBoundsInParent().contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())
                            && !p.isEnabled() && (go instanceof Item)){

                        Effect ef = ((Item) go).getEffect();
                        if (ef instanceof Effect.Layout) {
                            if (node.getPreviousPetak() != null) {
                                Petak petak = node.getPreviousPetak();
                                petak.setNull();
                                if (petak.getRectangle().getId().startsWith("DA")) {
                                    GameManager.PlayerInterface.useCardT(node.getCard());
                                }
                            }

                            GameController.mainPane.getChildren().remove(node);
                            p.setItem((Item) go);
                        }
                    }

                    // for items and product
                    if (r.getBoundsInParent().contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())
                            && !p.isEmpty() && p.isEnabled()
                            && node.previousPetak != p) {
                        // if its a product
                        if (go instanceof Product) {
                            Product pr = (Product) go;
                            GameObject go2 = p.getGameObject();
                            // if its an animal feed it
                            if (go2 instanceof Animal) {
                                Animal animal = (Animal) go2;
                                // try to feed if can
                                if (animal.isEatAble(pr)) {
                                    animal.Feed(pr.getWeight());
                                    botNot("Success: Weight now " + animal.GetWeight());
                                    placed = true;
                                } else {
                                    botNot("Cannot Feed That");
                                }
                            } else {
                                botNot("Not Animal");
                            }
                            // if it is an item try applying it
                        } else if (go instanceof Item) {
                            Item item = (Item) go;
                            Effect effect = item.getEffect();
                            if (effect instanceof Effect.Destroy || effect instanceof Effect.Delay) {
                                if (!GameController.enemy) {
                                    botNot("WHY?!?!?");
                                    node.setLayoutX(initialPosition[0]);
                                    node.setLayoutY(initialPosition[1]);
                                    return;
                                }
                            }

                            if (node.getPreviousPetak() != null) {
                                Petak petak = node.getPreviousPetak();
                                petak.setNull();
                                if (petak.getRectangle().getId().startsWith("DA")) {
                                    GameManager.PlayerInterface.useCardT(node.getCard());
                                }
                            }

                            GameController.mainPane.getChildren().remove(node);
                            p.setItem(item);
                        } else {
                            botNot("Can't do that!");
                        }

                        if (placed) {
                            if (node.getPreviousPetak() != null) {
                                Petak petak = node.getPreviousPetak();
                                petak.setNull();

                                if (petak.getRectangle().getId().startsWith("DA")) {
                                    GameManager.PlayerInterface.useCardT(node.getCard());
                                }
                            }
                            GameController.mainPane.getChildren().remove(node);
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

    public void setGrid(Petak petak, Card kartu){
        Rectangle rec = petak.getRectangle();

        double layX = rec.getLayoutX();
        double layY = rec.getLayoutY();

        cardObj kar = new cardObj(kartu, petak);

        petak.setCardObj(kar);

        kar.setLayoutY(layY);
        kar.setLayoutX(layX);

        GameController.mainPane.getChildren().add(kar);

        makeDraggable(kar);
    }

    public static void botNot(String message){
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

    public static class cardObj extends Pane {
        private Card isiKartu;
        private Petak previousPetak;
        private GameObject gameObject;

        public cardObj(Card isiKartu, Petak previousPetak){
            this.isiKartu = isiKartu;
            this.previousPetak = previousPetak;
            this.gameObject = this.isiKartu.convertToGameObject();

            String img = this.isiKartu.getImagePath();
            String name = this.gameObject.GetName();

            // Create the main card image view
            ImageView kard = new ImageView();
            kard.setFitHeight(114.0);
            kard.setFitWidth(112.0);
            kard.setPreserveRatio(true);
            kard.setImage(new Image(String.valueOf(GameController.class.getResource("Image/kard.png"))));

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

            // Create the icon image view
            ImageView icon = new ImageView();
            icon.setFitHeight(54.0);
            icon.setFitWidth(62.0);
            icon.setLayoutX(15.0);
            icon.setLayoutY(22.0);
            icon.setPickOnBounds(true);
            icon.setPreserveRatio(true);
            icon.setImage(new Image(String.valueOf(GameController.class.getResource(img))));

            this.getChildren().addAll(kard, text, icon);

            this.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.SECONDARY){
                    try {
                        if (this.gameObject instanceof Animal){
                            dlgAnimal(this);
                        } else if (this.gameObject instanceof Plant) {
                            dlgPlant(this);
                        } else if (this.gameObject instanceof Product){
                            dlgProduct(this);
                        } else {
                            dlgItem(isiKartu);
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

        public void dlgAnimal(CardBrain.cardObj isiKartu) throws IOException {
            try{
                FXMLLoader dlgLoad = new FXMLLoader(getClass().getResource("Animal.fxml"));
                Pane root = dlgLoad.load();

                AnimalController animalController = dlgLoad.getController();

                animalController.setAnimal(isiKartu, root);

                GameController.mainPane.getChildren().add(root);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public void dlgPlant(CardBrain.cardObj isiKartu) throws IOException {
            try{
                FXMLLoader dlgLoad = new FXMLLoader(getClass().getResource("Plant.fxml"));
                Pane root = dlgLoad.load();

                PlantController plantController = dlgLoad.getController();

                plantController.setPlant(isiKartu, root);

                GameController.mainPane.getChildren().add(root);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public void dlgProduct(CardBrain.cardObj kar) throws IOException {
            try{
                FXMLLoader dlgProduct = new FXMLLoader(getClass().getResource("Product.fxml"));
                Pane root = dlgProduct.load();

                ProductController productController = dlgProduct.getController();

                productController.setProduct(kar, root);

                GameController.mainPane.getChildren().add(root);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public void dlgItem(Card kar) throws IOException {
            try{
                FXMLLoader dlgItem = new FXMLLoader(getClass().getResource("Item.fxml"));
                Pane root = dlgItem.load();

                ItemController itemController = dlgItem.getController();

                itemController.setItem(kar, root);

                GameController.mainPane.getChildren().add(root);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }


}