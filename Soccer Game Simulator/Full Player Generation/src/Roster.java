import Generator.PlayerGenerator;
import Player.Player;

import java.util.ArrayList;
import java.util.Random;

public class Roster {

  PlayerGenerator playerGenerator = new PlayerGenerator();
  ArrayList<Player> team = new ArrayList<Player>();
  Random r = new Random();

  public Roster(int teamAvg, int numPlayers) {
    this.generateTeam(teamAvg, numPlayers);
    this.generateTeam(teamAvg * 6 / 7, 11, 18);
  }

  public Roster() {}

  public void generateTeam(int teamAvg, int num) {
    String[] positions = {
      "GK", "RB", "CB", "CB", "LB", "CM", "CM", "RM", "LM", "ST", "ST"
    };

    for (int i = 0; i < num; i++) {
      int age = (int) (Math.random() * 8) + 25;
      if (i < 11) {
        if (i < 5) {
          team.add(new Player(generateRating(teamAvg, 2.3), 1, playerGenerator, age));
        } else if (i < 9) {
          team.add(new Player(generateRating(teamAvg, 2.3), 2, playerGenerator, age));
        } else {
          team.add(new Player(generateRating(teamAvg, 2.3), 3, playerGenerator, age));
        }
        team.get(i).setPosition(positions[i]);
      } else {
        if (Math.random() > .9) {
          team.add(new Player(generateRating(teamAvg, 2.3), 3, playerGenerator, age));
        } else {
          team.add(new Player(generateRating(teamAvg, 2.3), (int) (Math.random() * 2) + 1, playerGenerator, age));
        }
      }
    }
  }

  public void generateTeam(int teamAvg, int num, int a) {
    String[] positions = {
      "GK", "RB", "CB", "CB", "LB", "CM", "CM", "RM", "LM", "ST", "ST"
    };

    for (int i = 0; i < num; i++) {
      int age = (int) (Math.random() * 8) + a;
      if (Math.random() > .9) {
        team.add(new Player(generateRating(teamAvg, 2.3), 3, playerGenerator, age));
      } else {
        team.add(new Player(generateRating(teamAvg, 2.3), (int) (Math.random() * 2) + 1, playerGenerator, age));
      }
    }
  }

  /**
   * Generates a random player rating, following a Gaussian distribution
   * @param average : Average for distribution
   * @param d : standard deviation
   */
  public int generateRating(int average, double d) {
    return (int) ((r.nextGaussian() * d) + average);
  }

  public ArrayList<Player> getTeam() {
    return team;
  }

  public String printTeam() {
    String ret = "";
    for (int i = 0; i < team.size(); i++) {
      ret += team.get(i).teamView() + "\n";
    }
    return ret;
  }

  public String sortBy(String att) {
    switch (att) {
      case "PAC" : return this.sortByPAC();
      case "PHY" : return this.sortByPHY();
      case "POS" : return this.sortByPOS();
      default : return this.sortByOVR();
    }
  }

  public String sortByPAC() {
    ArrayList<Player> sorted = new ArrayList<Player>();
    for (int i = 0; i < team.size(); i++) {
      int max = Integer.MIN_VALUE;
      int index = 0;
      for (int j = 0; j < team.size(); j++) {
        if (team.get(j).getPAC() > max && !sorted.contains(team.get(j))) {
          max = team.get(j).getPAC();
          index = j;
        }
      }
      sorted.add(team.get(index));
    }
    String ret = "";
    for (int i = 0; i < team.size(); i++) {
      ret += sorted.get(i).teamView() + "\n";
    }
    return ret;

  }

  public String sortByPHY() {
    ArrayList<Player> sorted = new ArrayList<Player>();
    for (int i = 0; i < team.size(); i++) {
      int max = Integer.MIN_VALUE;
      int index = 0;
      for (int j = 0; j < team.size(); j++) {
        if (team.get(j).getPHY() > max && !sorted.contains(team.get(j))) {
          max = team.get(j).getPHY();
          index = j;
        }
      }
      sorted.add(team.get(index));
    }
    String ret = "";
    for (int i = 0; i < team.size(); i++) {
      ret += sorted.get(i).teamView() + "\n";
    }
    return ret;

  }

  public String sortByOVR() {
    ArrayList<Player> sorted = new ArrayList<Player>();
    for (int i = 0; i < team.size(); i++) {
      int max = Integer.MIN_VALUE;
      int index = 0;
      for (int j = 0; j < team.size(); j++) {
        if (team.get(j).getOVR() > max && !sorted.contains(team.get(j))) {
          max = team.get(j).getOVR();
          index = j;
        }
      }
      sorted.add(team.get(index));
    }
    String ret = "";
    for (int i = 0; i < team.size(); i++) {
      ret += sorted.get(i).teamView() + "\n";
    }
    return ret;

  }

  public String sortByPOS() {
    String[] positions = {
      "GK", "RB", "CB", "LB", "CM", "RM", "LM", "ST"
    };

    ArrayList<Player> sorted = new ArrayList<Player>();
    for (int i = 0; i < positions.length; i++) {
      for (int j = 0; j < team.size(); j++) {
        Player p = team.get(j);
        if (p.getPosition() == positions[i]) {
          sorted.add(p);
        }
      }
    }
    String ret = "";
    for (int i = 0; i < team.size(); i++) {
      ret += sorted.get(i).teamView() + "\n";
    }
    return ret;
  }

  public void endSeason() {
    for (Player p : this.team) {
      p.endSeason();
    }
  }


}
