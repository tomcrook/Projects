package Generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlayerGenerator implements Generator {

  String[] positions = {
    "GK", "RB", "CB", "LB", "CM", "RM", "LM", "ST"
  };

  ArrayList<Nation> nations;

  Random r = new Random();

  public PlayerGenerator() {
    File folder = new File("finalNames");
    File[] files = folder.listFiles();
    nations = new ArrayList<Nation>();
    for (File f : files) {
      String n = f.getName();
      if (n.contains("FN.txt")) {
        String nation = n.substring(0, n.indexOf("FN.txt"));
        try {
          nations.add(new Nation(nation, "finalNames/" + nation + "FN.txt", "finalNames/" + nation + "LN.txt"));
        } catch (Exception e) {

        }
      }
    }

  }

  public String generateName(String nation) {
    Nation nat = null;
    for (Nation n : nations) {
      if (n.find(nation)) {
        nat = n;
        break;
      }
    }
    if (nat == null) {
      throw new IllegalArgumentException(nation + " is not a valid nation");
    }
    return nat.generateName();
  }

  public String generateName() {
    return generateName("England");
  }


  public String generatePosition(int pos){
    if (pos == 1) {
      return this.positions[(int)(Math.random() * 4)];
    } else if (pos == 2) {
      return this.positions[(int)(Math.random() * 3) + 4];
    } else {
      return "ST";
    }
  }

  /**
   * Generates a random stat rating, following a Gaussian distribution
   * @param average : Average for distribution
   * @param d : standard deviation
   */
  public int generateRating(int average, double d) {
    int ret = (int) ((r.nextGaussian() * d) + average);
    if (ret > 99) {
      return 99;
    } else if (ret < 1) {
      return 1;
    } else {
      return ret;
    }
  }

  /**
   * Generates a random stat rating, in upper 25th percentile of a Gaussian distribution
   * @param average : Average for distribution
   * @param d : standard deviation
   */
  public int generateBestRating(int average, double d) {
    double g = 0;
    while (g < 0.67) {
      g = r.nextGaussian();
    }
    int ret = (int) ((g * d) + average);
    if (ret > 99) {
      return 99;
    } else if (ret < 1) {
      return 1;
    } else {
      return ret;
    }
  }


  public ArrayList<String> generateSecondary(boolean a) {
    ArrayList<String> def = new ArrayList<String>(Arrays.asList("CB", "RB", "LB", "CM"));
    ArrayList<String> att = new ArrayList<String>(Arrays.asList("ST", "CM", "RM", "LM"));
    int num = 0;
    if (a) {
      num = (int)(att.size() * Math.random()) + 1;
      for (int i = 0; i < num; i++) {
        att.remove((int)(att.size() * Math.random()));
      }
      return att;
    } else {
      num = (int)(def.size() * Math.random()) + 1;
      for (int i = 0; i < num; i++) {
        def.remove((int)(def.size() * Math.random()));
      }
      return def;
    }
  }

  public int abilityAtPosition(String pos1, String pos2, int OVR) {
    ArrayList<String> def = new ArrayList<String>(Arrays.asList("CB", "RB", "LB", "CM"));
    ArrayList<String> att = new ArrayList<String>(Arrays.asList("ST", "CM", "RM", "LM"));
    if ((def.contains(pos1) && def.contains(pos2)) || (att.contains(pos1) && att.contains(pos2))) {
      return 6 * OVR / 10;
    } else {
      return OVR / 4;
    }
  }

}
