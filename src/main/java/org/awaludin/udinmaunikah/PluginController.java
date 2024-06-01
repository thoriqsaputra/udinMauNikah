package org.awaludin.udinmaunikah;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PluginController {

    private HomeController hc;

    public void closeWindow(MouseEvent event) {
        hc.closeP();
    }

    public void setHc(HomeController hc) {
        this.hc = hc;
    }
}
