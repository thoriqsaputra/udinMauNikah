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
    private static int totalTurnCounter;
    private static int turnCounter;
    private static Toko shop;

    // Reset all ladang in ladang list, empties all decklist
    public static void initGameManager() {
        ladangList = new ArrayList<>();
        deckList = new ArrayList<>();
        guldenList = new ArrayList<>();
        for (int i = 0; i < defaultPlayerCount; i++){
            ladangList.add(new Ladang());
            deckList.add(new CardDeck(maxHandCount));
            guldenList.add(0);
        }
        shop = new Toko();
        turnCounter = 0;
        totalTurnCounter = 0;
    }

    public static void nextTurn() {
        turnCounter++;
        turnCounter = (turnCounter + 1) % defaultPlayerCount;
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

        public static List<Card> getDraftList(){
            return draftList;
        }

        public static boolean takeCard(Card card){
            if (pickList.size() + getHand().size() < maxHandCount && draftList.contains(card)){
            pickList.add(card);
            draftList.remove(card);
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
                getHand().add(card);
            }
        }

        //ACTION STUFFS
        public static List<Card> getHand(){
            return deckList.get(turnCounter).getHand();
        }

        public static Boolean useCardAt(Card card, int index, boolean attacking){
            //pastiin kita emang punya kartunya
            if (!getHand().contains(card)){
                System.out.println("Card not in hand!");
                return false;
            }
            //ladangList.get(turnCounter + attacking*1).

            //say move was legal
            boolean removal = deckList.get(turnCounter).use(card);

            assert removal;
            return true;
        }

        public static Boolean tryAddToHand(Card card){
            int i;
            for (i = 0; i < maxHandCount; i++){
                if (deckList.get(turnCounter).getHand().get(i) != null){
                    i++;
                }
                else{
                    deckList.get(turnCounter).getHand().set(i, card);
                    return true;
                }
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