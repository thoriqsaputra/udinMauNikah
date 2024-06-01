package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.TXTLoader;
import org.awaludin.udinmaunikah.Programming.JSONLoader;
import org.awaludin.udinmaunikah.Programming.YAMLLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveController {

    @FXML
    private TextField formatField;

    @FXML
    private TextField folderField;

    @FXML
    private Label statusLabel; // Add this

    private TXTLoader txtLoader = new TXTLoader();
    private JSONLoader jsonLoader = new JSONLoader();
    private YAMLLoader yamlLoader = new YAMLLoader();

    public void closeWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveState(MouseEvent event) {
        String format = formatField.getText().trim().toLowerCase();
        String folder = folderField.getText().trim();

        if (!format.equals("txt") && !format.equals("json") && !format.equals("yaml")) {
            statusLabel.setText("Invalid format. Please use 'txt', 'json', or 'yaml'.");
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
            } else if (format.equals("yaml")) {
                result = yamlLoader.save(path);
            }

            if (result) {
                statusLabel.setText("State saved successfully!");
            } else {
                statusLabel.setText("Failed to save state.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to save state due to an exception.");
        }
    }
}