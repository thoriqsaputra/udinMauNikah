package org.awaludin.udinmaunikah.Programming;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;
import java.util.Map;

public class TXTLoader implements Loader {
    private Field temp;

    private Object exposeFieldValue(Field[] flist, String field){
        Object retval = null;
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
                    retval = f.get(this);

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
                content.add(exposeFieldValue(flist, "totalTurnCounter").toString());
                //toko items
                Toko shop = (Toko) exposeFieldValue(flist, "shop");
                content.add("SHOP ITEM AMMOUNT");


                Files.write(Path.of(path + "\\gamestate.txt"),content, StandardCharsets.UTF_8);

            //player.txt
                List<CardDeck> cdlist = expose_decklist();//(List<CardDeck>) exposeFieldValue(flist,"deckList");
                List<Ladang> ldlist = expose_ladanglist();
                for (int i = 0; i < GameManager.defaultPlayerCount; i++){
                    content.clear();
                    String filename = String.format("player%d.txt",i+1);
                    content.add((((List<Integer>)exposeFieldValue(flist,"guldenList")).get(i)).toString());
                    //player deck
                    CardDeck cd = cdlist.get(i);
                    content.add(String.valueOf(cd.getCardCount()));
                    for (CardSlot cs : cd.getDeck()){
                        for (int j = 0; j < cs.count(); j++){
                            content.add(cs.GetCardThing().convertToGameObject().getId());
                        }
                    }
                    //deck active
                    int handcount = 0;
                    List<String> temp_idholder = new ArrayList<>();
                    for (int j = 0; j < GameManager.maxHandCount; j++){
                        Card c = cd.getHand().get(j);
                        if (c != null){
                            handcount++;
                            temp_idholder.add(c.convertToGameObject().getId());
                        }
                    };
                    content.add(String.valueOf(handcount));
                    content.addAll(temp_idholder);

                    //ladang
                    Ladang ld = ldlist.get(i);
                    Map<Integer, GameObject> ld_info = ld.getIngfo();
                    //total
                    content.add(String.valueOf(ld_info.size()));

                    Files.write(Path.of(path + "\\" + filename),content, StandardCharsets.UTF_8);

                }

            } catch (Exception e){
                    e.printStackTrace();
                }
        }
        return false;
    }
}
