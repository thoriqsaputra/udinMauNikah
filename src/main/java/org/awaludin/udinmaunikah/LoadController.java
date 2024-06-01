package org.awaludin.udinmaunikah;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoadController {

    private HomeController hc;

    public void closeWindow(MouseEvent event) {
        hc.closeL();
    }

    public void setHom(HomeController hsc){
        hc = hsc;
    }
}
