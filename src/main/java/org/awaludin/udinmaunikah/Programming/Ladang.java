package org.awaludin.udinmaunikah.Programming;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;

public class Ladang {
    private List<Petak> grid;

    public Ladang() {
        grid = new ArrayList<Petak>(30);
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
        Card card = new Card(grid.get(x).getGameObject());
        return card;
    }

    public void harvest(GameObject gameObject, int x) {
        if (grid.get(x).isReadyToHarvest()) {
            grid.get(x).Harvest();
        }
    }

    public boolean isHerbivoreAda(){
        // mengecek apakah ada herbivore di ladang, jika ada, langsung return true
        try {
            for (int i = 0; i < grid.size(); i++) {
                Animal hewan = (Animal) grid.get(i).getGameObject();
                if (hewan.GetType() == AnimalType.HERBIVORE) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCarnivoreAda(){
        try {
            for (int i = 0; i < grid.size(); i++) {
                Animal hewan = (Animal) grid.get(i).getGameObject();
                if (hewan.GetType() == AnimalType.CARNIVORE) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    //Public card/GameObject/Product tryToHarvest(){};
}


    //LadangEntry bisa dipake untuk ngebantu