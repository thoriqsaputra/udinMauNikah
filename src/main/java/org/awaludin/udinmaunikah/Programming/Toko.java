package org.awaludin.udinmaunikah.Programming;

import java.util.HashMap;
import java.util.Map;

public class Toko {
    private Map<GameObject, Integer> listItemToko;

    public Toko(){
        listItemToko = new HashMap<>();
    }

    public boolean isItemAvailable(GameObject item) {
        if (listItemToko.containsKey(item)) {
            int quantity = listItemToko.get(item);
            return quantity > 0;
        }
        return false;
    }

    public void removeItems(GameObject item) {
        if (listItemToko.containsKey(item)) {
            int quantity = listItemToko.get(item);
            if (quantity > 1) {
                listItemToko.put(item, quantity - 1);
            } else {
                listItemToko.remove(item);
            }
        }
    }

    public void setListItems(Map<GameObject, Integer> setlistItemToko) {
        listItemToko = setlistItemToko;
    }

    public void tambahListItems(Map<GameObject, Integer> listItemToko) {
        for (Map.Entry<GameObject, Integer> entry : listItemToko.entrySet()) {
            GameObject item = entry.getKey();
            int quantity = entry.getValue();
            
            if (this.listItemToko.containsKey(item)) {
                int currentQuantity = this.listItemToko.get(item);
                this.listItemToko.put(item, currentQuantity + quantity);
            } else {
                this.listItemToko.put(item, quantity);
            }
        }
    }
    
    public Map<GameObject, Integer> getListItems() {
        return listItemToko;
    }
}
