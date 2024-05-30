package org.awaludin.udinmaunikah.Programming;
import org.controlsfx.control.BreadCrumbBar;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public static final int defaultPlayerCount = 2;
    public static final int maxHandCount = 6;

    private static List<Integer> guldenList;
    private static List<Ladang> ladangList;
    private static List<CardDeck> deckList;
    private static List<Ladang> ladangDeckList;
    private static int totalTurnCounter;
    private static int turnCounter;

    // Reset all ladang in ladang list, empties all decklist
    public static void initGameManager() {
        ladangList = new ArrayList<>();
        ladangDeckList = new ArrayList<>();
        deckList = new ArrayList<>();
        guldenList = new ArrayList<>();
        for (int i = 0; i < defaultPlayerCount; i++){
            ladangList.add(new Ladang());
            deckList.add(new CardDeck(maxHandCount));
            guldenList.add(0);
            ladangDeckList.add(new Ladang());
        }
        Toko.createToko();
        turnCounter = 0;
        totalTurnCounter = 0;
    }

    public static List<Ladang> getLadangList(){
        return ladangList;
    }

    public static List<CardDeck> getDeckList(){
        return deckList;
    }

    public static List<Ladang> getLadangDeckList(){
        return ladangDeckList;
    }

    public static void nextTurn() {
        int oldTurnCounter = turnCounter;
        turnCounter++;
        totalTurnCounter++;
        turnCounter = (oldTurnCounter + 1) % defaultPlayerCount;
    }

    public static void sellItems(GameObject product){
        Toko.addItem(product);
        Product pr = (Product) product;

        guldenList.set(turnCounter, guldenList.get(turnCounter) + pr.getPrice());
        int guldenNow = getGulden(getTurnCounter()) + pr.getPrice();

        System.out.println(getTurnCounter() + " " + guldenNow);

        guldenList.set(getTurnCounter(), guldenNow);
    }

    public static int getTurnCounter() {
        return turnCounter;
    }

    public static int getTotalTurnCounter() {
        return totalTurnCounter;
    }

    public static int getGulden(int index){
        return guldenList.get(index);
    }

    public static class SetUpUtils{
        public static boolean useDeck(int player, String deckname){
            return deckList.get(player).load_deck(deckname);
        }
    }

    public static class PlayerInterface{

        //PLAYER DRAFT PICK
        private static List<Card> draftList;
        private static List<Card> pickList;

        //Use to get the usual 4 cards, DONT FORGET TO RETURN CARDS
        public static void beginDraftPick(){
            draftList = deckList.get(turnCounter).draft_pick(4);
            pickList = new ArrayList<>();
        }

        public static void reroll(){
            deckList.get(turnCounter).return_draft_pick(draftList);
            beginDraftPick();
        }

        public static List<Card> getPickList(){
            return pickList;
        }

        public static List<Card> getDraftList(){
            return draftList;
        }

        //DRAFT LIST PURPOSE
        public static boolean takeCard(Card card){
            if (pickList.size() + deckList.get(turnCounter).getHandSize() < maxHandCount && draftList.contains(card)){
                pickList.add(card);
                draftList.remove(card);

//                for (Card car : pickList){
//                    System.out.println(car.convertToGameObject().GetName());
//                }

                return true;
            }
            return false;
        }

        public static boolean returnCard(Card card){
            if (pickList.contains(card)){
            pickList.remove(card);
            draftList.add(card);
            return true;
            }
            return false;
        }

        //sebelum end draft pick
        public static void endDraftPick(){
            deckList.get(turnCounter).return_draft_pick(draftList);
            for (Card card : pickList){
//                getHand().add(card);
                tryAddToHand(card);
            }
        }

        //ACTION STUFFS
        public static List<Card> getHand(){
            return deckList.get(turnCounter).getHand();
        }

        //GUNAKAN JIKA MENCOBA UNTUK MENAMBAHKAN KARTU KE TANGAN
        public static Boolean tryAddToHand(Card card){
            int i;
            for (i = 0; i < maxHandCount; i++){
                if (deckList.get(turnCounter).getHand().get(i) != null){
                }
                else{
                    deckList.get(turnCounter).getHand().set(i, card);
                    return true;
                }
            }
            return false;
        }

        //REMOVE CARD FROM HAND
        //hanya menghandle penghapusan kartu dari hand pada slot tersebut
        //returns kartu pada slot tsb jika berhasil, return null jika gagal
        public static Card useCard(int slot){
            if (getHand().get(slot) != null){
                Card retval = getHand().get(slot);
                getHand().set(slot, null);
                return retval;
            }
            return null;
        }

        public static Card useCardT(Card card) {
            List<Card> hand = getHand();

            for (int i = 0; i < hand.size(); i++) {
                if (card.equals(hand.get(i))) {
                    Card retval = hand.get(i);
                    hand.set(i, null);
                    return retval;
                }
            }
            return null;
        }

        //alternatif, untuk memindahkan kartu dari slot x ke y saja (handles swapping);
        public static boolean moveCard(int from, int to){
            //ANGGAPAN INPUT SUDAH BENAR
            if (getHand().get(from) != null){
                Card temp = null;
                if (getHand().get(to) != null){
                    temp = getHand().get(to);
                }
                getHand().set(to, getHand().get(from));
                getHand().set(from, temp);
                return true;
            }
            return false;
        }
    }

    public static class EventManager{
        public boolean roll_event(){
//            return ladangList.get(turnCounter).bearAttack();
            return false;
        }
    }

    public static class SaveNLoad{

    }
}