package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.awaludin.udinmaunikah.Programming.GameManager;

public class EndGameController {

    @FXML
    private Pane TIEMAN;

    @FXML
    private ImageView b1;

    @FXML
    private ImageView b2;

    @FXML
    private ImageView bitcoin;

    @FXML
    private Group boardError;

    @FXML
    private Text error;

    @FXML
    private ImageView imgWinner;

    @FXML
    private Text m1;

    @FXML
    private Text m2;

    @FXML
    private ImageView ooo;

    @FXML
    private Text p1;

    @FXML
    private Text p2;

    @FXML
    private Pane shopMain;

    @FXML
    private Text tie;

    @FXML
    private Text winnerGuld;

    @FXML
    private Text winnerName;

    @FXML
    void goBack(MouseEvent event) {
        System.out.println("WOO");
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene previousScene = Application.popScene();

            if (previousScene != null) {
                stage.setScene(previousScene);
                stage.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setWinner(int who){

        if (who == -1){
            p1.setText("Uchiha Baden");
            String gulde = String.valueOf(GameManager.getGulden(0));
            m1.setText(gulde);
            p2.setText("Peter Panik");
            String gulde2 = String.valueOf(GameManager.getGulden(0));
            m2.setText(gulde2);
            return;
        }
        TIEMAN.setOpacity(0);
        ooo.setOpacity(1.0);
        winnerGuld.setOpacity(1.0);
        winnerName.setOpacity(1.0);
        bitcoin.setOpacity(1.0);
        imgWinner.setOpacity(1.0);

        if (who == 0) {
            winnerName.setText("Uchiha Baden");
            Image img = new Image(getClass().getResourceAsStream("Image/jin.png"));
            imgWinner.setImage(img);
        } else {
            winnerName.setText("Peter Panik");
            Image img = new Image(getClass().getResourceAsStream("Image/bondowoso.png"));
            imgWinner.setImage(img);
        }

        String gulde = String.valueOf(GameManager.getGulden(who));
        winnerGuld.setText(gulde);
    }

}