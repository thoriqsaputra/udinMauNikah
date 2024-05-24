package org.awaludin.udinmaunikah.Programming;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.shape.Rectangle;

public class Ladang {
    private List<Petak> grid;
    private boolean bearAttackActive;

    public Ladang() {
        grid = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            grid.add(new Petak(new Rectangle(), true)); // Inisialisasi dengan objek dummy
        }
        for (int i = 20; i < 30; i++) {
            grid.get(i).disable();
        }
    }

    public void bonus() {
        for (int i = 0; i < 30; i++) {
            grid.get(i).enable();
        }
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

    public List<Petak> bonusMusuh(Ladang ladangMusuh) {
        for (int i = 12; i < 30; i++) {
            if (i % 5 == 4) {
                ladangMusuh.getPetak(i).disable();
            }
        }

        for (int i = 20; i < 30; i++) {
            Rectangle temp = ladangMusuh.getGrid().get(i).getRectangle();
            Petak temp2 = new Petak(temp, false);
            ladangMusuh.getGrid().set(i, temp2);
        }

        return ladangMusuh.getGrid();
    }

    public List<Petak> bonusMusuhHabis(Ladang ladangMusuh) {
        for (int i = 12; i < 21; i++) {
            ladangMusuh.getPetak(i).enable();
        }
        return ladangMusuh.getGrid();
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

    public synchronized void bearAttack() {
        if (bearAttackActive) {
            return; // Serangan beruang sedang aktif, tidak melakukan serangan baru
        }

        Random random = new Random();
        boolean attackHappens = random.nextBoolean();
        if (!attackHappens) {
            return; // Tidak ada serangan beruang pada turn ini
        }

        bearAttackActive = true;
        int subgridStartIndex = random.nextInt(20); 
        System.out.println("Bear attack starts at index " + subgridStartIndex);

        int duration = 30 + random.nextInt(31); 
        System.out.println("Bear attack duration: " + duration + " seconds");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                for (int i = 0; i < duration * 10; i++) {
                    System.out.println("Time remaining: " + (duration - (i / 10.0)) + " seconds");
                    Thread.sleep(100);
                }

                // Setelah durasi berakhir, hilangkan tumbuhan/hewan yang masih berada dalam subgrid
                for (int i = subgridStartIndex; i < subgridStartIndex + 6; i++) {
                    if (i < grid.size()) {
                        grid.set(i, new Petak(new Rectangle(), false)); // Hapus objek
                    }
                }
                bearAttackActive = false;
                System.out.println("Bear attack ended, plants/animals removed from subgrid");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.shutdown();
    }
}
