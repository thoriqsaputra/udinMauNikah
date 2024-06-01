package org.awaludin.udinmaunikah.Programming;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map.Entry;
import java.util.function.Consumer;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.CacheHint;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.awaludin.udinmaunikah.CardBrain;
import org.awaludin.udinmaunikah.GameController;
import org.awaludin.udinmaunikah.Programming.Effect.Protect;
import org.awaludin.udinmaunikah.Programming.Effect.Trap;
import org.awaludin.udinmaunikah.Programming.GameManager;

import javafx.scene.shape.Rectangle;

public class Ladang {
    private List<Petak> grid;
    private boolean bearAttackActive;
    private transient Runnable onBearAttackStart;
    private transient Runnable onBearAttackEnd;
    private transient Consumer<Double> onBearAttackUpdate;

    public Ladang() {
        grid = new ArrayList<>(30);
    }

    public void add(Petak petak) {
        grid.add(petak);
    }

    public void bonus() {
        for (int i = 0; i < 30; i++) {
            grid.get(i).enable();
        }
    }

    public List<Petak> getList() {
        return this.grid;
    }

    public void bonusHabis() {
        for (int i = 20; i < 30; i++) {
            grid.get(i).disable();
        }

        for (int i = 20; i < 30; i++) {
            Rectangle temp = grid.get(i).getRectangle();
            Petak temp2 = new Petak(temp, false);
            grid.set(i, temp2);
        }
    }

    public void bonusMusuh() {
        for (int i = 12; i < 30; i++) {
            grid.get(i).disable();
        }

        for (int i = 20; i < 30; i++) {
            Rectangle temp = grid.get(i).getRectangle();
            Petak temp2 = new Petak(temp, false);
            grid.set(i, temp2);
        }
    }

    public void bonusMusuhHabis() {
        for (int i = 12; i < 21; i++) {
            grid.get(i).enable();
        }
    }

    public List<Petak> getGrid() {
        return grid;
    }

    public Petak getPetak(int x) {
        return grid.get(x);
    }

    public Card convertToCard(int x) {
        return new Card(grid.get(x).getGameObject());
    }

    public void harvest(GameObject gameObject, int x) {
        if (grid.get(x).isReadyToHarvest()) {
            grid.get(x).Harvest();
        }
    }

