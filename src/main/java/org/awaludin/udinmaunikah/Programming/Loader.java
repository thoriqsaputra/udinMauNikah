package org.awaludin.udinmaunikah.Programming;

public interface Loader {
    public boolean load(String path, GameManager gm);
    public boolean save(String path, GameManager gm);
}
