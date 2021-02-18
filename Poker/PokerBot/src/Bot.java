import java.util.Scanner;

public class Bot {

    Scanner kb = new Scanner(System.in);
    int opps = 3;
    int round = 1;
    Deck deck = new Deck();

    Card card1 = new Card(1,1), card2 = new Card(1,1);

    Bot() {
        deck = new Deck();
    }

    void play() {
        System.out.println("Card 1:");
        String name = kb.nextLine();
        this.card1 = deck.pullCard(name);
        System.out.println("Card 2:");
        name = kb.nextLine();
        this.card2 = deck.pullCard(name);
        System.out.println(card1.title + "\n" + card2.title);

    }

}
