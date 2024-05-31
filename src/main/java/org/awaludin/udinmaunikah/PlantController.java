package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlantController {

    @FXML
    private ImageView img;

    @FXML
    private Label itemAktif;

    @FXML
    private Text name;

    @FXML
    private Label umur;

    private Card kar;

    private Pane plantPane;

    private CardBrain.cardObj go;

    @FXML
    void closeWindow(MouseEvent event) {
        GameController.mainPane.getChildren().remove(plantPane);
    }

    public void setPlant(CardBrain.cardObj iskartu, Pane pane){
        go = iskartu;

        plantPane = pane;

        Card kartu = iskartu.getCard();
        Petak petak = iskartu.getPreviousPetak();

        GameObject gameObject = kartu.convertToGameObject();
        Plant plant = (Plant) gameObject;
        int umurToHarvest = plant.GetAgeToHarvest();

        String umur = String.format("%d (%d)", plant.GetAge(), umurToHarvest);
        Image image = new Image(String.valueOf(AnimalController.class.getResource(kartu.getImagePath())));
        String name = plant.GetName();

        this.itemAktif.setText(petak.getItemsToList());
        this.umur.setText(umur);
        this.img.setImage(image);
        this.name.setText(name);

        kar = kartu;
    }

    public void panenBugi(MouseEvent event) {
        if (kar != null) {

            Plant plant = (Plant) kar.convertToGameObject();

            if(plant.isReadyToHarvest()){
                GameObject hasilPanen = plant.Harvest();

                Card hasilPanenCard = new Card(hasilPanen);

                if (hasilPanenCard != null && GameManager.PlayerInterface.tryAddToHand(hasilPanenCard)) {
                    Petak p = go.getPreviousPetak();

                    if (p != null){
                        p.setNull();
                    }

                    GameController.gameC.isiDeck(Collections.singletonList(hasilPanenCard));
                    GameController.mainPane.getChildren().remove(plantPane);
                    GameController.mainPane.getChildren().remove(go);

                } else {
                    GameController.mainPane.getChildren().remove(plantPane);
                    CardBrain.botNot("Hand Full!");
                }
            } else {
                GameController.mainPane.getChildren().remove(plantPane);
                CardBrain.botNot("Not Ready!");
            }
        }
    }
}
