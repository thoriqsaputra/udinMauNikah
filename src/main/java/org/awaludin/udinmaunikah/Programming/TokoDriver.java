package org.awaludin.udinmaunikah.Programming;

import java.util.HashMap;
import java.util.Map;

public class TokoDriver {
    public static void main(String[] args) {
        // Create some GameObject instances to be used as items in the store
        GameObject apple = new GameObject("Apple", "fruit");
        GameObject beef = new GameObject("Beef", "meat");
        
        // Create a Toko instance
        Toko toko = new Toko();
        
        // Add items to the store
        Map<GameObject, Integer> initialItems = new HashMap<>();
        initialItems.put(apple, 10);
        initialItems.put(beef, 5);
        toko.setListItems(initialItems);

        // Display the list of items in the store
        System.out.println("Initial items in the store:");
        printItems(toko.getListItems());

        // Check if an item is available
        System.out.println("Is Apple available? " + toko.isItemAvailable(apple)); // Should be true
        System.out.println("Is Beef available? " + toko.isItemAvailable(beef)); // Should be true

        // Remove an item from the store
        toko.removeItems(apple);
        System.out.println("Items in the store after removing one Apple:");
        printItems(toko.getListItems());

        // Add more items to the store
        Map<GameObject, Integer> additionalItems = new HashMap<>();
        additionalItems.put(apple, 5);
        additionalItems.put(beef, 3);
        toko.tambahListItems(additionalItems);
        
        // Display the list of items in the store after adding more items
        System.out.println("Items in the store after adding more items:");
        printItems(toko.getListItems());
    }
    
    private static void printItems(Map<GameObject, Integer> items) {
        for (Map.Entry<GameObject, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey().getName() + " (" + entry.getKey().getCategory() + "): " + entry.getValue());
        }
        System.out.println();
    }
}

