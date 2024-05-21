package org.AwalUdin.Programming;

import java.util.Map;
import java.util.HashMap;

public class Ladang {
    private Petak[][] grid;

    public Ladang() {
        grid = new Petak[4][5];
    }

    public bonus() {
        temp = new Petak[5][6];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                temp[i][j] = grid[i][j];
            }
        }
        grid = temp;
    }

    public bonusHabis(Ladang ladang) {
        // membuat ladang kembali menjadi 4x5, dan menghapus petak yang ada di kolom 6 dan row 5
        temp = new Petak[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                temp[i][j] = grid[i][j];
            }
        }
        grid = temp;
    }

    public Petak[][] bonusMusuh(Ladang ladangMusuh) {
        // membuat ladang musuh menjadi 3x4, dan menghapus petak lawan yang ada di kolom 5 dan row 4
        temp = new Petak[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = ladangMusuh.getGrid()[i][j];
            }
        }
        return temp;
    }

    public void add(Petak Petak, int x, int y) {
        grid[x][y] = Petak;
    }

    public Petak[][] getGrid() {
        return grid;
    }

    public Petak getPetak(int x, int y) {
        return grid[x][y];
    }

    public Card convertToCard(int x, int y) {
        Card card = new Card();
        card.setGameObject(grid[x][y]);
        return card;
    }

    public void harvest(GameObject gameObject, int x, int y) {
        if (grid[x][y] instanceof IHarvestable) {
            if (((IHarvestable) grid[x][y]).isReadyToHarvest()) {
                grid[x][y] = ((IHarvestable) grid[x][y]).Harvest();
            }
        }
    }

    public bool isHerbivoreAda(){
        for (Map.Entry<GameObject, Integer> entry : grid.entrySet()) {
            if(entry.getKey() instanceof Herbivore){
                return true;
            }
        }
        return false;
    }

    public bool isCarnivoreAda(){
        for (Map.Entry<GameObject, Integer> entry : grid.entrySet()) {
            if(entry.getKey() instanceof Carnivore){
                return true;
            }
        }
        return false;
    }

    //Public card/GameObject/Product tryToHarvest(){};
}


//LadangEntry bisa dipake untuk ngebantu