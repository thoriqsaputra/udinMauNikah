public class Card {
    private GameObject thing;
    //private Image displayImage
    // you could move description here, depends ig

    public GameObject convertToGameObject(){
        //doany operation here
        return thing;
    }
}


class CardDeck {
    private List<Card> deck;
    private List<Card> hand; //ini bebas/debatable
    private List<Card> graveyard; //usually called Banish in some CG

    public void shuffle(){};
}