package Match;

import java.util.ArrayList;

public class Pitch {

  int height = 120/5;
  int width = 60/5;
  ArrayList<ArrayList<Area>> pitch = new ArrayList<>();


  public Pitch() {
    this.makePitch();
  }

  public void makePitch() {
    for (int i = 0; i < height; i++) {
      ArrayList<Area> row = new ArrayList<Area>();
      for (int j = 0; j < width; j++) {
        boolean home = true;
        boolean goalBox = false;
        if (i < height/2) {
          home = false;
        }
        if ((i > 19 || i < 4) && (j > 1 && j < 10)) {
          goalBox = true;
        }
        row.add(new Area(home, goalBox));
      }
      pitch.add(row);
    }
  }

  public String toString() {
    String ret = "";
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        ret += pitch.get(i).get(j).toString();
      }
      ret += "\n";
    }
    return ret;
  }

}
