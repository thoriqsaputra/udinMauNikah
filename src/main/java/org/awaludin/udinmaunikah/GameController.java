package org.awaludin.udinmaunikah;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.awaludin.udinmaunikah.Programming.*;
import java.util.List;
import java.util.ArrayList;

public class GameController implements Initializable {

    @FXML
    private ImageView kard;

    @FXML
    private Rectangle DA1;

    @FXML
    private Rectangle DA2;

    @FXML
    private Rectangle DA3;

    @FXML
    private Rectangle DA4;

    @FXML
    private Rectangle DA5;

    @FXML
    private Rectangle DA6;

    @FXML
    private Rectangle LE1;

    @FXML
    private Rectangle LE10;

    @FXML
    private Rectangle LE2;

    @FXML
    private Rectangle LE3;

    @FXML
    private Rectangle LE4;

    @FXML
    private Rectangle LE5;

    @FXML
    private Rectangle LE6;

    @FXML
    private Rectangle LE7;

    @FXML
    private Rectangle LE8;

    @FXML
    private Rectangle LE9;

    @FXML
    private Rectangle LN1;

    @FXML
    private Rectangle LN10;

    @FXML
    private Rectangle LN11;

    @FXML
    private Rectangle LN12;

    @FXML
    private Rectangle LN13;

    @FXML
    private Rectangle LN14;

    @FXML
    private Rectangle LN15;

    @FXML
    private Rectangle LN16;

    @FXML
    private Rectangle LN17;

    @FXML
    private Rectangle LN18;

    @FXML
    private Rectangle LN19;

    @FXML
    private Rectangle LN2;

    @FXML
    private Rectangle LN20;

    @FXML
    private Rectangle LN3;

    @FXML
    private Rectangle LN4;

    @FXML
    private Rectangle LN5;

    @FXML
    private Rectangle LN6;

    @FXML
    private Rectangle LN7;

    @FXML
    private Rectangle LN8;

    @FXML
    private Rectangle LN9;

    @FXML
    private AnchorPane mainBoo;

    private ArrayList<Petak> petaks = new ArrayList<>();

    private Ladang ladang = new Ladang();

    private GameManager gameManager = new GameManager();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializePlaceHolders();

        GameManager.initGameManager();
        GameObjectFactory.Load();
        for (int i = 0; i < 2; i++) {
            GameManager.SetUpUtils.useDeck(i,"default");
        }
        System.out.println("YAYYY");

        shuffleMe();


    }

    public void shuffleMe(){
        GameManager.PlayerInterface.beginDraftPick();
        List<Card> cards;
        cards = GameManager.PlayerInterface.getDraftList();

        System.out.println("BATMAN");

        try{
            FXMLLoader shuffle = new FXMLLoader(getClass().getResource("Shuffle.fxml"));
            Parent root = shuffle.load();

            ShuffleController shuffleController = shuffle.getController();

            shuffleController.setShuffleCards(cards);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void initializePlaceHolders(){
        petaks.add(new Petak(LN1));
        petaks.add(new Petak(LN2));
        petaks.add(new Petak(LN3));
        petaks.add(new Petak(LN4));
        petaks.add(new Petak(LN5));
        petaks.add(new Petak(LN6));
        petaks.add(new Petak(LN7));
        petaks.add(new Petak(LN8));
        petaks.add(new Petak(LN9));
        petaks.add(new Petak(LN10));
        petaks.add(new Petak(LN11));
        petaks.add(new Petak(LN12));
        petaks.add(new Petak(LN13));
        petaks.add(new Petak(LN14));
        petaks.add(new Petak(LN15));
        petaks.add(new Petak(LN16));
        petaks.add(new Petak(LN17));
        petaks.add(new Petak(LN18));
        petaks.add(new Petak(LN19));
        petaks.add(new Petak(LN20));
        petaks.add(new Petak(LE1, false));
        petaks.add(new Petak(LE2, false));
        petaks.add(new Petak(LE3, false));
        petaks.add(new Petak(LE4, false));
        petaks.add(new Petak(LE5, false));
        petaks.add(new Petak(LE6, false));
        petaks.add(new Petak(LE7, false));
        petaks.add(new Petak(LE8, false));
        petaks.add(new Petak(LE9, false));
        petaks.add(new Petak(LE10, false));
        petaks.add(new Petak(DA1));
        petaks.add(new Petak(DA2));
        petaks.add(new Petak(DA3));
        petaks.add(new Petak(DA4));
        petaks.add(new Petak(DA5));
        petaks.add(new Petak(DA6));
    }

    public void openShop(MouseEvent mouseEvent) throws IOException {
        try{
            FXMLLoader startGame = new FXMLLoader(getClass().getResource("Shop.fxml"));
            Parent root = startGame.load();

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            Application.pushScene(stage.getScene());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void openSetting(MouseEvent mouseEvent) throws IOException {
        try{
            FXMLLoader dlgPlug = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Parent root = dlgPlug.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
