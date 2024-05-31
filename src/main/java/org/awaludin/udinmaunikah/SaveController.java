package org.awaludin.udinmaunikah;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.TXTLoader;

import java.io.IOException;

public class SaveController {

    private TXTLoader txtLoader = new TXTLoader();

    public void closeWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void saveState(MouseEvent event) {
        String path = "src\\main\\resources\\org\\awaludin\\udinmaunikah"; // replace with actual path or get from a TextField
        boolean result = txtLoader.save(path);
        if (result) {
            System.out.println("State saved successfully!");
        } else {
            System.out.println("Failed to save state.");
        }
    }
}
