package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.GameObjectFactory;
import org.awaludin.udinmaunikah.Programming.Toko;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class ShopController {

    @FXML
    private GridPane productGrid;

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

                        Parent parent = fxmlLoader2.load();

                        ShopDlgController controller = fxmlLoader2.getController();

                        controller.setDlgBuy(item);
                        controller.setShpcontroller(this);

                        Scene scene = new Scene(parent);
                        scene.setFill(Color.TRANSPARENT);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.show();
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

    public void goBack(MouseEvent mouseEvent) {

        System.out.println("WOO");

        try {
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            Scene previousScene = Application.popScene();

            if (previousScene != null) {
                stage.setScene(previousScene);
                stage.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static Group createCard(String iconImageUrl, String itemName, String hargaText, String jumlahText) {
        Group cardGroup = new Group();

        // Card ImageView
        ImageView cardImageView = new ImageView(new Image(ShopController.class.getResourceAsStream("Image/shopDeck.png")));
        cardImageView.setFitHeight(145.0);
        cardImageView.setFitWidth(212.0);
        cardImageView.setPickOnBounds(true);
        cardImageView.setPreserveRatio(true);

        // Icon ImageView
        ImageView iconImageView = new ImageView(new Image(ShopController.class.getResourceAsStream(iconImageUrl)));
        iconImageView.setFitHeight(55.0);
        iconImageView.setFitWidth(68.0);
        iconImageView.setLayoutX(20.0);
        iconImageView.setLayoutY(42.0);
        iconImageView.setPickOnBounds(true);
        iconImageView.setPreserveRatio(true);

        // Labels
        Label hargaLabel = new Label("HARGA:");
        hargaLabel.setLayoutX(79.0);
        hargaLabel.setLayoutY(66.0);
        hargaLabel.setPrefHeight(17.0);
        hargaLabel.setPrefWidth(55.0);
        hargaLabel.setFont(new Font("Arial Black", 12.0));

        Label jumlahLabel = new Label("JUMLAH:");
        jumlahLabel.setLayoutX(76.0);
        jumlahLabel.setLayoutY(93.0);
        jumlahLabel.setFont(new Font("Arial Black", 12.0));

        Label hargaValueLabel = new Label(hargaText);
        hargaValueLabel.setLayoutX(139.0);
        hargaValueLabel.setLayoutY(66.0);
        hargaValueLabel.setPrefHeight(17.0);
        hargaValueLabel.setPrefWidth(55.0);
        hargaValueLabel.setFont(new Font("Arial Black", 12.0));

        Label jumlahValueLabel = new Label(jumlahText);
        jumlahValueLabel.setLayoutX(139.0);
        jumlahValueLabel.setLayoutY(93.0);
        jumlahValueLabel.setPrefHeight(17.0);
        jumlahValueLabel.setPrefWidth(55.0);
        jumlahValueLabel.setFont(new Font("Arial Black", 12.0));

        // Text
        Text nameText = new Text(itemName);
        nameText.setLayoutX(18.0);
        nameText.setLayoutY(32.0);
        nameText.setStrokeWidth(0.0);
        nameText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        nameText.setWrappingWidth(169.6719970703125);
        nameText.setFont(new Font("Snap ITC", 24.0));

        // Add children to the group
        cardGroup.getChildren().addAll(cardImageView, iconImageView, hargaLabel, jumlahLabel, hargaValueLabel, jumlahValueLabel, nameText);

        return cardGroup;
    }
}
