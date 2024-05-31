package org.awaludin.udinmaunikah.Programming;

import java.util.HashMap;
import java.util.Map;

public class Toko {
    private static Map<GameObject, Integer> listItemToko;

    public static void createToko(){
        listItemToko = new HashMap<>();
    }

    private static GameObject seek_key(GameObject reference){
        for (var listing : listItemToko.keySet()){
            if (listing.getId().equals(reference.getId())){
                return listing;
            }
        }
        return null;
    }

    public static boolean isItemAvailable(GameObject item) {
        if (seek_key(item) != null) {
            int quantity = listItemToko.get(item);
            return quantity > 0;
        }
        return false;
    }

    public static void removeItems(GameObject item) {
        if (seek_key(item) != null) {
            GameObject key = seek_key(item);
            int quantity = listItemToko.get(key);
            if (quantity > 1) {
                listItemToko.replace(key, quantity - 1);
            } else {
                listItemToko.remove(key);
            }
        }
    }

    public static void addItem(GameObject item) {
        if (seek_key(item) != null) {
            GameObject key = seek_key(item);
            int quantity = listItemToko.get(key);
            listItemToko.replace(key, quantity + 1);
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

    public static Map<GameObject, Integer> getListItems() {
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