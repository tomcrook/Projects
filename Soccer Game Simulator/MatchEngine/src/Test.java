public class Test {

    public static void main(String[] args) {
        Match match = new Match();
        for (int i = 0; i < 10; i++) {
            match.playRound();
            match.printPitch();
        }
    }
}
