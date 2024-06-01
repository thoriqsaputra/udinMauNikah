package org.awaludin.udinmaunikah.Programming;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.nio.file.*;
import java.util.Map;
import java.io.FileReader;

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

    private void fieldSetter(Object master, String fname, Object value){
        try{
            boolean reset_on_done = false;
            Field f = master.getClass().getDeclaredField(fname);
            if (!f.canAccess(this)){
                reset_on_done = true;
                f.setAccessible(true);
            }

            f.set(master, value);

            if (reset_on_done){
                f.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private void fieldSetter(Field[] flist, String field, Object value );

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
        GameManager gm = new GameManager();
        Path fpath = Paths.get(path);
        if (Files.isDirectory(fpath)) {
            try {
                Path gstate = fpath.resolve("gamestate.txt");
                Path p1 = fpath.resolve("player1.txt");
                Path p2 = fpath.resolve("player2.txt");
                Path[] files = {gstate, p1, p2};
                for (Path p : files){
                    if (!Files.isRegularFile(p)) {
                        throw new FileNotFoundException("File not found: " + p.toString());
                    }
                }

                //BEGIM

                GameManager.initGameManager();
                GameObjectFactory.Load();

                String line = null;
                //gamestate.txt reader
                BufferedReader gom_state = new BufferedReader(new FileReader(gstate.toString()));
                line = gom_state.readLine();
                int turns = Integer.parseInt(line);
                fieldSetter(gm, "totalTurnCounter", turns);
                fieldSetter(gm, "turnCounter", turns % 2);
                int shopItemCount = Integer.parseInt(gom_state.readLine());
                List<GameObject> go = new ArrayList<>();
                for (int i = 0; i < shopItemCount; i++) {
                    String shopline = gom_state.readLine();
                    GameObject item = GameObjectFactory.CreateGameObjectByID(shopline);
                    if (item != null) {
                        go.add(item);
                    } else {
                        throw new IllegalArgumentException(String.format("Gameobject ID not found! (\"%s\")",shopline));
                    }
                }

                //players
                Field gold_field = gm.getClass().getDeclaredField("guldenList");
                Field ladang_field = gm.getClass().getDeclaredField("ladangList");
                Field deck_field = gm.getClass().getDeclaredField("deckList");

                Field[] tempfields = {gold_field,ladang_field,deck_field};
                for (Field f : tempfields) {
                    f.setAccessible(true);
                }

                List<Integer> temp_gold_list = new ArrayList<>();
                List<Map<Integer,Petak>> temp_ladang_list = new ArrayList<>();
                List<CardDeck> temp_deck_list = new ArrayList<>();
                for (int i = 0; i < GameManager.defaultPlayerCount; i++) {
                    BufferedReader playerstates = new BufferedReader(new FileReader(files[1+i].toString()));
                    //GULDEN, JMLAH DECK, JMLAH DECK ACTIVE, LIST OF ACTIVE DECK,  JUMLAH KARTU LADANG, LIST OF KARTU LADANG
                    temp_gold_list.add(Integer.parseInt(playerstates.readLine()));
                    CardDeck tempCardDeck = new CardDeck();
                    tempCardDeck.load_deck("default");
                    tempCardDeck.reduceUntil(Integer.parseInt(playerstates.readLine()));
                    temp_deck_list.add(tempCardDeck);

                    //Set hand card
                    int card_count = Integer.parseInt(playerstates.readLine());
                    for (int j = 0; j < card_count; j++) {
                        String local_line = playerstates.readLine();
                        String[] thing = local_line.split(" ");
                        if (thing.length != 3){
                            throw new IllegalArgumentException(String.format("Incorrect format for handcard state! (%s)",local_line));
                        }
                        Card c = new Card(GameObjectFactory.CreateGameObjectByID(thing[1]));
                        temp_deck_list.get(i).getHand().set(Integer.parseInt(thing[0]),c);
                        //might cause null/ordering issues
                    }

                    //ladang
                    int ladang_count = Integer.parseInt(playerstates.readLine());
                    for (int j = 0; i < ladang_count; j++){
                        String temp_line = playerstates.readLine();
                        String[] thing = temp_line.split(" ");
                        Petak temp_petak = new Petak(null);
                        //LOKASI //KARTU //ANGKA_SIGNIFIKAN //JUMLAH ITEM AKTIF //ITEMS
                        int location = Integer.parseInt(thing[0]);
                        GameObject object = GameObjectFactory.CreateGameObjectByID(thing[1]);
                        if (object != null){
                            object.setSignificantNumber(Integer.parseInt(thing[2]));
                            temp_petak.setGameObject(object);
                            int itemcount = Integer.parseInt(thing[3]);
                            Map<Item, Integer> item_list = new HashMap<Item, Integer>();
                            for (int k = 0; k < itemcount; k++){
                                Item itemToAdd = (Item) GameObjectFactory.CreateGameObjectByID(thing[4+i]);
                                if (itemToAdd != null){
                                    boolean inserted = false;
                                    for (Map.Entry<Item, Integer> keySeekTemp : item_list.entrySet()){
                                        if (keySeekTemp.getKey().getId().equals(itemToAdd.getId())){
                                            item_list.replace(keySeekTemp.getKey(),keySeekTemp.getValue()+1);
                                            inserted = true;
                                        }
                                    }
                                    if (!inserted){
                                        item_list.put(itemToAdd,1);
                                    }
                                }
                            }
                            fieldSetter(temp_petak,"item",item_list);
                        }
                        else{
                            throw new IllegalArgumentException(String.format("Incorrect format for petak (\"%s\")",temp_line));
                        }
                    }
                    playerstates.close();
                }

                //START OVERRIDING GAMEMANAGER

                //ini adder, bisa diseret kebawah
                Toko.createToko();
                for (GameObject item : go){
                    Toko.addItem(item);
                }
                gom_state.close();

                //ini buat override nanti kalo udah bener
                List<Integer> gold_list = (ArrayList<Integer>) gold_field.get(gm);
                List<Ladang> ladang_list = (ArrayList<Ladang>) ladang_field.get(gm);
                List<CardDeck> deck_list = (ArrayList<CardDeck>) deck_field.get(gm);

                for (Field f: tempfields){
                    f.setAccessible(false);
                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e){
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean save(String path) {
        GameManager gm = new GameManager();
        Path fpath = Paths.get(path);
        Field[] flist = gm.getClass().getDeclaredFields();
        if (Files.isDirectory(fpath)) {
            try {
                List<String> content = new ArrayList<>();

                // Save game state to gamestate.txt
                content.add(exposeFieldValue(flist, "totalTurnCounter").toString());

                Toko shop = (Toko) exposeFieldValue(flist, "shop");
                Map<GameObject, Integer> shopItems =  shop.getListItems();
                content.add(String.valueOf(shopItems.size()));
                for (Map.Entry<GameObject, Integer> listThing : shopItems.entrySet()) {
                    int amount = listThing.getValue();
                    for (int k=0; k<amount; k++) {
                        content.add(listThing.getKey().getId());
                    }
                }

                Files.write(Path.of(path + "\\gamestate.txt"), content, StandardCharsets.UTF_8);

                // Save player data
                List<CardDeck> cdlist = expose_decklist();
                List<Ladang> ldlist = expose_ladanglist();
                for (int i = 0; i < GameManager.defaultPlayerCount; i++) {
                    content.clear();
                    String filename = String.format("player%d.txt", i + 1);

                    content.add((((List<Integer>) exposeFieldValue(flist, "guldenList")).get(i)).toString());

                    // Player deck
//                    CardDeck cd = cdlist.get(i);
//                    content.add(String.valueOf(cd.getCardCount()));
//                    for (CardSlot cs : cd.getDeck()) {
//                        for (int j = 0; j < cs.count(); j++) {
//                            content.add(cs.GetCardThing().convertToGameObject().getId());
//                        }
//                    }

                    // Deck active
                    int handcount = 0;
                    List<String> temp_idholder = new ArrayList<>();
                    for (int j = 0; j < GameManager.maxHandCount; j++) {
                        Card c = cdlist.get(i).getHand().get(j);
                        if (c != null) {
                            handcount++;
                            temp_idholder.add(String.format("%d %s", j, c.convertToGameObject().getId()));

                        }
                    }
                    content.add(String.valueOf(handcount));
                    content.addAll(temp_idholder);

                    // Ladang
                    Ladang ld = ldlist.get(i);
                    Map<Integer, GameObject> ld_info = ld.getIngfo();
                    content.add(String.valueOf(ld_info.size()));
                    for (int j = 0; j < 30; j++) {
                        if (ld.getGrid().get(j).getGameObject() != null) {
                            Petak temp = ld.getGrid().get(j);
                            StringBuilder sb = new StringBuilder();
                            sb.append(String.format("%d ", j));
                            sb.append(String.format("%s ", temp.getGameObject().getId()));
                            if(temp.getGameObject().getClass().equals(Plant.class)){
                                Plant plant = (Plant) temp.getGameObject();
                                sb.append(String.format("%d ", plant.GetAge()));
                            }else if(temp.getGameObject().getClass().equals(Animal.class)){
                                Animal animal = (Animal) temp.getGameObject();
                                sb.append(String.format("%d ", animal.GetWeight()));
                            }
                            sb.append(String.format("%d ", temp.getCount()));

                            for(var x : temp.getItem().entrySet()){
                                for(int k=0; k<x.getValue(); k++){
                                    sb.append(String.format("%s ", x.getKey().getId()));
                                }
                            }

                            content.add(sb.toString());
                        }
                    }

                    Files.write(Path.of(path + "\\" + filename), content, StandardCharsets.UTF_8);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }
}
