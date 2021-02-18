package Save;

import League.League;
import LeagueSystem.LeagueSystem;

import java.io.IOException;

public class SaveTest {

  public static void main(String[] args) throws IOException {
    LeagueSystem sys = new LeagueSystem();
    sys.simulateSystem();
    sys.endSeason();
    sys.simulateSystem();
    SaveGame save = new SaveGame(sys);
    save.save();
  }


}
