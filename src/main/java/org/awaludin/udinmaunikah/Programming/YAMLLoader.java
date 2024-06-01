package org.awaludin.udinmaunikah.Programming;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class YAMLLoader implements Loader {
    private Field temp;

    private Object exposeFieldValue(Field[] flist, String field) {
        Object retval = null;
        for (final Field f : flist) {
            try {
                if (f.getName().equals(field)) {
                    boolean reset_on_done = false;
                    try {
                        f.canAccess(this);
                    } catch (IllegalArgumentException e) {
                        reset_on_done = true;
                        f.setAccessible(true);
                    }
                    retval = f.get(this);

                    if (reset_on_done) {
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

    private void fieldSetter(Object master, String fname, Object value) {
        try {
            boolean reset_on_done = false;
            Field f = master.getClass().getDeclaredField(fname);
            if (!f.canAccess(this)) {
                reset_on_done = true;
                f.setAccessible(true);
            }

            f.set(master, value);

            if (reset_on_done) {
                f.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<CardDeck> expose_decklist() {
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

    private List<Ladang> expose_ladanglist() {
        GameManager gm = new GameManager();
        Field[] declaredFields = gm.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            try {
                if (f.getName().equals("ladangList")) {
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
                Path gstate = fpath.resolve("gamestate.yaml");
                Path p1 = fpath.resolve("player1.yaml");
                Path p2 = fpath.resolve("player2.yaml");
                Path[] files = {gstate, p1, p2};
                for (Path p : files) {
                    if (!Files.isRegularFile(p)) {
                        throw new FileNotFoundException("File not found: " + p.toString());
                    }
                }

                GameManager.initGameManager();
                GameObjectFactory.Load();

                // Load game state from gamestate.yaml yang berformat:
                // totalTurnCounter: 5
                // shopItems: [PRODUCT_007]
                BufferedReader gameStateReader = Files.newBufferedReader(gstate, StandardCharsets.UTF_8);
                String gameStateString = gameStateReader.readLine();
                Map<String, Object> gameState = parseYaml(gameStateString);
                Integer totalTurnCounterValue = (Integer) gameState.get("totalTurnCounter");
                int totalTurnCounter = totalTurnCounterValue != null ? totalTurnCounterValue : 0;
                fieldSetter(gm, "totalTurnCounter", totalTurnCounter);
                fieldSetter(gm, "turnCounter", totalTurnCounter % 2);
                gameStateString = gameStateReader.readLine();
                gameState = parseYaml(gameStateString);
                List<String> shopItems = gameState.get("shopItems") != null ? (List<String>) gameState.get("shopItems") : new ArrayList<>();
                Toko.createToko();
                List<GameObject> shopItemsList = new ArrayList<>();
                for (String itemId : shopItems) {
                    GameObject item = GameObjectFactory.CreateGameObjectByID(itemId);
                    shopItemsList.add(item);
                }
                
                gameStateReader.close();
                
                // Load player data
                Field gold_field = gm.getClass().getDeclaredField("guldenList");
                Field ladang_field = gm.getClass().getDeclaredField("ladangList");
                Field deck_field = gm.getClass().getDeclaredField("deckList");
                
                Field[] tempfields = {gold_field, ladang_field, deck_field};
                for (Field f : tempfields) {
                    f.setAccessible(true);
                }

                List<Integer> gold_list = (ArrayList<Integer>) gold_field.get(gm);
                List<Ladang> ladang_list = (ArrayList<Ladang>) ladang_field.get(gm);
                List<CardDeck> deck_list = (ArrayList<CardDeck>) deck_field.get(gm);

                for (int i = 0; i < GameManager.defaultPlayerCount; i++) {
                    BufferedReader playerstates = Files.newBufferedReader(files[1 + i], StandardCharsets.UTF_8);
                    Map<String, Object> deckCount = parseYaml(playerstates.readLine());
                    deck_list.get(i).reduceUntil((Integer) deckCount.get("deckCount"));
                    Map<String, Object> active = parseYaml(playerstates.readLine());
                    List<Map<String, Object>> activeDeck = (List<Map<String, Object>>) active.get("activeDeck");
                    for (Map<String, Object> cardData : activeDeck) {
                        int index = (int) cardData.get("index");
                        String cardId = (String) cardData.get("cardId");
                        Card c = new Card(GameObjectFactory.CreateGameObjectByID(cardId));
                        deck_list.get(i).getHand().set(index, c);
                    }
                    Map<String, Object> gulden = parseYaml(playerstates.readLine());
                    gold_list.set(i, (Integer) deckCount.get("gulden"));
                    Map<String, Object> lad = parseYaml(playerstates.readLine());
                    List<Map<String, Object>> ladangData = (List<Map<String, Object>>) lad.get("ladang");
                    for (Map<String, Object> ladangItem : ladangData) {
                        int index = (int) ladangItem.get("index");
                        String gameObjectId = (String) ladangItem.get("gameObjectId");
                        int count = (int) ladangItem.get("count");
                        List<String> itemIds = (List<String>) ladangItem.get("items");
                        Petak petak = ladang_list.get(i).getPetak(index);
                        GameObject go = GameObjectFactory.CreateGameObjectByID(gameObjectId);
                        petak.setGameObject(go);
                        petak.getCount();
                        Map<Item, Integer> baru = new HashMap<>();
                        for (String itemId : itemIds) {
                            for (Map.Entry<Item, Integer> key : baru.entrySet()) {
                                if (key.getKey().getId().equals(itemId)) {
                                    baru.replace(key.getKey(), key.getValue()+1);
                                    break;
                                }
                                baru.put(key.getKey(), 1);
                            }
                        }
                        fieldSetter(petak, "item", baru);
                        ladang_list.get(i).add(petak);
                    }

                    playerstates.close();
                }

                for (Field f : tempfields) {
                    f.setAccessible(false);
                }
            } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
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
                Map<String, Object> gameState = new HashMap<>();
                // nanti bentuknya {totalTurn Counter: 5}
                gameState.put("totalTurnCounter", exposeFieldValue(flist, "totalTurnCounter"));

                Toko shop = (Toko) exposeFieldValue(flist, "shop");
                Map<GameObject, Integer> shopItems = shop.getListItems();
                List<String> shopItemIds = new ArrayList<>();
                for (Map.Entry<GameObject, Integer> entry : shopItems.entrySet()) {
                    int amount = entry.getValue();
                    for (int k = 0; k < amount; k++) {
                        shopItemIds.add(entry.getKey().getId());
                    }
                }
                gameState.put("{shopItems", shopItemIds);

                writeYamlToFile(new File(path + "/gamestate.yaml"), gameState);

                // Save player data
                List<CardDeck> cdlist = expose_decklist();
                List<Ladang> ldlist = expose_ladanglist();
                for (int i = 0; i < GameManager.defaultPlayerCount; i++) {
                    Map<String, Object> playerState = new HashMap<>();
                    playerState.put("gulden", ((List<Integer>) exposeFieldValue(flist, "guldenList")).get(i));
                    playerState.put("deckCount", cdlist.get(i).getCardCount());

                    List<Map<String, Object>> activeDeck = new ArrayList<>();
                    for (int j = 0; j < GameManager.maxHandCount; j++) {
                        Card c = cdlist.get(i).getHand().get(j);
                        if (c != null) {
                            Map<String, Object> cardData = new HashMap<>();
                            cardData.put("index", j);
                            cardData.put("cardId", c.convertToGameObject().getId());
                            activeDeck.add(cardData);
                        }
                    }
                    playerState.put("activeDeck", activeDeck);

                    List<Map<String, Object>> ladangData = new ArrayList<>();
                    Ladang ld = ldlist.get(i);
                    for (int j = 0; j < 30; j++) {
                        Petak petak = ld.getGrid().get(j);
                        if (petak.getGameObject() != null) {
                            Map<String, Object> ladangItem = new HashMap<>();
                            ladangItem.put("index", j);
                            ladangItem.put("gameObjectId", petak.getGameObject().getId());
                            ladangItem.put("count", petak.getCount());

                            List<String> itemIds = new ArrayList<>();
                            for (var entry : petak.getItem().entrySet()) {
                                for (int k = 0; k < entry.getValue(); k++) {
                                    itemIds.add(entry.getKey().getId());
                                }
                            }
                            ladangItem.put("items", itemIds);
                            ladangData.add(ladangItem);
                        }
                    }
                    playerState.put("ladang", ladangData);

                    writeYamlToFile(new File(path + "/player" + (i + 1) + ".yaml"), playerState);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    private Map<String, Object> parseYaml(String yamlString) {
        Map<String, Object> yamlMap = new HashMap<>();
        yamlString = yamlString.trim();
        yamlString = yamlString.substring(0, yamlString.length()); // Remove the curly braces
        String[] keyValuePairs = yamlString.split(",");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim().replaceAll("^\"|\"$", "");
            String value = keyValue[1].trim();
            if (value.startsWith("[")) {
                value = value.substring(1, value.length() - 1); // Remove the square brackets
                String[] arrayValues = value.split(",");
                List<String> list = new ArrayList<>();
                for (String val : arrayValues) {
                    list.add(val.trim().replaceAll("^\"|\"$", ""));
                }
                yamlMap.put(key, list);
            } else if (value.matches("-?\\d+(\\.\\d+)?")) {
                yamlMap.put(key, Integer.parseInt(value));
            } else {
                yamlMap.put(key, value.replaceAll("^\"|\"$", ""));
            }
        }
        return yamlMap;
    }

    private void writeYamlToFile(File file, Map<String, Object> yamlMap) throws IOException {
        StringBuilder yamlBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : yamlMap.entrySet()) {
            yamlBuilder.append(entry.getKey()).append(": ");
            if (entry.getValue() instanceof List) {
                yamlBuilder.append("[");
                List<?> list = (List<?>) entry.getValue();
                for (int i = 0; i < list.size(); i++) {
                    yamlBuilder.append(list.get(i));
                    if (i < list.size() - 1) {
                        yamlBuilder.append(", ");
                    }
                }
                yamlBuilder.append("]");
            } else {
                yamlBuilder.append(entry.getValue());
            }
            yamlBuilder.append("\n");
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(yamlBuilder.toString());
        }
    }
}