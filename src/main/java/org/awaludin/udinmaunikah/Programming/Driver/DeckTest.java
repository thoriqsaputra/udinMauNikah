package org.awaludin.udinmaunikah.Programming.Driver;

import org.awaludin.udinmaunikah.Programming.Card;
import org.awaludin.udinmaunikah.Programming.GameManager;
import org.awaludin.udinmaunikah.Programming.GameObjectFactory;
import java.util.List;

public class DeckTest {
    public static void main(String[] args) {
        //INISIALISASI GAMEMANAGER UNTUK SIMULASI DRAW CARD
        GameManager.initGameManager();
        GameObjectFactory.Load();

        //START
        System.out.println("COMMENCE TESTING : ");
        //SETUP DECK
        for (int i = 0; i < 2; i++) {
            GameManager.SetUpUtils.useDeck(i,"default");
        }

        //DRAFT PHASE
        GameManager.PlayerInterface.beginDraftPick();

        //LIHAT DAFTAR DRAFT
        List<Card> clist = GameManager.PlayerInterface.getDraftList();
        ////printer
        System.out.print("Draft pick: ");
        for (Card c : clist) {
            System.out.printf("%s, ", c.convertToGameObject().GetName());
        }
        System.out.println();

        //Anggap, ambil kartu paling kiri
        System.out.println("TAKING LEFTMOST CARD");
        Card wantedCard = clist.get(0); //get the card object
        GameManager.PlayerInterface.takeCard(wantedCard);

        //sekarang kartu ada di picklist
        ////printer
        System.out.print("Picklist: ");
        List<Card> picklist = GameManager.PlayerInterface.getPickList();
        for (Card c : picklist) {
            System.out.printf("%s, ", c.convertToGameObject().GetName());
        }
        System.out.println();
        // NOTE : Sebagai GUI sebaiknya tidak langsung ambil dari picklist, sebaiknya refresh/ check ulang hand

        //REROLL
        System.out.println("REROLL!");
        GameManager.PlayerInterface.reroll();
        clist = GameManager.PlayerInterface.getDraftList();
        // check picklist pastikan kosong

        System.out.print("Picklist After Reroll: ");
        picklist = GameManager.PlayerInterface.getPickList();

        ////PRINTER
        for (Card c : picklist) {
            System.out.printf("%s, ", c.convertToGameObject().GetName());
        }
        System.out.println();

        ////printer
        System.out.print("NEW Draft pick: ");
        for (Card c : clist) {
            System.out.printf("%s, ", c.convertToGameObject().GetName());
        }
        System.out.println();

        //Sebelum end draftpick :
        ////PRINTER
        System.out.print("Check if hand empty after reroll: ");
        for (Card c : GameManager.PlayerInterface.getHand()) {
            if (c != null){ //PENTING ! Null slot digunakan untuk menggambarkan slot kosong
                System.out.printf("%s, ", c.convertToGameObject().GetName());
            }
            else{
                System.out.print("{EMPTY} ");
            }
        }
        System.out.println();

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
        for (Card c : GameManager.PlayerInterface.getHand()) {
            if (c != null){ //PENTING ! Null slot digunakan untuk menggambarkan slot kosong
                System.out.printf("%s, ", c.convertToGameObject().GetName());
            }
            else{
                System.out.print("{EMPTY} ");
            }
        }
        System.out.println();


    }
}
