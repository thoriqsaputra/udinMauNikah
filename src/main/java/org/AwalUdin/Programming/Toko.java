import org.AwalUdin.Programming.GameObject;
import java.util.HashMap;
import java.util.Map;

package org.AwalUdin.Programming;



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
            if (listItemToko.containsKey(entry.getKey())) {
                listItemToko.put(entry.getKey(), listItemToko.get(entry.getKey()) + entry.getValue());
            } else {
                listItemToko.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public Map<GameObject, Integer> getListItems() {
        return listItemToko;
    }
}
