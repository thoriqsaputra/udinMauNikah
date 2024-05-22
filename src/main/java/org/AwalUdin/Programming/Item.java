package org.AwalUdin.Programming;

public class Item extends GameObject {
    private String name;
    private int price;
    private Effect effect;

    public Item(String name, int price, Effect effect) {
        this.name = name;
        this.price = price;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Effect getEffect() {
        return effect;
    }

    public void use(GameObject subject) {
        effect.applyEffect(subject);
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

    public abstract void applyEffect(GameObject subject);

    public static class Accelerate extends Effect {
        @Override
        public void applyEffect(GameObject subject) {
            // Menambah umur tanaman sebanyak 2 turn atau menambah berat kartu hewan sebesar 8.
            if (subject instanceof IGrowable) { // Call the getPetak() method on the instance
                Plant tanaman = (Plant) subject; // Cast the GameObject to a Plant
                tanaman.Tick(2);
            } else {
                Animal hewan = (Animal) subject; // Cast the GameObject to a Hewan
                hewan.Feed(8);
            }
        }
    }

    public static class Delay extends Effect {
        @Override
        public void applyEffect(GameObject subject) {
            // Mengurangi umur tanaman sebanyak 2 turn (umur tanaman minimal bernilai 0) atau mengurangi berat kartu hewan sebesar 5 (berat hewan minimal bernilai 0).
            if (subject instanceof IGrowable) {
                Plant tanaman = (Plant) subject;
                if (tanaman.GetAge() >= 2) {
                    tanaman.Tick(-2);
                } else if (tanaman.GetAge() == 1) {
                    tanaman.Tick(-1);
                } else {
                    tanaman.Tick(0);
                }
            } else {
                Animal hewan = (Animal) subject;
                if (hewan.GetWeight() >= 5) {
                    hewan.Feed(-5);
                } else {
                    hewan.Feed(hewan.GetWeight() * -1);
                }
            }
        }
    }

    public static class InstantHarvest extends Effect {
        @Override
        public void applyEffect(GameObject subject) {
            // Melakukan harvest secara langsung untuk kartu tanaman/hewan yang dipilih.
            if (subject instanceof IHarvestable) {
                // masukkin fungsi harvest disini
            } else {
                System.out.println("Kartu yang dipilih tidak bisa di-harvest");
            }
        }
    }

    public static class Destroy extends Effect {
        @Override
        public void applyEffect(GameObject subject) {
            // Menghancurkan kartu tanaman/hewan lawan
            subject = null;
        }
    }

    public static class Protect extends Effect {
        @Override
        public void applyEffect(GameObject subject) {
            // Melindungi kartu tanaman/hewan diri sendiri dari item yang ditambahkan oleh lawan ke ladang atau serangan beruang.) {
            // subject akan di protect dan tidak bisa dihancurkan beruang
        }
    }

    public static class Trap extends Effect {
        @Override
        public void applyEffect(GameObject subject) {
            // Mengubah beruang menjadi kartu hewan yang dapat diternak apabila menyerang hewan/tanaman yang diberikan item ini.
            
        }
    }
}

abstract class EffectBonus {
    public abstract void applyEffectBonus(Ladang ladang);

    public static class Layout extends EffectBonus {
        @Override
        public void applyEffectBonus(Ladang ladang) {
            // bila dipakai ke diri sendiri, ladang berubah menjadi 5x6, kalau dipakai ke lawan, menjadi 3x4
        }
    }
}