package org.awaludin.udinmaunikah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.GameManager;
import org.awaludin.udinmaunikah.Programming.GameObjectFactory;
import org.awaludin.udinmaunikah.Programming.TXTLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadController {

    @FXML
    private Text botNot;

    @FXML
    private TextField folder;

    @FXML
    private TextField format;

    private TXTLoader txtLoader = new TXTLoader();

    private Pane base;
    private Pane self;

    public void closeWindow(MouseEvent event) {
        base.getChildren().remove(self);
    }

    public void setPane(Pane hsc, Pane sel) {
        base = hsc;
        self = sel;
    }

    @FXML
    void loadGame(MouseEvent event) throws IOException {
        String forma = format.getText().trim().toLowerCase();
        String folde = folder.getText().trim();

        if (!forma.equals("txt") && !forma.equals("json") && !forma.equals("yaml")) {
            botNot.setText("Invalid format. Please use 'txt', 'json', or 'yaml'.");
            botNot.setOpacity(1.0);
            return;
        }

        String basePath = "src\\main\\resources\\org\\awaludin\\udinmaunikah";
        Path dirPath = Paths.get(basePath, folde);

        if (!Files.exists(dirPath)) {
            botNot.setText("Folder not found. Please check the folder name and try again.");
            botNot.setOpacity(1.0);
            return;
        }

        FXMLLoader startGame = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = startGame.load();
        // Initialize Game
        GameManager.initGameManager();

        GameObjectFactory.Load();

        GameController gameController = startGame.getController();

        gameController.initializePlaceHolders();

        // gameController.changeDeck();

        txtLoader.load(dirPath.toAbsolutePath().toString());

        gameController.refresh();

        gameController.setPlayer();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}