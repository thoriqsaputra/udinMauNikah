package org.awaludin.udinmaunikah;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PluginController {

    private Pane base;
    private Pane self;

    public void closeWindow(MouseEvent event) {
        base.getChildren().remove(self);
    }

    public void setPane(Pane base, Pane self) {
        this.base = base;
        this.self = self;
    }
}
