package org.awaludin.udinmaunikah.Programming;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.*;
import java.util.Queue;

public class Card {
    private GameObject thing;
    //private Image displayImage
    // you could move description here, depends ig

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
}


class CardDeck {
    private List<CardSlot> deck;
    private List<Card> hand; //ini bebas/debatable
    private List<Card> graveyard; //usually called Banish in some CG
    private int cardCount;
    private Random rng;
    public boolean allowShuffleWhileDraft;

    public CardDeck(){
        deck = new ArrayList<CardSlot>();
        hand = new ArrayList<Card>();
        graveyard = new ArrayList<Card>();
        cardCount++;
        rng = new Random();
    }

    public CardDeck(long seed){
        this();
        rng.setSeed(seed);
    }

    public void shuffle(){
        for (int i = 0; i < graveyard.size(); i++){
            this.add(graveyard.get(0));//
            this.graveyard.remove(0);
        }

        assert graveyard.isEmpty();
    };

    //draws 1 random available card
    public Card draw(){
        //count how many cards there are
        int i = rng.nextInt(0,cardCount); //target Literal Card
        int j = 0; //Literal Card iterator
        int k = 0; //CardSlot iterator
        int l = 0; //local Card iterator (buat iterate kartu dalam suatu CardSlot)
        while(j != i || deck.get(k).count() == 0){
            // //while the currect cardslot has no cards
            // while (deck.get(k).count() == 0){
            //     k++;
            // } ACCOMPLISHED IN THE LATER PART OF CARDSLOT ITTERATION

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

    public void return_draft_pick(List<Card> remainingCards){
        for (int i = 0; i < remainingCards.size(); i++){
            this.add(remainingCards.get(0));
            remainingCards.remove(0);
        }

        assert remainingCards.isEmpty();
    }

    //Add new permanent cards to the deck
    public void add(Card thing){
        int i = 0;
        while (i < deck.size() && deck.get(i).GetCardThing() != thing) {
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
}

class CardSlot{ //kenapa? store more than 1 card with less memory
    private Card thing;
    private int count;

    public CardSlot(Card thing){
        this.thing = thing;
        count = 1;
    }

    public CardSlot(GameObject thing_object){
        this.thing = new Card(thing_object);
        count = 1;
    };

    public Card Draw(){
        count--;
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