package Match;

import Team.Team;

public class MatchTest {

  public static void main(String[] args) {

    QuickMatch match = new QuickMatch(new Team(80,18), new Team(80, 18));
    System.out.println(match.getScore());


  }

}
