package Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Position {

  HashMap<String, Double> positions;


  public Position(String position) {
    this.positions = new HashMap<>();
    this.assignPositions(position);
  }

  void assignPositions(String position) {

    ArrayList<String> defPositions = (ArrayList<String>) Arrays.asList("RB", "CB", "LB", "CDM");

    ArrayList<String> midPositions = (ArrayList<String>) Arrays.asList("CM", "RM", "LM");

    ArrayList<String> attPositions = (ArrayList<String>) Arrays.asList("RW", "LW", "ST", "CAM");


    if (position.equals("GK")) {
      positions.put(position, 1.0);
    } else {
      ArrayList<String> toIterate = new ArrayList<String>();
      if (defPositions.contains(position)) {
        toIterate = defPositions;
      } else if (midPositions.contains(position)) {
        toIterate = midPositions;
      } else {
        toIterate = attPositions;
      }

      for (String p : toIterate) {
        positions.put(p, 0.5 + (Math.random() / 2));
      }

      positions.replace(position, 1.0);

    }
  }

  double abilityAtPosition(String position) {
    if (positions.containsKey(position)) {
      return positions.get(position);
    } else {
      return 0;
    }
  }


}
