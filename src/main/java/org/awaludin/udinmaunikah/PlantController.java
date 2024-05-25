package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.Card;
import org.awaludin.udinmaunikah.Programming.GameManager;
import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.Plant;

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

    @FXML
    void closeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setPlant(Card kartu){
        GameObject gameObject = kartu.convertToGameObject();
        Plant plant = (Plant) gameObject;

        String umur = String.valueOf(plant.GetAge());
        Image image = new Image(String.valueOf(AnimalController.class.getResource(kartu.getImagePath())));
        String name = plant.GetName();

        this.umur.setText(umur);
        this.img.setImage(image);
        this.name.setText(name);

        kar = kartu;
    }

    public void panenBugi(MouseEvent event) {
        if (kar != null) {
            Plant plant = (Plant) kar.convertToGameObject();
            GameObject hasilPanen = plant.Harvest();
            Card hasilPanenCard = new Card(hasilPanen);
            if (hasilPanenCard != null && GameManager.PlayerInterface.getHand().size() < 6) {
                GameManager.PlayerInterface.tryAddToHand(hasilPanenCard);
            } else {
                System.out.println("Deck aktif penuh");
            }
        }
    }
}
