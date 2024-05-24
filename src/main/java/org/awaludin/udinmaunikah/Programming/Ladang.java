package org.awaludin.udinmaunikah.Programming;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.awaludin.udinmaunikah.Programming.Effect.Layout;
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
            if (item.getEffect() instanceof Layout) {
                grid.get(x).setItemBonus(item, this, attacking);
                return true;
            } else {
                grid.get(x).setItem(item);
                return true;
            }
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
        int subgridStartIndex = random.nextInt(25); // Pilih indeks awal subgrid (0-24)
        System.out.println("Bear attack starts at index " + subgridStartIndex);

        int duration = 30 + random.nextInt(31); // Durasi serangan 30-60 detik
        System.out.println("Bear attack duration: " + duration + " seconds");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                for (int i = 0; i < duration * 10; i++) {
                    System.out.println("Time remaining: " + (duration - (i / 10.0)) + " seconds");
                    Thread.sleep(100);
                }

                // Setelah durasi berakhir, hilangkan tumbuhan/hewan yang masih berada dalam subgrid
                boolean trapFound = false;
                boolean protectFound = false;
                for (int i = subgridStartIndex; i < subgridStartIndex + 6; i++) {
                    if (i < grid.size()) {
                        Petak petak = grid.get(i);
                        for (Item item : petak.getItem()) {
                            if (item.getEffect() instanceof Protect) {
                                protectFound = true;
                            } else if (item.getEffect() instanceof Trap) {
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
                    
                } else if (protectFound) {
                    System.out.println("Protect found! Bear attack stopped.");
                } else {
                    for (int i = subgridStartIndex; i < subgridStartIndex + 6; i++) {
                        if (i < grid.size()) {
                            grid.set(i, new Petak(new Rectangle(), false)); // Hapus objek
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
}

abstract class Effect {
    // ada 5 jenis effect dalam game ini:
    // Accelerate: Menambah umur tanaman sebanyak 2 turn atau menambah berat kartu hewan sebesar 8.
    // Delay: Mengurangi umur tanaman sebanyak 2 turn (umur tanaman minimal bernilai 0) atau mengurangi berat kartu hewan sebesar 5 (berat hewan minimal bernilai 0).
    // Instant harvest: Melakukan harvest secara langsung untuk kartu tanaman/hewan yang dipilih.
    // Destroy: Menghancurkan kartu tanaman/hewan lawan.
    // Protect: Melindungi kartu tanaman/hewan diri sendiri dari item yang ditambahkan oleh lawan ke ladang atau serangan beruang.
    // Trap: Mengubah beruang menjadi kartu hewan yang dapat diternak apabila menyerang hewan/tanaman yang diberikan item ini.
    // Bonus: Layout: bila dipakai ke diri sendiri, ladang berubah menjadi 5x6, kalau dipakai ke lawan, menjadi 3x4

    public abstract void applyEffect(Petak subject);
    public abstract void applyEffectBonus(boolean attacking, Ladang ladang);

    public static class Accelerate extends Effect {
        @Override
        public void applyEffect(Petak subject) {
            // Menambah umur tanaman sebanyak 2 turn atau menambah berat kartu hewan sebesar 8.
            if (subject.getGameObject() instanceof Plant) {
                Plant tanaman = (Plant) subject.getGameObject();
                tanaman.Tick(2);
            } else {
                Animal hewan = (Animal) subject.getGameObject();
                hewan.Feed(8);
            }
        }

        @Override
        public void applyEffectBonus(boolean attacking, Ladang ladang) {}
    }

    public static class Delay extends Effect {
        @Override
        public void applyEffect(Petak subject) {
            // Mengurangi umur tanaman sebanyak 2 turn (umur tanaman minimal bernilai 0) atau mengurangi berat kartu hewan sebesar 5 (berat hewan minimal bernilai 0).
            if (subject instanceof IGrowable) {
                Plant tanaman = (Plant) subject.getGameObject();
                if (tanaman.GetAge() >= 2) {
                    tanaman.Tick(-2);
                } else {
                    tanaman.Tick(tanaman.GetAge() * -1);
                }
            } else {
                Animal hewan = (Animal) subject.getGameObject();
                if (hewan.GetWeight() >= 5) {
                    hewan.Feed(-5);
                } else {
                    hewan.Feed(hewan.GetWeight() * -1);
                }
            }
        }

        @Override
        public void applyEffectBonus(boolean attacking, Ladang ladang) {}
    }

    public static class InstantHarvest extends Effect {
        @Override
        public void applyEffect(Petak subject) {
            // Melakukan harvest secara langsung untuk kartu tanaman/hewan yang dipilih.
            if (subject.getGameObject() instanceof Plant) {
                Plant tanaman = (Plant) subject.getGameObject();
                tanaman.Harvest();
            } else {
                Animal hewan = (Animal) subject.getGameObject();
                hewan.Harvest();
            }
        }

        @Override
        public void applyEffectBonus(boolean attacking, Ladang ladang) {}
    }

    public static class Destroy extends Effect {
        @Override
        public void applyEffect(Petak subject) {
            // jika subject mempunyai item protect, maka subject tidak akan dihancurkan
            for (Item item : subject.getItem()) {
                if (item.getEffect() instanceof Protect) {
                    subject.getItem().remove(item);
                    return;
                }
            }

            subject.setGameObject(null);
        }

        @Override
    public void applyEffectBonus(boolean attacking, Ladang ladang) {}
    }

    public static class Protect extends Effect {
        @Override
        public void applyEffect(Petak subject) {
            // Melindungi kartu tanaman/hewan diri sendiri dari item yang ditambahkan oleh lawan ke ladang atau serangan beruang.
        }

        @Override
        public void applyEffectBonus(boolean attacking, Ladang ladang) {}
    }

    public static class Trap extends Effect {
        @Override
        public void applyEffect(Petak subject) {}

        @Override
        public void applyEffectBonus(boolean attacking, Ladang ladang) {}
    }

    public static class Layout extends Effect {
        @Override
        public void applyEffectBonus(boolean attacking, Ladang ladang) {
            if (attacking) {
                ladang.bonusMusuh();
            } else {
                ladang.bonus();
            }
        }

        @Override
        public void applyEffect(Petak subject) {}
    }
}