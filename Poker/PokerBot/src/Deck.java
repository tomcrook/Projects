import java.util.ArrayList;
public class Deck {

    ArrayList<Card> deck = new ArrayList<Card>();

    Deck() {
        this.createDeck();
        this.shuffle();
    }

    void createDeck() {
        deck = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new Card(j, i));
            }
        }
    }

    void shuffle() {
        for (int i = 0; i < 52; i++) {
            this.swap(i, (int) (Math.random() * 52));
        }
    }

    void swap(int i, int j) {
        Card card = deck.get(i);
        deck.set(i, deck.get(j));
        deck.set(j, deck.get(i));
    }

    void printDeck() {
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i).title);
        }
    }

    Card drawCard() {
        Card card = deck.get((int)(Math.random() * deck.size()));
        System.out.println(card.title);
        deck.remove(card);
        return card;
    }

    void removeCard(Card card) {
        deck.remove(card);
    }

    Card pullCard(String card) {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).title.equals(card)) {
                Card Card = deck.get(i);
                deck.remove(Card);
                return Card;
            }
        }
        return null;
    }
}
