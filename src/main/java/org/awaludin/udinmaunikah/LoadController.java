package org.awaludin.udinmaunikah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.GameManager;
import org.awaludin.udinmaunikah.Programming.GameObjectFactory;
import org.awaludin.udinmaunikah.Programming.TXTLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadController {

    private TXTLoader txtLoader = new TXTLoader();

    private HomeController hc;

    public void closeWindow(MouseEvent event) {
        hc.closeL();
    }

    public void setHom(HomeController hsc){
        hc = hsc;
    }

    @FXML
    void loadGame(MouseEvent event) throws IOException {
        FXMLLoader startGame = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = startGame.load();
        //Initialize Game
        GameManager.initGameManager();

        GameObjectFactory.Load();

        GameController gameController = startGame.getController();

        gameController.initializePlaceHolders();

        //gameController.changeDeck();

        String path = "src\\main\\resources\\org\\awaludin\\udinmaunikah";
        Path dirPath = Paths.get(path);
        txtLoader.load(dirPath.toAbsolutePath().toString());

        gameController.setPlayer();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
