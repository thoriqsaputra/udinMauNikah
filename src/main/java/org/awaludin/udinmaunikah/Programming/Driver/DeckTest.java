package org.awaludin.udinmaunikah.Programming.Driver;

import org.awaludin.udinmaunikah.Programming.Card;
import org.awaludin.udinmaunikah.Programming.GameManager;
import org.awaludin.udinmaunikah.Programming.GameObjectFactory;
import java.util.List;

public class DeckTest {

    private static void cardlistPrinter(List<Card> clist, String message){
        System.out.printf("%s :",message);
        for (Card c : clist) {
            if (c != null){ //PENTING ! Null slot digunakan untuk menggambarkan slot kosong
                System.out.printf("%s, ", c.convertToGameObject().GetName());
            }
            else{
                System.out.print("{EMPTY} ");
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {
        //INISIALISASI GAMEMANAGER UNTUK SIMULASI DRAW CARD
        GameManager.initGameManager();
        GameObjectFactory.Load();


        //SETUP DECK
        for (int i = 0; i < 2; i++) {
            GameManager.SetUpUtils.useDeck(i,"default");
        }

        //START
        System.out.println("COMMENCE TESTING : ");

        //DRAFT PHASE
        GameManager.PlayerInterface.beginDraftPick();

        //LIHAT DAFTAR DRAFT
        List<Card> clist = GameManager.PlayerInterface.getDraftList();
        ////printer
        cardlistPrinter(clist, "DraftList");

        //Anggap, ambil kartu paling kiri
        System.out.println("TAKING LEFTMOST CARD");
        Card wantedCard = clist.get(0); //get the card object
        GameManager.PlayerInterface.takeCard(wantedCard);

        //sekarang kartu ada di picklist
        ////printer
        List<Card> picklist = GameManager.PlayerInterface.getPickList();
        cardlistPrinter(picklist, "PickList");
        // NOTE : Sebagai GUI sebaiknya tidak langsung ambil dari picklist, sebaiknya refresh/ check ulang hand

        //REROLL
        System.out.println("REROLL!");
        GameManager.PlayerInterface.reroll();
        clist = GameManager.PlayerInterface.getDraftList();

        // check picklist pastikan kosong
        picklist = GameManager.PlayerInterface.getPickList();
        cardlistPrinter(picklist, "PickList after reroll");

        ////printer
        cardlistPrinter(clist, "DraftList after reroll");

        //Sebelum end draftpick :
        cardlistPrinter(GameManager.PlayerInterface.getHand(), "Hand before end draftpick");

        //ambil kartu paling kiri dan kartu paling kanan
        System.out.println("TAKING LEFTMOST CARD AND RIGHTMOST CARD");
        Card c1 = clist.get(0);
        Card c2 = clist.get(3);
        GameManager.PlayerInterface.takeCard(c1);
        GameManager.PlayerInterface.takeCard(c2);

        //setelah end draftpick :
        GameManager.PlayerInterface.endDraftPick();
        System.out.print("END DRAFT PICKK RESULTS: ");
        ////PRINTER
        cardlistPrinter(GameManager.PlayerInterface.getHand(), "HAND");

        //pindahkan kartu ke dua dari slot 2 ke slot 6:
        GameManager.PlayerInterface.moveCard(1,5);
        cardlistPrinter(GameManager.PlayerInterface.getHand(), "AFTER MOVE");

        //use card
        System.out.println("USING FIRST CARD");
        GameManager.PlayerInterface.useCard(0);
        cardlistPrinter(GameManager.PlayerInterface.getHand(), "AFTER USE");


    }
}
