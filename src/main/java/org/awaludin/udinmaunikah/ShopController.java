package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.Toko;

import java.io.IOException;
import java.util.Map;


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

}
