public class Card {

    int rank, suit;
    String title = "";

    String[] suits = {
            "/s", "/d", "/h", "/c"
    };

    String[] ranks = {
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"
    };

    Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
        this.makeTitle();
    }

    Card(String title) {
        this.title = title;
        this.titleCheck();
    }

    void makeTitle() {
        this.title = ranks[rank] + suits[suit];
    }

    void titleCheck() {
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                String name = ranks[j] + suits[i];
                if (name.equals(title)) {
                    this.rank = j;
                    this.suit = i;
                    break;
                }
            }
        }
    }

}
