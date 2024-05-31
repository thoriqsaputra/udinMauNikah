package org.awaludin.udinmaunikah.Programming;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.*;
import java.util.Queue;

public class Card {
    private GameObject thing;
    private static String resource_path = "src\\main\\resources\\org\\awaludin\\udinmaunikah";
    //private Image displayImage
    // you could move description here, depends ig


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            if (((Card) obj).thing.getId().equals(this.thing.getId())) {
                return true;
            }
        }

        return false;
    }

    public Card(GameObject thing){
        this.thing = thing;


    }

    public Card(Card copycard){
        this.thing = copycard.thing;


    }

    public GameObject convertToGameObject(){
        //doany operation here
        return thing;
    }

    public String getImagePath(){
        String classname = this.thing.getClass().getSimpleName();
        String img_path = "Texture" + "/" + classname + "/" + this.thing.getId() + ".png";
        return img_path;
    }


}


class CardDeck {
    private List<CardSlot> deck;
    private List<Card> hand; //ini bebas/debatable
    private List<Card> graveyard; //usually called Banish in some CG
    private int cardCount;
    private Random rng;
    public boolean allowShuffleWhileDraft;
    public static String deck_template_location = "src\\main\\resources\\org\\AwalUdin\\udinmaunikah\\deck_template.json";

    public CardDeck(){
        deck = new ArrayList<CardSlot>();
        hand = new ArrayList<Card>();
        graveyard = new ArrayList<Card>();
        cardCount = 0;
        rng = new Random();
    }

    public CardDeck(int handSize){
        this();
        for (int i = 0; i < handSize; i++) {
            hand.add(null);
        }
    }

    public CardDeck(long seed){
        this();
        rng.setSeed(seed);
    }

    public List<CardSlot> getDeck(){
        return deck;
    }

    public boolean load_deck(String deck_name){
        String fname = Paths.get(deck_template_location).toAbsolutePath().toString();
        System.out.println(fname);
        StringBuilder sb = new StringBuilder();
        JSONObject deck_list;
        JSONParser parser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            deck_list = (JSONObject) parser.parse(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (deck_list != null) {
            JSONObject deck = (JSONObject) deck_list.get(deck_name);
            if (deck != null) {
                for (Object key : deck.keySet()) {
                    for (int i = 0 ; i < Math.toIntExact((long) deck.get(key)); i++){
                        Card c = new Card(GameObjectFactory.CreateGameObjectByID((String)key));
                        this.add(c);
                    }
                }
            }

            return true;
        }

        return false;
    }

    public boolean load_deck(){
        return this.load_deck("default");
    }

    public void shuffle(){
        for (int i = 0; i < graveyard.size(); i++){
            this.add(graveyard.get(0));//
            this.graveyard.remove(0);
        }

        assert graveyard.isEmpty();
    };

    //cuman ngapus card dari hand
    public Boolean use(Card c){
        for (int i = 0; i < hand.size(); i++){
            if (hand.get(i).equals(c)){
                hand.set(i, null);
                return true;
            }
        }
        System.out.println("Card not found in hand!");
        return false;
    }

    //draws 1 random available card
    public Card draw(){
        //count how many cards there are
        int i = rng.nextInt(0,cardCount); //target Literal Card
        System.out.printf("Fetch %d\n",i);
        int j = 0; //Literal Card iterator
        int k = 0; //CardSlot iterator
        int l = 0; //local Card iterator (buat iterate kartu dalam suatu CardSlot)
        while(j != i || deck.get(k).count() == 0){
            //locally iterate card on current CardSlot
            if (j != i){
                l++;
            }

            //Change cardslots when over curent cardslot
            //and step over any empty cardslot
            while (l >= deck.get(k).count()){
                l -= deck.get(k).count();
                k++;
            }

            if (j < i){
                j++;
            }
        }
        assert (j == i);

        this.cardCount--;
        return deck.get(k).Draw();
    }

    //draws card_ammt from the deck
    //immediately decreases count in cardslot so dont forget to return card
    public List<Card> draft_pick(int card_ammt){
        List<Card> retval = new ArrayList<Card>();

        //gamerule
        for (int i = 0; i < card_ammt && cardCount > 0; i++){
            retval.add(this.draw());
        };


        if (this.cardCount == 0){
            shuffle();
        }

        assert retval.size() <= card_ammt;
        return retval;
    }

    public int getHandSize(){
        int retval = 0;
        for (int i = 0; i < GameManager.maxHandCount; i++){
            if (hand.get(i) != null){
                retval ++;
            }
        }
        return retval;
    }

    public void return_draft_pick(List<Card> remainingCards){
        for (Card c : remainingCards){
            this.add(c);
        }
        remainingCards.clear();
        assert remainingCards.isEmpty();
    }

    //Add new permanent cards to the deck
    public void add(Card thing){
        int i = 0;
        while (i < deck.size() && deck.get(i).GetCardThing().convertToGameObject().getId() != thing.convertToGameObject().getId()) {
            i++;
        }
        //needs new CardSlot
        if (i == deck.size()){
            deck.add(new CardSlot(thing));
        }
        else{
            deck.get(i).add();
        }

        cardCount++;
    };

    //deck related shit

    public List<Card> getHand() {
        return hand;
    }

    public void addToHand(Card card){
        hand.add(card);
    }

    public boolean useHand(Card card){
        if (hand.contains(card)){
            hand.remove(card);
            return true;
        }

        return false;
    }

    public int getCardCount(){
        return this.cardCount;
    }
}

class CardSlot{ //kenapa? store more than 1 card with less memory
    private Card thing;
    private int count;

    public CardSlot(Card thing){
        this.thing = thing;
        this.count = 1;
    }

    public CardSlot(GameObject thing_object){
        this.thing = new Card(thing_object);
        this.count = 1;
    };

    public Card Draw(){
        this.count--;
        return new Card(thing);
    }


    //add series
    public void add(){
        count++;
        assert count > 0;
    }

    public void add(int n){
        if (n > 0){
            count += n;
        }
        assert count > 0;
    }

    public int count(){
        return count;
    }

    public Card GetCardThing(){
        return thing;
    }
}