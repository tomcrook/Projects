import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {

    ArrayList<Card> hand = new ArrayList<>();

    Player() {

    }

    void addCard(Card card) {
        hand.add(card);
    }

}
