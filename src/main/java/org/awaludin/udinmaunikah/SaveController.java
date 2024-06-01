package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.TXTLoader;

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

    public void closeWindow(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveState(MouseEvent event) {
        String format = formatField.getText().trim();
        String folder = folderField.getText().trim();

        if (!format.equalsIgnoreCase("txt")) {
            System.out.println("Invalid format. Please use 'txt'.");
            return;
        }

        String path = "src\\main\\resources\\org\\awaludin\\udinmaunikah\\" + folder;
        Path dirPath = Paths.get(path);

        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            boolean result = txtLoader.save(path);
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