//package org.AwalUdin.udinmaunikah.Programming;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GameManager {
//    public final int defaultPlayerCount = 2;
//
//    private static List<Ladang> ladangList;
//    private static List<CardDeck> deckList;
//    private static GameManager gameManagerInstance;
//    private static int turnCounter;
//
//    // Reset all ladang in ladang list, empties all decklist
//    public static void initGameManager() {
//        for (int i = 0; i < gameManagerInstance.defaultPlayerCount; i++){
//            ladangList.set(i,new Ladang());
//            deckList.set(i,new CardDeck());
//        }
//        turnCounter = 0;
//    }
//
//    public static void nextTurn() {
//        turnCounter = (turnCounter + 1) % gameManagerInstance.defaultPlayerCount;
//    }
//
//    public static class PlayerInterface{
//
//        //PLAYER DRAFT PICK
//        private static List<Card> draftList;
//        private static List<Card> pickList;
//
//        //Use to get the usual 4 cards, DONT FORGET TO RETURN CARDS
//        public void beginDraftPick(){
//            draftList = deckList.get(turnCounter).draft_pick(4);
//            pickList = new ArrayList<>();
//        }
//
//        public List<Card> getDraftList(){
//            return draftList;
//        }
//
//        public void takeCard(Card card){
//            pickList.add(card);
//            draftList.remove(card);
//        }
//
//        public void returnCard(Card card){
//            pickList.remove(card);
//            draftList.add(card);
//        }
//
//        //sebelum end draft pick
//        public void endDraftPick(){
//            deckList.get(turnCounter).
//            deckList.get(turnCounter).return_draft_pick(draftList);
//        }
//
//        //ACTION STUFFS
//        public void getHand(){
//            deckList.get(turnCounter).getHand();
//        }
//    }
//
//    public static class EventManager{
//        public boolean roll_event(){
//
//        }
//    }
//}