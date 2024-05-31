package org.awaludin.udinmaunikah.Programming;

import java.util.*;
import java.util.Map.Entry;

import org.awaludin.udinmaunikah.CardBrain;
import org.awaludin.udinmaunikah.GameController;
import org.awaludin.udinmaunikah.Programming.Effect;

abstract public class Effect {
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
            Product pr;
            if (subject.getGameObject() instanceof Plant) {
                Plant tanaman = (Plant) subject.getGameObject();
                pr = (Product) tanaman.Harvest();
            } else {
                Animal hewan = (Animal) subject.getGameObject();
                pr = (Product) hewan.Harvest();
            }
            Card prd = new Card(pr);
            if (!GameManager.PlayerInterface.tryAddToHand(prd)){
                CardBrain.botNot("Deck Slot FULL!");
            }
            List<Card> k = new ArrayList<>();
            k.add(prd);
            CardBrain.cardObj tempc = subject.getCardObj();
            Petak p = tempc.getPreviousPetak();
            if (p!=null){
                p.setNull();
            }
            GameController.mainPane.getChildren().remove(tempc);
            GameController.gameC.isiDeck(k);

;        }

        @Override
        public void applyEffectBonus(boolean attacking, Ladang ladang) {}
    }

    public static class Destroy extends Effect {
        @Override
        public void applyEffect(Petak subject) {
            // jika subject mempunyai item protect, maka subject tidak akan dihancurkan
            for (Entry<Item, Integer> item : subject.getItem().entrySet()) {
                if (item.getKey().getEffect() instanceof Protect) {
                    subject.getItem().remove(item.getKey(), item.getValue());
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
