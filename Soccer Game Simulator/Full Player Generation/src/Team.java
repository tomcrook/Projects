import Generator.TeamGenerator;
import Player.Player;

import java.util.ArrayList;

public class Team {

  String name;
  int teamAvg;
  Roster roster;

  public Team(int rating, int numPlayers, TeamGenerator tG) {
    this.name = tG.generateName();
    this.teamAvg = rating;
    this.roster = new Roster(teamAvg, numPlayers);
  }




  @Override
  public String toString() {
    return this.name;
  }

}
