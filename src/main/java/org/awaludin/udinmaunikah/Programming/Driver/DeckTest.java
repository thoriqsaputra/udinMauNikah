package org.awaludin.udinmaunikah.Programming.Driver;

import org.awaludin.udinmaunikah.Programming.GameManager;
import org.awaludin.udinmaunikah.Programming.GameObjectFactory;

public class DeckTest {
    public static void main(String[] args) {
        GameManager.initGameManager();
        GameObjectFactory.Load();
        for (int i = 0; i < 2; i++) {
            GameManager.SetUpUtils.useDeck(i,"default");
        }
    }
}
