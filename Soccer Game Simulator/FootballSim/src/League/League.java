package League;

import Generator.TeamGenerator;
import Match.QuickMatch;
import Player.Player;
import Schedule.Schedule;
import Team.*;

import java.util.ArrayList;
import java.util.Collections;

public class League {

  private Schedule schedule;
  private final int numTeams = 16;
  public ArrayList<Team> teams;
  public int gamesPlayed = 0;
  public String name;

  Roster roster = new Roster();

  ArrayList<Team> promoted = null;
  ArrayList<Team> relegated = null;

  public League(int leagueAverage, String name) {
    while (schedule == null || !schedule.isValidBoard()) {
      schedule = new Schedule(numTeams);
    }
    TeamGenerator gen = new TeamGenerator();
    teams = new ArrayList<Team>();
    for (int i = 0; i < numTeams; i++) {
      teams.add(new Team(gen.generateRating(leagueAverage, 2.5), 25));
    }
    this.name = name;
  }

  public League(int leagueAverage) {
    this(leagueAverage, "League");
  }

  public String leagueTable() {
    String finRet = "";
    if (this.gamesPlayed == 0) {
      this.sortByAvg();
    } else {
      this.sortStandings();
    }
    for (int j = 1; j < numTeams + 1; j++) {
      int i = j - 1;
      String ret = "";
      if (j < 10) {
        ret += " ";
      }
      ret += j + ". " + teams.get(i).getName() + " (" + teams.get(i).getTeamAvg() + ")";

      int buffer = 45 - ret.length();
      for (int x = 0; x < buffer; x++) {
        ret += " ";
      }
      ret += teams.get(i).printSeasonStats() + "\n";
      finRet += ret;
    }
    return finRet;
  }

  public void sortByAvg() {
    for (int i = 0; i < numTeams - 1; i++) {
      for (int j = i + 1; j < numTeams; j++) {
        if (teams.get(i).getTeamAvg() < teams.get(j).getTeamAvg()) {
          Collections.swap(teams, i, j);
        }
      }
    }
  }

  public int size() {
    return teams.size();
  }

  public void sortStandings() {
    for (int i = 0; i < numTeams - 1; i++) {
      for (int j = i + 1; j < numTeams; j++) {
        if (!teams.get(i).betterSeason(teams.get(j))) {
          Collections.swap(teams, i, j);
        }
      }
    }
  }

  public void playGames() {

    for (int i = 0; i < numTeams; i++) {
      Team home = teams.get(i);
      Team away = null;
      int a = schedule.getOpponent(i, gamesPlayed + 1);
      if (a != -1) {
        away = teams.get(a);
        QuickMatch match = new QuickMatch(home, away);
        match.playGame();
        home.addGame(match.hG, match.aG);
        away.addGame(match.aG, match.hG);
      }
    }
    gamesPlayed++;
  }

  public void simulateSeason() {
    while (gamesPlayed < (numTeams - 1) * 2) {
      this.playGames();
    }
  }

  public ArrayList<Team> getPromoted() {
    this.teams.removeAll(promoted);
    return promoted;
  }

  public ArrayList<Team> getRelegated() {
    this.teams.removeAll(relegated);
    return relegated;
  }

  public void addNew(Team team) {
    this.teams.add(team);
  }

  public void endSeason() {
    this.sortStandings();
    ArrayList<Team> p = new ArrayList<Team>();

    p.add(teams.get(0));
    p.add(teams.get(1));
    p.add(teams.get(2));

    this.promoted = p;

    ArrayList<Team> ret = new ArrayList<Team>();

    ret.add(teams.get(teams.size() - 1));
    ret.add(teams.get(teams.size() - 2));
    ret.add(teams.get(teams.size() - 3));

    this.relegated = ret;

    for (int i = 0; i < numTeams; i++) {
      this.teams.get(i).endSeason();
    }
    this.gamesPlayed = 0;
  }

  public String topGoalScorers() {
    ArrayList<Player> scorers = new ArrayList<Player>();
    for (Team t : this.teams) {
     scorers.addAll(t.seasonScorers());
    }
    for (int i = 0; i < scorers.size() - 1; i++) {
      for (int j = 0; j < scorers.size(); j++) {
        if (scorers.get(i).seasonGoals > scorers.get(j).seasonGoals) {
          Collections.swap(scorers, i, j);
        }
      }
    }
    String ret = "";
    for (int i = 0; i < 10; i++) {
      ret += scorers.get(i).getPosition() + " " + scorers.get(i).getName() + " " + scorers.get(i).FIN + " " + scorers.get(i).seasonGoals + "\n";
    }
    return ret;
  }


}
