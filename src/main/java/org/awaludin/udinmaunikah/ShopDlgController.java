package org.awaludin.udinmaunikah;


import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ShopDlgController {

    @FXML
    private Group BuyButton;

    private ShopController shpcontroller;

    @FXML
    private Text Name;

    @FXML
    private Label Price;

    private Pane poi;

    public void setShpcontroller(ShopController shpcontroller) {
        this.shpcontroller = shpcontroller;
    }

    public void setDlgBuy (GameObject product, Pane pane){

        String name = product.GetName();
        Product pp = (Product) product;
        String harga = String.valueOf(pp.getPrice());
        Name.setText(name);
        Price.setText(harga);

        BuyButton.setOnMouseClicked(event -> {
            if (pp.getPrice() > GameManager.getGulden(GameManager.getTurnCounter())){
                shpcontroller.botNot("Not enough money");
                shpcontroller.closeShop();
                return;
            }

            Card ko = new Card(pp);

            if (!GameManager.PlayerInterface.tryAddToHand(ko)){
                shpcontroller.botNot("Hand full!");
                shpcontroller.closeShop();
                return;
            }

            Toko.removeItems(product);

            GameController.gameC.isiDeck(Collections.singletonList(ko));

            shpcontroller.setProductGrid();

            shpcontroller.closeShop();

            GameManager.buyItems(product);

            shpcontroller.botNot("Sucess!");
        });
    }

    public void closeWindow(MouseEvent event) {
        shpcontroller.closeShop();
    }
}
