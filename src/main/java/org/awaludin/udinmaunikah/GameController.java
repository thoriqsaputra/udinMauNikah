package org.awaludin.udinmaunikah;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.awaludin.udinmaunikah.Programming.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;



public class GameController implements Initializable {

    @FXML
    private ImageView deck;

    @FXML
    private Text deckCount;

    @FXML
    private Label gulden;

    @FXML
    private Text name;

    @FXML
    private ImageView prof;

    @FXML
    private Label turn;

    @FXML
    private Pane paneManeh;

    @FXML
    private Text zenemy;

    @FXML
    private Text bearAttackTimer;

    @FXML
    private Label turntext;

    @FXML
    private Group turnbut;

    public static Pane mainPane;

    public static Map<Integer, Rectangle> rec = new HashMap<>();
    public static Map<Rectangle, Integer> recReverse = new HashMap<>();

    private Pane shufflePane;
    private Pane settingsPane;

    private CardBrain cardBrain;

    public static GameController gameC;

    public static boolean enemy;

    private Timeline bearAttackTimeline;

    @FXML
    private Group timer;


    @FXML
    private Group enemybut;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        mainPane = paneManeh;
        gameC = this;
        enemy = false;

        List<Rectangle> rectangles = mainPane.getChildren().stream()
                .filter(node -> node instanceof Rectangle)
                .map(node -> (Rectangle) node)
                .collect(Collectors.toList());

        // Sort rectangles by their IDs
        rectangles.sort(Comparator.comparing(Rectangle::getId, Comparator.nullsLast(Comparator.naturalOrder())));

