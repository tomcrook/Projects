package Player;

import Generator.PlayerGenerator;

import java.io.IOException;

public class PlayerTest {

  public static void main(String[] args) throws IOException {
    testNameGeneration(100, new PlayerGenerator());
  }

  public static void testNameGeneration(int num, PlayerGenerator pG) throws IOException {
    for (int i = 0; i < num; i++) {
      System.out.println(new Player(74, (int)(Math.random() * 3) + 1, pG, 20));
    }
  }

  public static void testAging() throws IOException {
    Player player = new Player(67, 2, new PlayerGenerator(), 16);
    System.out.println(player);
    for (int i = 0; i < 30; i++) {
      player.endSeason();
      System.out.println("Age : " + (player.age) + "\n" + player);
    }
  }

  public static void testXG() throws IOException {
    Player player = new Player(90, 3, new PlayerGenerator(), 20);
    player.calculateXG(80, 4);
    System.out.println(player + "\n" + player.xG);
  }

  public static void testXA() throws IOException {
    Player player = new Player(90, 3, new PlayerGenerator(), 20);
    player.calculateXA(80, 4);
    System.out.println(player + "\n" + player.xA);
  }


}
