package org.awaludin.udinmaunikah;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.Product;

import java.util.Map;

public class ShopDeckController {

    @FXML
    private Text harga;

    @FXML
    private ImageView image;

    @FXML
    private Text jumlah;

    @FXML
    private Text name;

    public void setDeck(GameObject gameObject, Integer kuantitas) {
        String kuanti = String.valueOf(kuantitas);
        String nam = gameObject.GetName();
        String classname = gameObject.getClass().getSimpleName();
        String img = "Texture" + "/" + classname + "/" + gameObject.getId() + ".png";

        Product proTemp = (Product) gameObject;
        String har = String.valueOf(proTemp.getPrice());

        harga.setText(har);
        jumlah.setText(kuanti);
        image.setImage(new Image(getClass().getResourceAsStream(img)));
        name.setText(nam);

    }

}
