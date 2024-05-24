package org.awaludin.udinmaunikah;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShopController {
    public void goBack(MouseEvent mouseEvent) {
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
