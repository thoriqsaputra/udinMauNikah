package org.awaludin.udinmaunikah;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShopController {
    public void goBack(MouseEvent mouseEvent) {
        try {
            // Get the current stage
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Get the previous scene from the stack
            Scene previousScene = Application.popScene();

            if (previousScene != null) {
                // Set the previous scene
                stage.setScene(previousScene);
                stage.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