        int k = 0;
        for (Rectangle rectangle : rectangles) {
            if (rectangle.getId() != null) {
                rec.put(k, rectangle);
                recReverse.put(rectangle, k);
                k++;
            }
        }

    }

    public void onBearAttackStart() {
        Platform.runLater(() -> {
            turntext.setText("GOOD LUCK!");
            turnbut.setMouseTransparent(true);
            enemybut.setMouseTransparent(true);
            timer.setOpacity(1.0);
            bearAttackTimer.setOpacity(1.0);
        });
    }

    public void removeCard(CardBrain.cardObj car){
        Platform.runLater(() -> {
           mainPane.getChildren().remove(car);
        });
    }

    public void onBearAttackEnd() {
        Platform.runLater(() -> {
            turntext.setText("NEXT TURN");
            turnbut.setMouseTransparent(false);
            enemybut.setMouseTransparent(false);
            timer.setOpacity(0.0);
            bearAttackTimer.setOpacity(0.5);
        });
    }

    public void onBearAttackUpdate(double timeRemaining) {
        Platform.runLater(() -> bearAttackTimer.setText(String.format("%.1f", timeRemaining)));
    }

    public void initializePlaceHolders() {
        for (int i = 0; i < 2; i++) {
            Ladang ldp = GameManager.getLadangList().get(i);
            Ladang lda = GameManager.getLadangDeckList().get(i);

            for (int j = 0; j < 6; j++) {
                Rectangle rectangle = rec.get(j);
                lda.add(new Petak(rectangle));
            }

            for (int j = 6; j < 26; j++) {
                Rectangle rectangle = rec.get(j);
                ldp.add(new Petak(rectangle));
            }

            for (int j = 26; j < 36; j++) {
                Rectangle rectangle = rec.get(j);
                ldp.add(new Petak(rectangle, false));
            }
        }
    }

    public void removePrevCards() {
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node n : mainPane.getChildren()) {
            if (n instanceof CardBrain.cardObj) {
                nodesToRemove.add(n);
            }
        }
        mainPane.getChildren().removeAll(nodesToRemove);
    }

    public void setDeck() {
        int turn = GameManager.getTurnCounter();

        Ladang deckAktif = GameManager.getLadangDeckList().get(turn);
        List<Petak> deck = deckAktif.getList();

        Ladang ladang = GameManager.getLadangList().get(turn);

        List<Petak> lad = ladang.getList();

        for (Petak p : lad) {
            CardBrain.cardObj tempC = p.getCardObj();
            if (tempC != null) {
                Card car = tempC.getCard();
                cardBrain.setGrid(p, car);
            } else {
//                System.err.println("tempC is null in Petak 'p'");
            }
        }

        List<Card> c = GameManager.PlayerInterface.getHand();
        for (int i = 0; i < deck.size(); i++) {
            if (c.get(i) != null && !deck.isEmpty()) {
                cardBrain.setGrid(deck.get(i), c.get(i));
            } else {
                deck.get(i).setNull();
            }
        }
    }

    public void isiDeck(List<Card> cards) {
        Ladang deckAktif = GameManager.getLadangDeckList().get(GameManager.getTurnCounter());
        List<Petak> deck = deckAktif.getList();

        Platform.runLater(()->{
            if (!cards.isEmpty()) {
                int j = 0;
                for (Petak p : deck) {
                    if (j >= cards.size()) {
                        break;
                    }
                    if (p.isEmpty()) {
                        cardBrain.setGrid(p, cards.get(j));
                        System.out.println("CARD NAME: " + cards.get(j).convertToGameObject().GetName());
                        j++;
                    }
                }
            }
        });
    }

    public void clearLadang(int index) {
        Ladang ladang = GameManager.getLadangList().get(index);
        List<Petak> lad = ladang.getList();
        for (Petak p : lad) {
            CardBrain.cardObj tempc = p.getCardObj();
            if (tempc != null) {
                mainPane.getChildren().remove(tempc);
            }
        }

    }

    @FXML
    void seeEnemy(MouseEvent event) {
        int idx = 0;

        if (!enemy) {
            clearLadang(GameManager.getTurnCounter());
            idx = (GameManager.getTurnCounter() == 0) ? 1 : 0;
            enemy = true;
            zenemy.setText("My Territory");
            zenemy.setFill(Color.GREEN);
        } else {
            int who = (GameManager.getTurnCounter() == 0) ? 1 : 0;
            clearLadang(who);
            idx = GameManager.getTurnCounter();
            enemy = false;
            zenemy.setText("Enemy's Territory");
            zenemy.setFill(Color.RED);
        }

        Ladang ladang = GameManager.getLadangList().get(idx);

        List<Petak> lad = ladang.getList();

        for (Petak p : lad) {
            CardBrain.cardObj tempC = p.getCardObj();
            if (tempC != null) {
                Card car = tempC.getCard();
                cardBrain.setGrid(p, car);
            } else {
//                System.err.println("tempC is null in Petak 'p'");
            }
        }
    }



    public void changeDeck() {
        Ladang la = GameManager.getLadangList().get(GameManager.getTurnCounter());
        List<Petak> lad = la.getList();

        cardBrain = new CardBrain(new ArrayList<>(lad));

        removePrevCards();

        setDeck();

        String deck = String.valueOf(GameManager.PlayerInterface.getCardCount());
        deckCount.setText(deck);

        shuffleMe();
    }

    public void setPlayer(){
        int player = GameManager.getTurnCounter();
        if (player == 0) {
            name.setText("Uchiha Baden");
            Image img = new Image(getClass().getResourceAsStream("Image/jin.png"));
            prof.setImage(img);
        } else {
            name.setText("Peter Panik");
            Image img = new Image(getClass().getResourceAsStream("Image/bondowoso.png"));
            prof.setImage(img);
        }

        String gulde = String.valueOf(GameManager.getGulden(player));
        gulden.setText(gulde);
        turn.setText(String.valueOf(GameManager.getTotalTurnCounter()));
    }

    public void shuffleMe() {
        GameManager.PlayerInterface.beginDraftPick();
        List<Card> cards = GameManager.PlayerInterface.getDraftList();

        try {
            FXMLLoader shuffle = new FXMLLoader(getClass().getResource("Shuffle.fxml"));
            shufflePane = shuffle.load();

            ShuffleController shuffleController = shuffle.getController();
            shuffleController.setShuffleCards(cards);
            shufflePane.toFront();

            paneManeh.getChildren().add(shufflePane);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeShufflePane() {
        paneManeh.getChildren().remove(shufflePane);
        String deck = String.valueOf(GameManager.PlayerInterface.getCardCount());
        deckCount.setText(deck);
        Ladang la = GameManager.getLadangList().get(GameManager.getTurnCounter());
        la.bearAttack();
    }

    public void openShop(MouseEvent mouseEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Shop.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Application.pushScene(stage.getScene());

            ShopController shopController = fxmlLoader.getController();
            shopController.setProductGrid();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void openSetting(MouseEvent mouseEvent) throws IOException {
        try {
            FXMLLoader dlgSett = new FXMLLoader(getClass().getResource("Settings.fxml"));
            settingsPane = dlgSett.load();

            SettingsController settingsController = dlgSett.getController();
            settingsController.setSettingsPane(settingsPane);

            mainPane.getChildren().add(settingsPane);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void nextTurn(MouseEvent event) {
        GameManager.nextTurn();
        System.out.println(GameManager.getTurnCounter());

        enemy = false;
        zenemy.setText("Enemy's Territory");
        zenemy.setFill(Color.RED);

        changeDeck();
        setPlayer();
    }
}