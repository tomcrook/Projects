import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

  ArrayList<ArrayList<Integer>> schedule;
  int numTeams;

  public Schedule(int num) {
    numTeams = num;
    schedule = new ArrayList<ArrayList<Integer>>(numTeams);
    this.initializeBoard();
    this.makeSchedule();
  }

  public void initializeBoard() {
    for (int i = 0; i < numTeams; i++) {
      ArrayList<Integer> row = new ArrayList<Integer>();
      for (int j = 0; j < numTeams; j++) {
        row.add(0);
      }
      schedule.add(row);
    }
  }

  public void makeSchedule() {
    for (int i = 1; i < numTeams; i++) { // for each game-week
      ArrayList<Integer> placed = new ArrayList<Integer>();
      placed.clear();
      for (int j = 0; j < numTeams; j++) { // add a game to each team
        for (int z = j+1; z < numTeams; z++) { // against each other team
          if (!placed.contains(j+1) && !placed.contains(z+1) && schedule.get(j).get(z) == 0
              && this.canPlay(i, z) && this.canPlay(i, j)) {
            placed.add(j+1);
            placed.add(z+1);
            if (Math.random() > .5) {
              schedule.get(j).set(z, i);
              schedule.get(z).set(j, i + numTeams - 1);
            } else {
              schedule.get(z).set(j, i);
              schedule.get(j).set(z, i + numTeams - 1);
            }
          }
        }
      }
    }


  }

  public boolean canPlay(int week, int team) {
    for (int i = 0; i < numTeams; i++) {
      if (schedule.get(i).get(team) == week) {
        return false;
      }
    }
    return !schedule.get(team).contains(week);
  }

  public boolean isValidBoard() {
    for (int i = 0; i < numTeams; i++) {
      for (int j = 0; j < numTeams; j++) {
        for (int z = 0; z < numTeams; z++) {
          if (z != j && z != i && i != j && schedule.get(z).get(i) == schedule.get(i).get(j)
              && schedule.get(z).get(i) != 0) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public void printBoard() {

    for (int i = 0; i < numTeams; i++) {
      for (int j = 0; j < numTeams; j++) {
        String ret = schedule.get(i).get(j) + "";
        if (ret.length() == 1) {
          ret = " " + ret;
        }
        if (i == j) {
          ret = " X";
        }
        System.out.print("[" + ret + "]");
      }
      System.out.println();
    }
  }

  public int getOpponent(int team, int week) {
    return schedule.get(team).indexOf(week);
  }
}
