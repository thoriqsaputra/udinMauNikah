package org.awaludin.udinmaunikah.Programming;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map.Entry;

import org.awaludin.udinmaunikah.Programming.Effect.Protect;
import org.awaludin.udinmaunikah.Programming.Effect.Trap;

import javafx.scene.shape.Rectangle;

public class Ladang {
    private List<Petak> grid;
    private boolean bearAttackActive;

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
        System.out.println("Bear attack initiated");
    
        int duration = 30 + random.nextInt(31); // Durasi serangan 30-60 detik
        System.out.println("Bear attack duration: " + duration + " seconds");
    
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                for (int i = 0; i < duration * 10; i++) {
                    System.out.println("Time remaining: " + (duration - (i / 10.0)) + " seconds");
                    Thread.sleep(100);
                }
    
                // Pilih subgrid berukuran 1-6 yang berkesinambungan
                List<Integer> subgridIndices = selectSubgrid();
                System.out.println("Bear attack subgrid: " + subgridIndices);
    
                // Setelah durasi berakhir, hilangkan tumbuhan/hewan yang masih berada dalam subgrid
                boolean trapFound = false;
                for (int index : subgridIndices) {
                    if (index < grid.size()) {
                        Petak petak = grid.get(index);
                        for (Entry<Item, Integer> item : petak.getItem().entrySet()) {
                            if (item.getKey().getEffect() instanceof Trap) {
                                trapFound = true;
                            }
                        }
                    }
                }
    
                if (trapFound) {
                    System.out.println("Trap found! Bear attack stopped.");
                    // mengubah beruang menjadi kartu Hewan yang bisa ditanam
                    Card beruang = new Card(new Animal("Beruang", 1, 25, AnimalType.OMNIVORE));
                    // memasukkan beruang ke dalam deck aktif jika masih ada tempat, jika tidak ada, maka tidak perlu melakukan apa apa
                    
                } else {
                    for (int index : subgridIndices) {
                        for (Entry<Item, Integer> p : this.grid.get(index).getItem().entrySet()) {
                            if (p.getKey().getEffect() instanceof Protect) {
                                this.grid.get(index).getItem().replace(p.getKey(), p.getValue()-1);
                                break;
                            }
                        }

                        // kalo gaada protect, petak akan dihancurkan, diganti dengan yang baru
                        Rectangle temp1 = this.grid.get(index).getRectangle();
                        Petak temp = new Petak(temp1);
                        this.grid.set(index, temp);
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
