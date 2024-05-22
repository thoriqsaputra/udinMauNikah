package org.awaludin.udinmaunikah.Programming.Driver;

import org.awaludin.udinmaunikah.Programming.Animal;
import org.awaludin.udinmaunikah.Programming.Ladang;
import org.awaludin.udinmaunikah.Programming.Petak;
import org.awaludin.udinmaunikah.Programming.AnimalType;

public class LadangDriver {
    public static void main(String[] args) {
        // Membuat instance Ladang
        Ladang ladang = new Ladang();

        // Menambahkan beberapa Petak dengan objek Animal
        ladang.add(new Petak(new Animal("Herbivore1", 5, 10,  AnimalType.HERBIVORE)), 0, 0);
        ladang.add(new Petak(new Animal("Carnivore1", 8, 15,  AnimalType.CARNIVORE)), 1, 1);
        ladang.add(new Petak(new Animal("Herbivore2", 10, 10,  AnimalType.HERBIVORE)), 2, 2);
        ladang.add(new Petak(new Animal("Carnivore2", 20, 15, AnimalType.CARNIVORE)), 3, 3);

        // Menampilkan grid awal
        printGrid(ladang.getGrid());

        // Memperbesar grid menggunakan metode bonus
        ladang.bonus();
        System.out.println("Setelah memperbesar grid:");
        printGrid(ladang.getGrid());

        // Mengembalikan grid ke ukuran awal menggunakan metode bonusHabis
        ladang.bonusHabis(ladang);
        System.out.println("Setelah mengembalikan grid ke ukuran awal:");
        printGrid(ladang.getGrid());

        // Membuat grid musuh dan mengubah ukurannya menggunakan metode bonusMusuh
        Ladang ladangMusuh = new Ladang();
        ladangMusuh.add(new Petak(new Animal("Herbivore1", 5, 10,  AnimalType.HERBIVORE)), 0, 0);
        ladangMusuh.add(new Petak(new Animal("Carnivore1", 8, 15,  AnimalType.CARNIVORE)), 1, 1);
        ladangMusuh.add(new Petak(new Animal("Herbivore2", 10, 10,  AnimalType.HERBIVORE)), 2, 2);
        ladangMusuh.add(new Petak(new Animal("Carnivore2", 20, 15, AnimalType.CARNIVORE)), 3, 3);
        System.out.println("Grid musuh sebelum diubah:");
        printGrid(ladangMusuh.getGrid());

        Petak[][] gridMusuhBaru = ladang.bonusMusuh(ladangMusuh);
        System.out.println("Grid musuh setelah diubah:");
        printGrid(gridMusuhBaru);

        // Memeriksa apakah ada Herbivore atau Carnivore
        System.out.println("Apakah ada Herbivore? " + ladang.isHerbivoreAda());
        System.out.println("Apakah ada Carnivore? " + ladang.isCarnivoreAda());

        // Menguji metode harvest
        ladang.harvest(new Animal("Herbivore2", 10, 10, AnimalType.HERBIVORE), 2, 2);
        System.out.println("Setelah harvest:");
        printGrid(ladang.getGrid());
    }

    // Metode untuk mencetak grid ke konsol
    public static void printGrid(Petak[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null && grid[i][j].getGameObject() instanceof Animal) {
                    Animal animal = (Animal) grid[i][j].getGameObject();
                    System.out.print(animal.GetName());
                } else {
                    System.out.print("... ");
                }
            }
            System.out.println();
        }
    }
}