package Match;

import Generator.ScoreGenerator;
import Player.Player;
import Team.Team;

import java.util.ArrayList;

public class QuickMatch {

  ScoreGenerator gen = new ScoreGenerator();

  public int hG = 0, aG = 0;
  Team home;
  Team away;
  ArrayList<ArrayList<Player>> homeTeam;
  ArrayList<ArrayList<Player>> awayTeam;

  ArrayList<Player> homeInjured = new ArrayList<>();
  ArrayList<Player> awayInjured = new ArrayList<>();

  ArrayList<Player> homeGoals = new ArrayList<Player>();
  ArrayList<Player> awayGoals = new ArrayList<Player>();

  public QuickMatch(Team home, Team away) {
    this.home = home;
    this.away = away;
    this.homeTeam = home.getStartingXI();
    this.awayTeam = away.getStartingXI();
    this.playGame();
  }


  public String playGame() {
    this.hG = gen.getGoals(this.calculateMean(true));
    this.aG = gen.getGoals(this.calculateMean(false));

    for (int i = 0; i < hG; i++) {
      homeGoals.add(gen.getGoalScorer(homeTeam));
    }
    for (int i = 0; i < aG; i++) {
      awayGoals.add(gen.getGoalScorer(awayTeam));
    }
    for (int i = 0; i < hG; i++) {
      homeGoals.get(i).addGoal();
    }
    for (int i = 0; i < aG; i++) {
      awayGoals.get(i).addGoal();
    }

    int hI = gen.getInjuries(0.3);
    int aI = gen.getInjuries(0.3);


    this.homeInjured = addInjuries(hI, homeTeam);
    this.awayInjured = addInjuries(aI, awayTeam);


    return this.getScore();
  }

  private ArrayList<Player> addInjuries(int aI, ArrayList<ArrayList<Player>> awayTeam) {
    ArrayList<Player> injured = new ArrayList<Player>();
    for (int i = 0; i < aI; i++) {
      ArrayList<Player> row = awayTeam.get((int)(Math.random() * awayTeam.size()));
      Player p = row.get((int)(Math.random() * row.size()));
      while(injured.contains(p)) {
        row = awayTeam.get((int)(Math.random() * awayTeam.size()));
        p = row.get((int)(Math.random() * row.size()));
      }
      p.addInjury(gen.getInjuryDuration());
      injured.add(p);
    }
    return injured;
  }

  public String getScore() {
    return home.getName() + " " +  hG + " - " + aG + " " + away.getName() + "\n" +  this.goalScorers() + this.injuries();
  }

  public String goalScorers() {
    return "\nGoals: " + getString(homeGoals, awayGoals);
  }

  public String injuries() {
    return "\nInjuries: " + getString(homeInjured, awayInjured);
  }

  private String getString(ArrayList<Player> homeInjured, ArrayList<Player> awayInjured) {
    String ret = "\n";
    if (homeInjured.size() > 0) {
      ret += home.getName() + ":\n";
    }
    for (int i = 0; i < homeInjured.size(); i++) {
      ret += homeInjured.get(i).getName() + " (" + homeGoals.get(i).getPosition() + ")\n";
      if (i == homeInjured.size() - 1 && awayInjured.size() > 0) {
        ret += "\n";
      }
    }
    if (awayInjured.size() > 0) {
      ret += away.getName() + ":\n";
    }
    for (int i = 0; i < awayInjured.size(); i++) {
      ret += awayInjured.get(i).getName() + " (" + awayInjured.get(i).getPosition() + ")\n";
    }
    return ret;
  }

  private double calculateMean(boolean h) {
    double ret = 1.5;
    int diff = 0;
    if (h) {
      diff = (home.getTeamAvg() - away.getTeamAvg());
    } else {
      ret = 1.3;
      diff = (away.getTeamAvg() - home.getTeamAvg());
    }

    if (diff < 0) {
      ret += -1 * 0.1 * diff * diff * 0.15;
    } else {
      ret += 0.1 * diff * diff * 0.15;
    }
    if (ret < 0.6) {
      ret = 0.6;
    }

    return ret;

  }

}
