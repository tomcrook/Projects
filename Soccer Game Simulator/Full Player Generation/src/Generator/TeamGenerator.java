package Generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TeamGenerator implements Generator {

  ArrayList<String> townNames;

  String[] teamSuffixes = {
    "United", "Athletic", "FC", "FC", "FC", "City", "FC", "FC", "FC", "United", "Union"
  };

  String[] teamPrefixes = {
    "South", "North", "West", "East"
  };

  String[] stadiums = {
    "Arena", "Stadium", "Lane"
  };

  String townName;
  String teamName;
  Random r = new Random();

  public TeamGenerator() {
    try {
      Scanner scan = new Scanner(Paths.get("townNames"));
      townNames = new ArrayList<String>();
      while(scan.hasNextLine()) {
        townNames.add(scan.nextLine());
      }
      this.townName = townNames.get((int) (Math.random() * townNames.size()));
      this.teamName = generateName();
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }
  }

  public String generateName() {
    if (teamName == null) {
      String loc = this.townName;
      String suff = "";
      boolean prefHuh = false;
      String pref = "";
      if (Math.random() > .8) {
        pref = teamPrefixes[(int) (Math.random() * teamPrefixes.length)] + " ";
        prefHuh = true;
      }
      if (Math.random() > .5 || !prefHuh) {
        suff = " " + teamSuffixes[(int) (Math.random() * teamSuffixes.length)];
      }
      if (Math.random() > .9) {
        pref = "" + pref;
      }
      return pref + loc + suff;
    }
    return teamName;
  }

  public String generateStadiumName() {
    Scanner scan = new Scanner(teamName);
    String init = "";
    while(scan.hasNext()) {
      String next = scan.next();
      if (next.equals("FC")) {
        init += next;
      } else {
        init += next.substring(0, 1);
      }
    }
    if (init.length() > 2  && Math.random() > .6) {
      return init + " " + stadiums[(int) (Math.random() * stadiums.length)];
    }
    return this.townName + " " + stadiums[(int) (Math.random() * stadiums.length)];
  }

  /**
   * Generates a random team rating, following a Gaussian distribution
   * @param average : Average for distribution
   * @param d : standard deviation
   */
  public int generateRating(int average, double d) {
    return(int) ((r.nextGaussian() * d) + average);
  }

}
