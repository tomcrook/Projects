import Generator.TeamGenerator;

public class TeamTest {

  public static void main(String[] args) {
    Team team = new Team(81, 23, new TeamGenerator());
    System.out.print(team);
  }

  static void testRoster() {
    Roster roster = new Roster(82, 24);
    System.out.println(roster.printTeam());
  }

}
