package LeagueSystem;

public class SystemTest {

  public static void main(String[] args) {
    LeagueSystem system = new LeagueSystem();
    for (int i = 0; i < 1; i++) {
      system.simulateSystem();
    }
    System.out.println(system.printLeagues());
  }


}
