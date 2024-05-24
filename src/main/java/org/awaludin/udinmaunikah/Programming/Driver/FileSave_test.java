package org.awaludin.udinmaunikah.Programming.Driver;

import org.awaludin.udinmaunikah.Programming.GameManager;
import org.awaludin.udinmaunikah.Programming.Loader;
import org.awaludin.udinmaunikah.Programming.TXTLoader;

public class FileSave_test {
    public static void main(String[] args) {
        GameManager.initGameManager();
        GameManager gm = GameManager.getInstance();

        Loader save = new TXTLoader();

        save.save("src\\main\\resources\\org\\awaludin\\udinmaunikah");
    }
}
