package org.awaludin.udinmaunikah;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import org.awaludin.udinmaunikah.Programming.*;

public class CardBrain {

    private ArrayList<Petak> petaks = new ArrayList<>();
    private double mouseAnchorX;
    private double mouseAnchorY;

    public CardBrain(ArrayList<Petak> petaks) {
        this.petaks = petaks;
    }

    public void makeDraggable(cardObj node){
        ArrayList<Petak> targets = this.petaks;
        final boolean[] yesKah = {false};
        double[] initialPosition = new double[2];

        node.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {

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
                boolean placed = false;
                for (Petak p : targets) {
                    Rectangle r = p.getRectangle();
                    if (r.getBoundsInParent().contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())
                            && p.isEnabled() && p.isEmpty()) {
                        placed = true;
                        node.setLayoutX(r.getLayoutX()-7);
                        node.setLayoutY(r.getLayoutY());
                        initialPosition[0] = r.getLayoutX();
                        initialPosition[1] = r.getLayoutY();
                        p.setGameObject(new GameObject());

                        if (node.getPreviousPetak() != null) {
                            Petak petak = node.getPreviousPetak();
                            petak.setGameObject(null);
                        }
                        node.setPreviousPetak(p);

                        break;
                    }
                }
                if (!placed) {

                    node.setLayoutX(initialPosition[0]-7);
                    node.setLayoutY(initialPosition[1]);
                }
            }
        });
    }

    public static class cardObj extends Pane {
        private Card isiKartu;
        private Petak previousPetak;
        private GameObject gameObject;
        private CardBrain cardBrain;

        public cardObj(Card isiKartu, Petak previousPetak){
            this.isiKartu = isiKartu;
            this.previousPetak = previousPetak;
            this.gameObject = this.isiKartu.convertToGameObject();
            this.cardBrain = cardBrain;

            this.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.SECONDARY){
                    try {

                        if (this.gameObject instanceof Animal){
                            dlgAnimal(isiKartu);
                        } else if (this.gameObject instanceof Plant) {
                            dlgPlant(isiKartu);
                        } else {
                            dlgProduct(isiKartu);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

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

        public void dlgProduct(Card isiKartu) throws IOException {
            try{
                FXMLLoader dlgProduct = new FXMLLoader(getClass().getResource("Product.fxml"));
                Parent root = dlgProduct.load();

                ProductController productController = dlgProduct.getController();

                productController.setProduct(isiKartu);

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

//    public static Pane createCard(String iconImageUrl, String cardText) {
//        // Create the main Group
//        Pane kartu = new Pane();
//
//        // Create the main card image view
//        ImageView kard = new ImageView();
//        kard.setFitHeight(114.0);
//        kard.setFitWidth(112.0);
//        kard.setPreserveRatio(true);
//        kard.setImage(new Image(String.valueOf(GameController.class.getResource("Image/kard.png"))));
//
//        // Create the text
//        Text text = new Text(cardText);
//        text.setLayoutX(20.0);
//        text.setFill(Color.WHITE);
//        text.setFont(Font.font("Snap ITC"));
//        text.setLayoutY(93.0);
//        text.setStrokeWidth(2.0);
//        text.setText(cardText);
//
//        System.out.println("Text created");
//
//        // Create the icon image view
//        ImageView icon = new ImageView();
//        icon.setFitHeight(54.0);
//        icon.setFitWidth(62.0);
//        icon.setLayoutX(15.0);
//        icon.setLayoutY(22.0);
//        icon.setPickOnBounds(true);
//        icon.setPreserveRatio(true);
//        icon.setImage(new Image(String.valueOf(GameController.class.getResource(iconImageUrl))));
//
//        System.out.println("Image icon");
//
//        // Add all children to the group
//        kartu.getChildren().addAll(kard, text, icon);
//        kartu.setPrefSize(112, 114);
//
//        return kartu;
//    }

}