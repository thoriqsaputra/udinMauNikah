package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.TXTLoader;
import org.awaludin.udinmaunikah.Programming.JSONLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveController {

    @FXML
    private TextField formatField;

    @FXML
    private TextField folderField;

    private TXTLoader txtLoader = new TXTLoader();
    private JSONLoader jsonLoader = new JSONLoader();

    public void closeWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveState(MouseEvent event) {
        String format = formatField.getText().trim().toLowerCase();
        String folder = folderField.getText().trim();

        if (!format.equals("txt") && !format.equals("json")) {
            System.out.println("Invalid format. Please use 'txt' or 'json'.");
            return;
        }

        String path = "src\\main\\resources\\org\\awaludin\\udinmaunikah\\" + folder;
        Path dirPath = Paths.get(path);

        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            boolean result = false;
            if (format.equals("txt")) {
                result = txtLoader.save(path);
            } else if (format.equals("json")) {
                result = jsonLoader.save(path);
            }

            if (result) {
                System.out.println("State saved successfully!");
            } else {
                System.out.println("Failed to save state.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
