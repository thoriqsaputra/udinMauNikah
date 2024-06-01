package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.awaludin.udinmaunikah.Programming.TXTLoader;
import org.awaludin.udinmaunikah.Programming.JSONLoader;
import org.awaludin.udinmaunikah.Programming.YAMLLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveController {

    @FXML
    private ComboBox<String> formatComboBox;

    @FXML
    private TextField folderField;

    @FXML
    private Label statusLabel; // Add this

    private TXTLoader txtLoader = new TXTLoader();
    private JSONLoader jsonLoader = new JSONLoader();
    private YAMLLoader yamlLoader = new YAMLLoader();

    private Pane base;
    private Pane self;

    public void closeWindow(MouseEvent event) throws IOException {
        base.getChildren().remove(self);
    }

    public void setPane(Pane base, Pane self) {
        this.base = base;
        this.self = self;
    }

    @FXML
    public void saveState(MouseEvent event) {
        String format = formatComboBox.getValue();
        String folder = folderField.getText().trim();

        if (format == null || (!format.equals("txt") && !format.equals("json") && !format.equals("yaml"))) {
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