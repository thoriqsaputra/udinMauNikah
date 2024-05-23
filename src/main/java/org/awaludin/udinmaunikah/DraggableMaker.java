package org.awaludin.udinmaunikah;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

import org.awaludin.udinmaunikah.Programming.GameObject;
import org.awaludin.udinmaunikah.Programming.Petak;

public class DraggableMaker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Node node, ArrayList<Petak> targets){

        double[] initialPosition = {node.getLayoutX(), node.getLayoutY()};

        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

        node.setOnMouseReleased(mouseEvent -> {
            boolean placed = false;
            for (Petak p : targets) {
                Rectangle r = p.getRectangle();
                if (r.getBoundsInParent().contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())
                && p.isEnabled() && p.isEmpty()) {
                    placed = true;
                    node.setLayoutX(r.getLayoutX());
                    node.setLayoutY(r.getLayoutY());
                    initialPosition[0] = r.getLayoutX();
                    initialPosition[1] = r.getLayoutY();
                    p.setGameObject(new GameObject());
                    break;
                }
            }
            if (!placed) {
                node.setLayoutX(initialPosition[0]);
                node.setLayoutY(initialPosition[1]);
            }
        });
    }


}