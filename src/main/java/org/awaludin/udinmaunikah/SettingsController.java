package org.awaludin.udinmaunikah;
import javafx.animation.ScaleTransition;
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
import org.awaludin.udinmaunikah.Programming.GameManager;

public class SettingsController {

    private Pane settingsPane;

    public void setSettingsPane(Pane settingsPane) {
        this.settingsPane = settingsPane;
    }

    public void closeSetting(MouseEvent event) {
        GameController.mainPane.getChildren().remove(settingsPane);
    }

    public void openSave(MouseEvent mouseEvent) {
        try{
            FXMLLoader dlgSave = new FXMLLoader(getClass().getResource("Save.fxml"));
            Pane root = dlgSave.load();

            SaveController controller = dlgSave.getController();

            controller.setPane(settingsPane, root);

            settingsPane.getChildren().add(root);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void openLoad(MouseEvent mouseEvent) {
        try{
            FXMLLoader dlgLoad = new FXMLLoader(getClass().getResource("Load.fxml"));
            Pane root = dlgLoad.load();

            LoadController controller = dlgLoad.getController();

            controller.setPane(settingsPane, root);

            settingsPane.getChildren().add(root);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void openPlugin(MouseEvent mouseEvent) {
        try{
            FXMLLoader dlgPlug = new FXMLLoader(getClass().getResource("Plugin.fxml"));
            Pane root = dlgPlug.load();

            PluginController controller = dlgPlug.getController();

            controller.setPane(settingsPane, root);

            settingsPane.getChildren().add(root);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

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
}
