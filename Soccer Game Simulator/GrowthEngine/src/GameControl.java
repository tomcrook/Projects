import java.util.Scanner;

public class GameControl {

    Pyramid pyramid;
    Scanner kb;

    GameControl() {
        kb = new Scanner(System.in);
    }

    void playGame() {
        pyramid = new Pyramid();
        while(true) {
            switch (gameInit()) {
                case 1: {
                    System.out.println("How many leagues?");
                    int num = kb.nextInt();
                    if (num > 9) {
                        System.out.println("9 leagues maximum... 9 selected.");
                        num = 9;
                    }
                    pyramid.play(num);
                    break;
                }
                case 2: {
                    System.out.println("First Name:");
                    String fN = kb.nextLine();
                    kb.nextLine();

                    System.out.println("Last Name:");
                    String lN = kb.nextLine();
                    Manager manager = new Manager(fN, lN);
                    manager.play();
                    break;
                }
            }
        }
    }

    private int gameInit() {
        System.out.println("Welcome to Tom's Soccer Simulator v1.0");
        System.out.println("--------------------------------------");
        System.out.println("1) Season simulator");
        System.out.println("2) Single player simulator");
        int choice = kb.nextInt();
        return choice;
    }

}
