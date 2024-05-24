package org.awaludin.udinmaunikah;


import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.Product;
import org.awaludin.udinmaunikah.Programming.Toko;

import java.util.Map;

public class ShopDlgController {

    @FXML
    private Group BuyButton;

    private ShopController shpcontroller;

    @FXML
    private Text Name;

    @FXML
    private Label Price;

    public void setShpcontroller(ShopController shpcontroller) {
        this.shpcontroller = shpcontroller;
    }

    public void setDlgBuy (GameObject product){
        String name = product.GetName();
        Product pp = (Product) product;
        String harga = String.valueOf(pp.getPrice());
        Name.setText(name);
        Price.setText(harga);
        BuyButton.setOnMouseClicked(event -> {
            Toko.removeItems(product);

            shpcontroller.setProductGrid();


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });
    }

    public void closeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
