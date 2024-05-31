package org.awaludin.udinmaunikah.Programming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Toko {
    private static Map<GameObject, Integer> listItemToko;

    public static void createToko(){
        listItemToko = new HashMap<>();
    }

    public static boolean isItemAvailable(GameObject item) {
        if (listItemToko.containsKey(item)) {
            int quantity = listItemToko.get(item);
            return quantity > 0;
        }
        return false;
    }

    public static void removeItems(GameObject item) {
        if (listItemToko.containsKey(item)) {
            int quantity = listItemToko.get(item);
            if (quantity > 1) {
                listItemToko.put(item, quantity - 1);
            } else {
                listItemToko.remove(item);
            }
        }
    }

    public static void addItem(GameObject item) {
        if (listItemToko.containsKey(item)) {
            int quantity = listItemToko.get(item);
            listItemToko.put(item, quantity + 1);
        } else {
            listItemToko.put(item, 1);
        }
    }

    public static void setListItems(Map<GameObject, Integer> setlistItemToko) {
        listItemToko = setlistItemToko;
    }

    public static void tambahListItems(Map<GameObject, Integer> listItemToko) {
        for (Map.Entry<GameObject, Integer> entry : listItemToko.entrySet()) {
            GameObject item = entry.getKey();
            int quantity = entry.getValue();
            
            if (listItemToko.containsKey(item)) {
                int currentQuantity = listItemToko.get(item);
                listItemToko.put(item, currentQuantity + quantity);
            } else {
                listItemToko.put(item, quantity);
            }
        }
    }
    
    public static List<Item> getListItems() {
        return listItemToko;
    }

    public static int countItems() {
        int count = 0;
        for (int quantity : listItemToko.values()) {
            count += quantity;
        }
        return count;
    }
}
