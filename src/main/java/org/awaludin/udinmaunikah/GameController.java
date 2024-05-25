package org.awaludin.udinmaunikah;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;
import java.net.URL;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.awaludin.udinmaunikah.Programming.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameController implements Initializable {

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
    private ImageView deck;

    @FXML
    private Text deckCount;

    @FXML
    private Label gulden;

    @FXML
    private AnchorPane mainBoo;

    @FXML
    private Text name;

    @FXML
    private ImageView prof;

    @FXML
    private Label turn;

    private List<Petak> petaks = new ArrayList<>();
    private ArrayList<Petak> deckActiv = new ArrayList<>();

    @FXML
    private Pane paneManeh;

    private ArrayList<Petak> placeHolder = new ArrayList<>();

    CardBrain cardBrain;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        GameObject test = GameObjectFactory.CreateGameObjectByID("HEWAN_002");
//        Card kartu = new Card(test);
        GameObject testa = GameObjectFactory.CreateGameObjectByID("PRODUCT_001");
//        Card kartu1 = new Card(testa);

        Toko.addItem(testa);
//
//        List<Ladang> deck = GameManager.getLadangDeckList();
//        Ladang deckA = deck.get(0);
//
//        cardBrain.setGrid(lad.getPetak(0),kartu1, paneManeh, cardBrain);
//        cardBrain.setGrid(deckA.getPetak(0), kartu, paneManeh, cardBrain);

//        removePrevCards();

//        Map<GameObject, Integer> bae = new HashMap<>();
//        Map<GameObject, Integer> baes = new HashMap<>();
//
//        GameObject testt = GameObjectFactory.CreateGameObjectByID("PRODUCT_001");
//        GameObject test2 = GameObjectFactory.CreateGameObjectByID("PRODUCT_002");
//        GameObject test3 = GameObjectFactory.CreateGameObjectByID("PRODUCT_003");
//        GameObject test4 = GameObjectFactory.CreateGameObjectByID("PRODUCT_004");
//        GameObject test5 = GameObjectFactory.CreateGameObjectByID("PRODUCT_005");
//        GameObject test6 = GameObjectFactory.CreateGameObjectByID("PRODUCT_006");
//        GameObject test7 = GameObjectFactory.CreateGameObjectByID("PRODUCT_007");
//        GameObject test8 = GameObjectFactory.CreateGameObjectByID("PRODUCT_008");
//        GameObject test9 = GameObjectFactory.CreateGameObjectByID("PRODUCT_009");
//
//        Toko.addItem(testa);
//        Toko.addItem(testt);
//        Toko.addItem(test2);
//        Toko.addItem(test3);
//        Toko.addItem(test4);
//        Toko.addItem(test5);
//        Toko.addItem(test6);
//        Toko.addItem(test7);
//        Toko.addItem(test8);
//        Toko.addItem(test9);
    }


    public Pane getPaneManeh(){
        return paneManeh;
    }

    public void removePrevCards(){
        placeHolder.clear();

        int turn = GameManager.getTurnCounter();

        Ladang ladang = GameManager.getLadangList().get(turn);
        List<Petak> lad = ladang.getList();
        placeHolder.addAll(lad);

        Ladang deckAktif = GameManager.getLadangDeckList().get(turn);
        List<Petak> deck = deckAktif.getList();
        placeHolder.addAll(deck);

        // Find all Rectangle objects within the AnchorPane
//        List<Rectangle> rectangles = paneManeh.getChildren().stream()
//                .filter(node -> node instanceof Rectangle) // Filter to get only Rectangle objects
//                .map(node -> (Rectangle) node) // Cast Node to Rectangle
//                .filter(rectangle -> rectangle.getId() != null) // Remove rectangles with null ids
//                .sorted(Comparator.comparing(Rectangle::getId)) // Sort rectangles by id
//                .collect(Collectors.toList()); // Collect the sorted rectangles

        for (Petak pepe : placeHolder){
            CardBrain.cardObj tempC = pepe.getCardObj();
            paneManeh.getChildren().remove(tempC);
        }
    }

    public void setDeck(){

        int turn = GameManager.getTurnCounter();
        Ladang deckAktif = GameManager.getLadangDeckList().get(turn);
        Ladang ladang = GameManager.getLadangList().get(turn);
        List<Petak> lad = ladang.getList();
        List<Petak> deck = deckAktif.getList();

        for(Petak p : lad){
            CardBrain.cardObj tempC = p.getCardObj();
            Card car = tempC.getCard();
            cardBrain.setGrid(p, car, paneManeh, cardBrain);
        }

        for (Petak dec : deck){
            CardBrain.cardObj tempC = dec.getCardObj();
            Card car = tempC.getCard();
            cardBrain.setGrid(dec, car, paneManeh, cardBrain);
        }


    }

    public void setPlayer(){
        removePrevCards();

        shuffleMe();

        cardBrain = new CardBrain(placeHolder, paneManeh);

        setDeck();

        int player = GameManager.getTurnCounter();
        if ( player == 0){
            name.setText("Uchiha Baden");
            Image img = new Image(getClass().getResourceAsStream("Image/jin.png"));
            prof.setImage(img);
            String gulde = String.valueOf(GameManager.getGulden(player));
            gulden.setText(gulde);

        } else {
            name.setText("Peter Panik");
            Image img = new Image(getClass().getResourceAsStream("Image/bondowoso.png"));
            prof.setImage(img);
            String gulde = String.valueOf(GameManager.getGulden(player));
            gulden.setText(gulde);
        }

        turn.setText(String.valueOf(GameManager.getTotalTurnCounter()));
    }

    public void shuffleMe(){
        GameManager.PlayerInterface.beginDraftPick();
        List<Card> cards;
        cards = GameManager.PlayerInterface.getDraftList();

        try{
            FXMLLoader shuffle = new FXMLLoader(getClass().getResource("Shuffle.fxml"));
            Parent root = shuffle.load();

            ShuffleController shuffleController = shuffle.getController();

            System.out.println(cards.toArray().length);

            shuffleController.setShuffleCards(cards);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.WINDOW_MODAL);
//            stage.setAlwaysOnTop(true);

            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void initializePlaceHolders(){

        for (int i = 0; i < 2; i++){

            Ladang ldp = GameManager.getLadangList().get(i);
            Ladang lda = GameManager.getLadangDeckList().get(i);

            ldp.add(new Petak(LN1));
            ldp.add(new Petak(LN2));
            ldp.add(new Petak(LN3));
            ldp.add(new Petak(LN4));
            ldp.add(new Petak(LN5));
            ldp.add(new Petak(LN6));
            ldp.add(new Petak(LN7));
            ldp.add(new Petak(LN8));
            ldp.add(new Petak(LN9));
            ldp.add(new Petak(LN10));
            ldp.add(new Petak(LN11));
            ldp.add(new Petak(LN12));
            ldp.add(new Petak(LN13));
            ldp.add(new Petak(LN14));
            ldp.add(new Petak(LN15));
            ldp.add(new Petak(LN16));
            ldp.add(new Petak(LN17));
            ldp.add(new Petak(LN18));
            ldp.add(new Petak(LN19));
            ldp.add(new Petak(LN20));

            ldp.add(new Petak(LE1, false));
            ldp.add(new Petak(LE2, false));
            ldp.add(new Petak(LE3, false));
            ldp.add(new Petak(LE4, false));
            ldp.add(new Petak(LE5, false));
            ldp.add(new Petak(LE6, false));
            ldp.add(new Petak(LE7, false));
            ldp.add(new Petak(LE8, false));
            ldp.add(new Petak(LE9, false));
            ldp.add(new Petak(LE10, false));

            lda.add(new Petak(DA1));
            lda.add(new Petak(DA2));
            lda.add(new Petak(DA3));
            lda.add(new Petak(DA4));
            lda.add(new Petak(DA5));
            lda.add(new Petak(DA6));
        }


    }

    public void openShop(MouseEvent mouseEvent) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Shop.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            Application.pushScene(stage.getScene());

            ShopController shopController = fxmlLoader.getController();

            shopController.setProductGrid();

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

    public void nextTurn(MouseEvent event) {
        System.out.println("yess");
        GameManager.nextTurn();

        System.out.println(GameManager.getTurnCounter());

        setPlayer();
    }
}
