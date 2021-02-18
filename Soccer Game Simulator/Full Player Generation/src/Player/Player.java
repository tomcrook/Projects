package Player;

import Generator.PlayerGenerator;
import java.io.IOException;


public class Player {

  PlayerGenerator playerGenerator;
  String nation;

  String name;
  String position;

  int pos;

  int xG = 0;
  int xA = 0;

  int OVR;
  int POT;

  public PlayerGenerator getPlayerGenerator() {
    return playerGenerator;
  }

  public String getNation() {
    return nation;
  }

  public String getName() {
    return name;
  }

  public String getPosition() {
    return position;
  }

  public int getPos() {
    return pos;
  }

  public int getxG() {
    return xG;
  }

  public int getxA() {
    return xA;
  }

  public int getOVR() {
    return OVR;
  }

  public int getPOT() {
    return POT;
  }

  public double getPotSD() {
    return potSD;
  }

  public int getAge() {
    return age;
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

  public double getForm() {
    return form;
  }

  double potSD;
  int age;
  int PAC, SHO, PAS, DRI, DEF, PHY;

  // value from 0 - 10 //
  double form;


  public Player(int rating, int pos, PlayerGenerator playerGenerator, int age, String nation)  {
    this.nation = nation;
    this.playerGenerator = playerGenerator;
    this.pos = pos;
    this.position = playerGenerator.generatePosition(pos);
    this.name = playerGenerator.generateName(nation);
    this.age = age;
    this.OVR = rating;
    String tempPos = position;
    if (position.equals("GK")) {
      tempPos = "CB";
    }

    try {
      MakePlayer maker = new MakePlayer(OVR, age, tempPos);
      this.PAC = maker.getPAC();
      this.SHO = maker.getSHO();
      this.PAS = maker.getPAS();
      this.DRI = maker.getDRI();
      this.DEF = maker.getDEF();
      this.PHY = maker.getPHY();
      this.POT = maker.getPOT();
      this.potSD = maker.potSD;
    } catch (Exception e) {
      throw new Error("MakePlayer failed.");
    }

  }

  public Player(int rating, int pos, PlayerGenerator playerGenerator, int age) {
    this(rating, pos, playerGenerator, age, playerGenerator.getRandomNation());
  }

  public void endSeason() {
    int tempOVR = OVR;
    this.POT = MakePlayer.generatePOT(age, OVR, POT, potSD);
    this.OVR    = MakePlayer.generateNewRating(tempOVR, POT, 6, age);
    double mod = MakePlayer.statGrowthModifier(tempOVR, POT, 6, age);
    this.PAC = (int)(PAC * mod);
    this.SHO = (int)(SHO * mod);
    this.PAS = (int)(PAS * mod);
    this.DRI = (int)(DRI * mod);
    this.DEF = (int)(DEF * mod);
    this.PHY = (int)(PHY * mod);
    if (this.OVR > 99) { this.OVR = 99; }
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

    this.age++;
//    try {
//      this.POT = MakePlayer.generatePOT(this.age, this.OVR, this.POT, this.potSD);
//    } catch (Exception e) {
//
//    }
  }

  @Override
  public String toString() {
    String title = this.name + "\n(" + this.position + ") " + this.nation;
    String genStats = this.OVR + " OVR " + this.POT + " POT" + "\n-------------";
    String stats = this.PAC + " PAC " + this.DRI + " DRI\n";
    stats += this.SHO + " SHO " + this.DEF + " DEF\n";
    stats += this.PAS + " PAS " + this.PHY + " PHY";
    return title + "\n" + genStats + "\n" + stats + "\n";
  }

  public void calculateXG(int leagueAvg, int leagueSD) {
    double[] meanG = {
      0, 0.038, 0.107, 0.373
    };
    double[] sdG = {
      0.002, 0.069, 0.126, 0.297
    };
    if (OVR < leagueAvg) {
      this.xG = (int) (38 * (meanG[pos] + ((OVR - leagueAvg) / leagueSD) * (sdG[pos] / 2)));
    } else {
      this.xG = (int) (38 * (meanG[pos] + ((OVR - leagueAvg) / leagueSD) * sdG[pos]));
    }
    if (xG < 0) {
      this.xG = 0;
    }
  }

  public void calculateXA(int leagueAvg, int leagueSD) {
    double[] meanG = {
      0.000,
      0.033,
      0.102,
      0.127
    };
    double[] sdG = {
      0.003,
      0.058,
      0.139,
      0.236
    };
    if (OVR < leagueAvg) {
      this.xA = (int) (38 * (meanG[pos] + ((OVR - leagueAvg) / leagueSD) * (sdG[pos] / 2)));
    } else {
      this.xA = (int) (38 * (meanG[pos] + ((OVR - leagueAvg) / leagueSD) * sdG[pos]));
    }
    if (xA < 0) {
      this.xA = 0;
    }
  }

  public String teamView() {
    return "(" + OVR + " OVR | " + POT +" POT | " + age + " Y/O) " + name + ", " + nation;
  }

  public void setPosition(String position) {
    this.position = position;
  }
}