    public boolean tryToUseItem(GameObject go, int x, boolean attacking) {
        if (go instanceof Item) {
            Item item = (Item) go;
            grid.get(x).setItem(item);
            return true;
        } else if (go instanceof Product) {
            Product product = (Product) go;
            if (grid.get(x).getGameObject() instanceof Animal) {
                Animal hewan = (Animal) grid.get(x).getGameObject();
                return hewan.isEatAble(product);
            } else {
                return false;
            }
        } else if (go instanceof Animal) {
            Animal hewan = (Animal) go;
            if (grid.get(x).getGameObject() != null) {
                grid.get(x).setGameObject(hewan);
                return true;
            } else {
                return false;
            }
        } else if (go instanceof Plant) {
            Plant tanaman = (Plant) go;
            if (grid.get(x).getGameObject() != null) {
                grid.get(x).setGameObject(tanaman);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean isHerbivoreAda(){
        try {
            for (Petak petak : grid) {
                if (petak.getGameObject() instanceof Animal) {
                    Animal hewan = (Animal) petak.getGameObject();
                    if (hewan.GetType() == AnimalType.HERBIVORE) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCarnivoreAda(){
        try {
            for (Petak petak : grid) {
                if (petak.getGameObject() instanceof Animal) {
                    Animal hewan = (Animal) petak.getGameObject();
                    if (hewan.GetType() == AnimalType.CARNIVORE) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<Integer, GameObject> getIngfo() {
        Map<Integer, GameObject> ingfo = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            if (grid.get(i).getGameObject() != null) {
                ingfo.put(i, grid.get(i).getGameObject());
            }
        }
        return ingfo;
    }

    public void setOnBearAttackStart(Runnable onBearAttackStart) {
        this.onBearAttackStart = onBearAttackStart;
    }

    public void setOnBearAttackEnd(Runnable onBearAttackEnd) {
        this.onBearAttackEnd = onBearAttackEnd;
    }

    public void setOnBearAttackUpdate(Consumer<Double> onBearAttackUpdate) {
        this.onBearAttackUpdate = onBearAttackUpdate;
    }

    public synchronized void bearAttack() {
        if (bearAttackActive) {
            return; // Serangan beruang sedang aktif, tidak melakukan serangan baru
        }
    
        Random random = new Random();
        int attackHappens = random.nextInt(4);
        if (attackHappens != 1) {
            System.out.println("No beruang for you");
            return; // Tidak ada serangan beruang pada turn ini
        }

        System.out.println("Bear attack initiated");
    
        int duration = 30 + random.nextInt(31); // Durasi serangan 30-60 detik

        System.out.println("Bear attack duration: " + duration + " seconds");
    
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                List<Integer> subgridIndices = selectSubgrid();
                GameController.gameC.onBearAttackStart();

                for (Integer i : subgridIndices) {
                    Rectangle tempc = grid.get(i).getRectangle();

                    DropShadow ds = new DropShadow();
                    ds.setColor(Color.RED);
                    ds.setSpread(0.5);
                    ds.setRadius(0);

                    tempc.setEffect(ds);
                    tempc.setCache(true); // Enable caching
                    tempc.setCacheHint(CacheHint.SPEED); // Hint for caching usage

                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(ds.radiusProperty(), 0, Interpolator.EASE_BOTH)),
                            new KeyFrame(Duration.seconds(1), new KeyValue(ds.radiusProperty(), 20, Interpolator.EASE_BOTH)),
                            new KeyFrame(Duration.seconds(2), new KeyValue(ds.radiusProperty(), 0, Interpolator.EASE_BOTH))
                    );
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.setAutoReverse(true);
                    timeline.play();
                }

                for (int i = 0; i < duration * 10; i++) {
                    GameController.gameC.onBearAttackUpdate(duration - (i / 10.0));
                    System.out.println("Time remaining: " + (duration - (i / 10.0)) + " seconds");
                    Thread.sleep(100);
                }
                for (Integer i : subgridIndices) {
                    Rectangle tempc = grid.get(i).getRectangle();
                    tempc.setEffect(null);
                    tempc.setCache(false);
                }
                GameController.gameC.onBearAttackEnd();

                // Pilih subgrid berukuran 1-6 yang berkesinambungan
                System.out.println("Bear attack subgrid: " + subgridIndices);
    
                // Setelah durasi berakhir, hilangkan tumbuhan/hewan yang masih berada dalam subgrid
                boolean trapFound = false;
                for (int index : subgridIndices) {
                    if (index < grid.size()) {
                        Petak petak = grid.get(index);
                        for (Entry<Item, Integer> item : petak.getItem().entrySet()) {
                            if (item.getKey().getEffect() instanceof Trap) {
                                trapFound = true;
                                // petak.kurangItems(item.getKey());
                                break;
                            }
                        }
                    }
                }
    
                if (trapFound) {
                    System.out.println("Trap found! Bear attack stopped.");
                    // mengubah beruang menjadi kartu Hewan yang bisa ditanam
                    GameObject ber = GameObjectFactory.CreateGameObjectByID("HEWAN_006");
                    // memasukkan beruang ke dalam deck aktif jika masih ada tempat, jika tidak ada, maka tidak perlu melakukan apa apa
                    Card beruang = new Card(ber);
                    if (GameManager.PlayerInterface.tryAddToHand(beruang)){
                        GameController.gameC.isiDeck(Collections.singletonList(beruang));
                    } else {
                        CardBrain.botNot("Hand Full");
                    }
                } else {
                    for (int index : subgridIndices) {
                        boolean protect = false;
                        for (Entry<Item, Integer> p : this.grid.get(index).getItem().entrySet()) {
                            if (p.getKey().getEffect() instanceof Protect) {
                                // this.grid.get(index).kurangItems(p.getKey());
                                protect = true;
                                break;
                            }
                        }

                        // kalo gaada protect, petak akan dihancurkan, diganti dengan yang baru
//                        Rectangle temp1 = this.grid.get(index).getRectangle();
//                        Petak temp = new Petak(temp1);
//                        this.grid.set(index, temp);
                        if (!protect && !getPetak(index).isEmpty()){
                            Petak p = this.grid.get(index);

                            CardBrain.cardObj tp = p.getCardObj();

                            System.out.println("Card "+ getPetak(index).getGameObject().GetName());

                            GameController.gameC.removeCard(tp);
                            p.setNull();
                        }
                    }
                    System.out.println("Bear attack ended, plants/animals removed from subgrid");
                }
    
                bearAttackActive = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    
        executor.shutdown();
    }
    
    private List<Integer> selectSubgrid() {
        Random random = new Random();
        int gridRows = 4; // Jumlah baris dalam grid
        int gridCols = 5; // Jumlah kolom dalam grid
        List<Integer> subgrid = new ArrayList<>();
    
        int startRow = random.nextInt(gridRows);
        int startCol = random.nextInt(gridCols);
        int subGridMax = random.nextInt(6);
    
        // Mulai dari titik acak dan tambahkan tetangga yang berdekatan sampai subgrid memiliki ukuran 1-6
        subgrid.add(startRow * gridCols + startCol);
        while (subgrid.size() < subGridMax) {
            int index = subgrid.get(random.nextInt(subgrid.size()));
            int row = index / gridCols;
            int col = index % gridCols;
    
            // Tambahkan tetangga yang valid
            addIfValid(subgrid, row - 1, col, gridRows, gridCols); // atas
            addIfValid(subgrid, row + 1, col, gridRows, gridCols); // bawah
            addIfValid(subgrid, row, col - 1, gridRows, gridCols); // kiri
            addIfValid(subgrid, row, col + 1, gridRows, gridCols); // kanan
        }
    
        return subgrid;
    }
    
    private void addIfValid(List<Integer> subgrid, int row, int col, int gridRows, int gridCols) {
        if (row >= 0 && row < gridRows && col >= 0 && col < gridCols) {
            int index = row * gridCols + col;
            if (!subgrid.contains(index)) {
                subgrid.add(index);
            }
        }
    }    
}
