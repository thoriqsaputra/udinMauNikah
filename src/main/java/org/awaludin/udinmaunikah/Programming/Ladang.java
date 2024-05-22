//package org.awaludin.udinmaunikah.Programming;
//
//public class Ladang {
//    private Petak[][] grid;
//    private Item bonus;
//
//    public Ladang() {
//        grid = new Petak[4][5];
//    }
//
//    public void bonus() {
//        Petak[][] temp = new Petak[5][6];
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 5; j++) {
//                temp[i][j] = grid[i][j];
//            }
//        }
//        grid = temp;
//    }
//
//    public void bonusHabis(Ladang ladang) {
//        // membuat ladang kembali menjadi 4x5, dan menghapus petak yang ada di kolom 6 dan row 5
//        Petak[][] temp = new Petak[4][5];
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 5; j++) {
//                temp[i][j] = grid[i][j];
//            }
//        }
//        grid = temp;
//    }
//
//    public Petak[][] bonusMusuh(Ladang ladangMusuh) {
//        // membuat ladang musuh menjadi 3x4, dan menghapus petak lawan yang ada di kolom 5 dan row 4
//        Petak[][] temp = new Petak[3][4];
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 4; j++) {
//                temp[i][j] = ladangMusuh.getGrid()[i][j];
//            }
//        }
//        return temp;
//    }
//
//    public void add(Petak Petak, int x, int y) {
//        grid[x][y] = Petak;
//    }
//
//    public Petak[][] getGrid() {
//        return grid;
//    }
//
//    public Petak getPetak(int x, int y) {
//        return grid[x][y];
//    }
//
//    public Card convertToCard(int x, int y) {
//        Card card = new Card(grid[x][y].getGameObject());
//        return card;
//    }
//
//    public void harvest(GameObject gameObject, int x, int y) {
//        if (grid[x][y] instanceof IHarvestable) {
//            if (((IHarvestable) grid[x][y]).isReadyToHarvest()) {
//                //masukkin buat harvest disini
//                System.out.println("sudah di harvest");
//                grid[x][y] = null;
//            }
//        }
//    }
//
//    public boolean isHerbivoreAda(){
//        // mengecek apakah ada herbivore di ladang, jika ada, langsung return true
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                Animal hewan = (Animal) grid[i][j].getGameObject();
//                if (hewan.GetType() == AnimalType.HERBIVORE) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    public boolean isCarnivoreAda(){
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                Animal hewan = (Animal) grid[i][j].getGameObject();
//                if (hewan.GetType() == AnimalType.CARNIVORE) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    //Public card/GameObject/Product tryToHarvest(){};
//}
//
//
////LadangEntry bisa dipake untuk ngebantu