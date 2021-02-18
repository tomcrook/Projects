package LeagueSystem;

import League.League;
import Team.Team;

import java.util.ArrayList;

public class LeagueSystem {

  private final int numLeagues = 4;
  public ArrayList<League> system;

  public LeagueSystem() {
    system = new ArrayList<League>();
    this.initializeSystem();
  }

  private void initializeSystem() {
    int distribution;
    if (numLeagues > 9) {
      distribution = 70 / numLeagues;
    } else {
      distribution = 7;
    }
    for (int i = 0; i < numLeagues; i++) {
      system.add(new League(85 - (distribution * i), "Division " + (i + 1)));
    }
  }

  public String printLeagues() {
    String ret = "";
    for (int i = 0; i < numLeagues; i++) {
      League curr = system.get(i);
      ret += curr.name + "\n";
      ret += curr.leagueTable() + "\n\n";
    }
    return ret;
  }

  public void simulateSystem() {
    for (int i = 0; i < numLeagues; i++) {
      system.get(i).simulateSeason();
    }
  }

  public void endSeason() {

    for (int i = 0; i < numLeagues; i++) {
      system.get(i).endSeason();
    }

    for (int i = 0; i < numLeagues - 1; i++) {
      ArrayList<Team> relegated = system.get(i).getRelegated();
      ArrayList<Team> promoted = system.get(i+1).getPromoted();

      for (int x = 0; x < 3; x++) {
        system.get(i).addNew(promoted.get(x));
        system.get(i + 1).addNew(relegated.get(x));
      }

    }
  }

}
