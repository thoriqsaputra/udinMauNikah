package org.awaludin.udinmaunikah.Programming;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;

public class GameObjectFactory {
    private static JSONParser parser = new JSONParser();
    private static HashMap codex = null;
    private static String file_name = "src\\main\\resources\\org\\AwalUdin\\udinmaunikah\\game_objects.json";
    private static boolean loaded = false;

    public static boolean Load(){
        if(!loaded){
            String fname = Paths.get(file_name).toAbsolutePath().toString();
            System.out.println(fname);
            StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            codex = (JSONObject) parser.parse(sb.toString());

            loaded = true;
            return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static GameObject CreateGameObjectByID(String gameobjectID){
        if (loaded){
            System.out.println(gameobjectID);
            JSONObject temp = (JSONObject) codex.get(gameobjectID);
            if (temp != null){
                switch (temp.get("type").toString()){
                    case "animal":
                        return GenerateAnimal(temp);
                    case "plant":
                        return  GeneratePlant(temp);
                    case "product":
                        return  GenerateProduct(temp);
                    default:
                        break;

                }
            }
        }

        return null;
    }

    public static GameObject CreateGameObjectByKey(String key, String value){
        if (loaded){
            for (Object benda : codex.keySet()) {
                JSONObject temp = (JSONObject) codex.get(benda);
                if (temp.get(key).equals(value)) {
                    return CreateGameObjectByID(benda.toString());
                }
            }
        }

        return null;
    }

    private static Product GenerateProduct (JSONObject data){
        boolean meat = false;
        boolean herb = false;
        for (String thing : ((List<String>)((JSONObject) data.get("properties")).get("diet"))){
            if (thing.equals("karnivora")){
                meat = true;
            }
            else if (thing.equals("herbivora")){
                herb = true;
            }
        }

        ProductType ptype = null;
        if (meat && herb){
            ptype = ProductType.BOTH;
        }
        else if (meat){
            ptype = ProductType.MEAT;
        }
        else if (herb){
            ptype = ProductType.PLANT;
        }
        Product product = new Product((String)data.get("name"),ptype);

        return product;
    }
;
    private static Plant GeneratePlant (JSONObject data){
        JSONObject temp = (JSONObject) data.get("properties");
        int growth_time = Math.toIntExact(((long) temp.get("time_to_harvest")));
        String product_id  = (String) temp.get("product_id");
        Product prod = (Product) CreateGameObjectByID((String) temp.get("product_id"));
        return new Plant((String)data.get("name"),growth_time,prod);
    }

    private static Animal GenerateAnimal (JSONObject data){
        JSONObject temp = (JSONObject) data.get("properties");
        int harvest_weight = Math.toIntExact(((long) temp.get("weight_to_harvest")));
        String product_id  = (String) temp.get("product_id");

        //animal type by diet
        boolean meat = false;
        boolean herb = false;

        for (String thing : (List<String>)temp.get("diet")){
            if (thing.equals("karnivora")){
                meat = true;
            }
            else if (thing.equals("herbivora")){
                herb = true;
            }
        }

        AnimalType atype = null;

        if (meat && herb){
            atype = AnimalType.OMNIVORE;
        }
        else if (meat){
            atype = AnimalType.CARNIVORE;
        }
        else if (herb){
            atype = AnimalType.HERBIVORE;
        }
        else{
            atype = AnimalType.UNDEFINED;
        }

        Product product = (Product) CreateGameObjectByID((String) temp.get("product_id"));

        return new Animal((String) data.get("name"),0,harvest_weight, product,atype);
    }

    public static void main(String[] args) {
        Load();
        Animal hiooo = (Animal) CreateGameObjectByKey("name","Hiu Darat");

        System.out.println(hiooo.GetName());
    }


}
