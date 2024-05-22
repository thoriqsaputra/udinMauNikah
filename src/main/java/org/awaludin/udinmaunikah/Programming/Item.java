//package org.awaludin.udinmaunikah.Programming;
//
//public class Item extends GameObject {
//    private String name;
//    private int price;
//    private Effect effect;
//
//    public Item(String name, int price, Effect effect) {
//        this.name = name;
//        this.price = price;
//        this.effect = effect;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public Effect getEffect() {
//        return effect;
//    }
//
//    public void use(int x, int y) {
//        effect.applyEffect(x, y);
//    }
//}
//
//
//abstract class Effect {
//    // ada 5 jenis effect dalam game ini:
//    // Accelerate: Menambah umur tanaman sebanyak 2 turn atau menambah berat kartu hewan sebesar 8.
//    // Delay: Mengurangi umur tanaman sebanyak 2 turn (umur tanaman minimal bernilai 0) atau mengurangi berat kartu hewan sebesar 5 (berat hewan minimal bernilai 0).
//    // Instant harvest: Melakukan harvest secara langsung untuk kartu tanaman/hewan yang dipilih.
//    // Destroy: Menghancurkan kartu tanaman/hewan lawan.
//    // Protect: Melindungi kartu tanaman/hewan diri sendiri dari item yang ditambahkan oleh lawan ke ladang atau serangan beruang.
//    // Trap: Mengubah beruang menjadi kartu hewan yang dapat diternak apabila menyerang hewan/tanaman yang diberikan item ini.
//    // Bonus: Layout: bila dipakai ke diri sendiri, ladang berubah menjadi 5x6, kalau dipakai ke lawan, menjadi 3x4
//
//    public abstract void applyEffect(int x, int y);
//
//    public static class Accelerate extends Effect {
//        @Override
//        public void applyEffect(int x, int y) {
//            // Menambah umur tanaman sebanyak 2 turn atau menambah berat kartu hewan sebesar 8.
//            if (tanaman) {
//                tanaman.umur += 2;
//            } else {
//                hewan.berat += 8;
//            }
//        }
//    }
//
//    public static class Delay extends Effect {
//        @Override
//        public void applyEffect(int x, int y) {
//            // Mengurangi umur tanaman sebanyak 2 turn (umur tanaman minimal bernilai 0) atau mengurangi berat kartu hewan sebesar 5 (berat hewan minimal bernilai 0).
//            if (tanaman) {
//                tanaman.umur -= 2;
//                if (tanaman.umur < 0) {
//                    tanaman.umur = 0;
//                }
//            } else {
//                hewan.berat -= 5;
//                if (hewan.berat < 0) {
//                    hewan.berat = 0;
//                }
//            }
//        }
//    }
//
//    public static class InstantHarvest extends Effect {
//        @Override
//        public void applyEffect(int x, int y) {
//            // Melakukan harvest secara langsung untuk kartu tanaman/hewan yang dipilih.
//            if (tanaman) {
//                tanaman.harvest();
//            } else {
//                hewan.harvest();
//            }
//        }
//    }
//
//    public static class Destroy extends Effect {
//        @Override
//        public void applyEffect(int x, int y) {
//            // Menghancurkan kartu tanaman/hewan lawan
//            lawan.getGrid()[x][y] = null;
//        }
//    }
//
//    public static class Protect extends Effect {
//        @Override
//        public void applyEffect(int x, int y) {
//            // Melindungi kartu tanaman/hewan diri sendiri dari item yang ditambahkan oleh lawan ke ladang atau serangan beruang.
//            if (diri sendiri) {
//                diri.sendiri.protect();
//            }
//        }
//    }
//
//    public static class Trap extends Effect {
//        @Override
//        public void applyEffect(int x, int y) {
//            // Mengubah beruang menjadi kartu hewan yang dapat diternak apabila menyerang hewan/tanaman yang diberikan item ini.
//            if (beruang) {
//                beruang.ubahMenjadiHewan();
//            }
//        }
//    }
//
//    public static class Layout extends Effect {
//        @Override
//        public void applyEffect() {
//            // Layout: bila dipakai ke diri sendiri, ladang berubah menjadi 5x6, kalau dipakai ke lawan, menjadi 3x4
//            if (ke diri sendiri) {
//                ladang.bonus();
//            } else {
//                ladang.bonusMusuh();
//            }
//        }
//    }
//}
