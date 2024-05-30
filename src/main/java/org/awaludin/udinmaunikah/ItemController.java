package org.awaludin.udinmaunikah;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.awaludin.udinmaunikah.Programming.Card;
import org.awaludin.udinmaunikah.Programming.Item;


public class ItemController {

    @FXML
    private ImageView img;

    @FXML
    private Text name;

    private Pane paneItem;

    @FXML
    void closeWindow(MouseEvent event) {
        GameController.mainPane.getChildren().remove(paneItem);
    }

    public void setItem(Card cars, Pane pane) {
        paneItem = pane;

        Item item = (Item) cars.convertToGameObject();

        name.setText(item.GetName());

        Image imgs = new Image(String.valueOf(ItemController.class.getResource(cars.getImagePath())));

        img.setImage(imgs);
    }

}
