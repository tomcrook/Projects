package Player;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class MakePlayer {

  String POS;
  int age;
  int OVR;
  int POT;
  int PAC;
  int SHO;
  int PAS;
  int DRI;
  int DEF;
  int PHY;

  double potSD;

  double[] means = new double[6];
  double[] sd = new double[6];

  MakePlayer(int overall, int age, String pos)  {
    this.age = age;
    this.OVR = overall;
    this.POS = pos;
    try {
      Scanner scanner = new Scanner(Paths.get("positionData"));
      Scanner getPot = new Scanner(Paths.get("potential"));
      if (age > 40 || age < 16) {
        throw new IllegalArgumentException("Cannot have players over 40 or under 16.");
      }
      while (getPot.hasNext()) {
        double next = getPot.nextDouble();
        if (next == age) {
          break;
        }
      }
      this.POT = (int) (this.OVR * this.getPotential(getPot.nextDouble(), getPot.nextDouble()));
      if (this.POT > 99) {
        this.POT = 99;
      }
      if (this.POT < this.OVR) {
        this.POT = this.OVR;
      }
      this.assignData(scanner);
      this.assignRatings();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void assignData(Scanner scan) {
    while(scan.hasNext()) {
      if (scan.nextLine().contains(POS)) {
        break;
      }
    }
    for (int i = 0; i < 6; i++) {
      String next = scan.nextLine();
      means[i] = Double.parseDouble(next.substring(0, next.indexOf("\t")));
      next = next.substring(next.indexOf("\t") + 1);
      sd[i] = Double.parseDouble(next.substring(0, 5));
    }

  }

  void assignRatings() {
    Random r = new Random();
    double[] sds = new double[6];
    double sum = 0;
    for (int i = 0; i < sd.length - 1; i++) {
      sds[i] = r.nextGaussian();
      sum += sds[i];
    }
    while (sum < -2 || sum > 2) {
      sum = 0;
      for (int i = 0; i < sd.length - 1; i++) {
        sds[i] = r.nextGaussian();
        sum += sds[i];
      }
    }
    sds[5] = -1 * sum;
    this.PAC = (int) (this.OVR * getGaussian(means[0], sd[0], sds[0]));
    this.SHO = (int) (this.OVR * getGaussian(means[1], sd[1], sds[1]));
    this.PAS = (int) (this.OVR * getGaussian(means[2], sd[2], sds[2]));
    this.DRI = (int) (this.OVR * getGaussian(means[3], sd[3], sds[3]));
    this.DEF = (int) (this.OVR * getGaussian(means[4], sd[4], sds[4]));
    this.PHY = (int) (this.OVR * getGaussian(means[5], sd[5], sds[5]));
    if (this.PAC > 97) { this.PAC = 97; }
    if (this.SHO > 97) { this.SHO = 97; }
    if (this.PAS > 97) { this.PAS = 97; }
    if (this.DRI > 97) { this.DRI = 97; }
    if (this.DEF > 97) { this.DEF = 97; }
    if (this.PHY > 97) { this.PHY = 97; }
    if (this.PAC < 1) { this.PAC = 1; }
    if (this.SHO < 1) { this.SHO = 1; }
    if (this.PAS < 1) { this.PAS = 1; }
    if (this.DRI < 1) { this.DRI = 1; }
    if (this.DEF < 1) { this.DEF = 1; }
    if (this.PHY < 1) { this.PHY = 1; }
  }

  private static double getGaussian(double mean, double deviation) {
    Random r = new Random();
    return mean + r.nextGaussian() * deviation;
  }

  private static double getGaussian(double mean, double deviation, double r) {
    return mean + r * deviation;
  }

  private double getPotential(double mean, double deviation) {
    Random r = new Random();
    this.potSD = r.nextGaussian();
    return mean + potSD * deviation;
  }

  public int getPOT() {
    return POT;
  }

  public int getPAC() {
    return PAC;
  }

  public int getSHO() {
    return SHO;
  }

  public int getPAS() {
    return PAS;
  }

  public int getDRI() {
    return DRI;
  }

  public int getDEF() {
    return DEF;
  }

  public int getPHY() {
    return PHY;
  }

  static int generatePOT(int age, int OVR, int POT, double potSD) {
    int p = POT;
    try {
      Scanner getPot = new Scanner(Paths.get("potential"));
      if (age > 40 || age < 16) {
        throw new IllegalArgumentException("Cannot have players over 40 or under 16.");
      }
      while (getPot.hasNext()) {
        double next = getPot.nextDouble();
        if (next == age) {
          break;
        }
      }
      p = (int) (OVR * getPot.nextDouble() + potSD * getPot.nextDouble() + 0.5);
      if (p > 99) {
        p = 99;
      }
    } catch(Exception e) {

    }

    return Math.min(p, POT);
  }

  static int generateNewRating(int OVR, int POT, int form, int age) {
    // percent through development
    double p = 0.35 * (Math.pow(age - 30, 3)) + 100;
    double pLeft = 100 - p;
    double growth = 10 * (.1 * Math.pow((age - 30), 2));
    p = growth / pLeft;
    if (age >= 29) {
      OVR += (form - 6) / 10;
      return Math.max(OVR - (int) (OVR * ((.015  * Math.pow(age - 30, 2) + 0.05) / 10)) , 1);
    }
    OVR += (form - 6) / 2;
    return (int) ((POT - OVR) * p) + OVR;
  }

  static double statGrowthModifier(int OVR, int POT, int form, int age) {
    double ratingChange = generateNewRating(OVR, POT, form, age);
    return ratingChange / OVR;
  }

}
