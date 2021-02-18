package League;

public class LeagueTest {

  public static void main(String[] args) {
    League league = new League(82);
    league.simulateSeason();
    System.out.println(league.leagueTable());
    System.out.println(league.topGoalScorers());
  }

}
