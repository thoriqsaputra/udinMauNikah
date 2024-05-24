package org.awaludin.udinmaunikah.Programming;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;

public class TXTLoader implements Loader {
    private Field temp;

    private String exposeFieldValue(Field[] flist, String field){
        String retval = null;
        for (final Field f : flist) {
            try{
                if (f.getName().equals(field)){
                    boolean reset_on_done = false;
                    try {
                        f.canAccess(this);
                    } catch (IllegalArgumentException e) {
                        reset_on_done = true;
                        f.setAccessible(true);
                    };
                    retval = f.get(this).toString();

                    if (reset_on_done){
                        f.setAccessible(false);
                    }
                    return retval;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retval;
    }

    private List<CardDeck> expose_decklist(){
        GameManager gm = new GameManager();
        Field[] declaredFields = gm.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            try {
                if (f.getName().equals("deckList")) {
                    f.setAccessible(true);
                    temp = f;
                    return (List<CardDeck>) f.get(this);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Ladang> expose_ladanglist(){
        GameManager gm = new GameManager();
        Field[] declaredFields = gm.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            try {
                if (f.getName().equals("ladangList") ) {
                    f.setAccessible(true);
                    temp = f;
                    return (List<Ladang>) f.get(this);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public boolean load(String path) {
        Path fpath = Paths.get(path);
        if (Files.isDirectory(fpath)) {

        }
        return false;
    }

    @Override
    public boolean save(String path) {
        GameManager gm = new GameManager();
        Path fpath = Paths.get(path);
        Field[] flist = gm.getClass().getDeclaredFields();
        if (Files.isDirectory(fpath)) {
            try{
            List<String> content = new ArrayList<String>();
            //gamestate.txt
            content.add(exposeFieldValue(flist, "turnCounter"));
            Files.write(Path.of(path + "\\gamestate.txt"),content, StandardCharsets.UTF_8);

            //player.txt
            } catch (Exception e){
                    e.printStackTrace();
                }
        }
        return false;
    }
}
