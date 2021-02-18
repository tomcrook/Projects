package Generator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlayerGenerator implements Generator {

  String[] defPositions = {
    "GK", "RB", "CB", "LB"
  };

  String[] midPositions = {
    "CDM", "CAM", "CM", "RM", "LM",
  };

  String[] attPositions = {
    "RW", "LW", "ST"
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

  @Override
  public String generateName() {
    return generateName("");
  }

  public String getRandomNation() {
    return this.nations.get((int)(nations.size() * Math.random())).name;
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

  public String generatePosition(int pos){
    if (pos == 1) {
      return this.defPositions[(int)(Math.random() * defPositions.length)];
    } else if (pos == 2) {
      return this.midPositions[(int)(Math.random() * midPositions.length)];
    } else {
      return this.attPositions[(int)(Math.random() * attPositions.length)];
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
