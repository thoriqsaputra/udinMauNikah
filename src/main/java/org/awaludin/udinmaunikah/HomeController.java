package org.awaludin.udinmaunikah;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import org.awaludin.udinmaunikah.Programming.GameManager;
import org.awaludin.udinmaunikah.Programming.GameObjectFactory;

import java.io.IOException;


public class HomeController {

    @FXML
    private Pane homePane;

    public void handleMouseEnter(javafx.scene.input.MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (source instanceof Node node) {
            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setDuration(Duration.millis(200));
            scaleTransition.setNode(node);
            scaleTransition.setFromX(1.0);
            scaleTransition.setFromY(1.0);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        }
    }

    public void handleMouseExit(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSource());
        Object source = mouseEvent.getSource();
        if (source instanceof Node node) {
            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setDuration(Duration.millis(200));
            scaleTransition.setNode(node);
            scaleTransition.setFromX(1.1);
            scaleTransition.setFromY(1.1);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        }
    }

    public void dlgLoad(MouseEvent mouseEvent) throws IOException {
        try{
            FXMLLoader dlgLoad = new FXMLLoader(getClass().getResource("Load.fxml"));
            Pane root = dlgLoad.load();


            LoadController lc = dlgLoad.getController();

            lc.setPane(homePane, root);

            homePane.getChildren().add(root);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void dlgPlug(MouseEvent mouseEvent) throws IOException {
        try{
            FXMLLoader dlgPlug = new FXMLLoader(getClass().getResource("Plugin.fxml"));
            Pane root = dlgPlug.load();

            PluginController lc = dlgPlug.getController();

            lc.setPane(homePane, root);

            homePane.getChildren().add(root);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        try{
            //Initialize Game
            GameManager.initGameManager();

            GameObjectFactory.Load();
            // Set Cards
            for (int i = 0; i < 2; i++) {
                GameManager.SetUpUtils.useDeck(i,"default");
            }

            FXMLLoader startGame = new FXMLLoader(getClass().getResource("Game.fxml"));
            Parent root = startGame.load();

            GameController gameController = startGame.getController();

            gameController.initializePlaceHolders();

            gameController.changeDeck();

            gameController.setPlayer();

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
