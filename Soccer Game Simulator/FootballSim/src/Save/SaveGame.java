package Save;

import League.League;
import LeagueSystem.LeagueSystem;
import Player.Player;
import Team.Team;

import java.io.FileWriter;
import java.io.IOException;

public class SaveGame {

  FileWriter fw;
  LeagueSystem game = new LeagueSystem();

  public SaveGame(LeagueSystem game) throws IOException {

    this.game = game;
    this.fw = new FileWriter("game.csv");

  }

  public void save() throws IOException {
    for (int i = 0; i < game.system.size(); i++) {
      this.saveLeague(game.system.get(i));
    }
    fw.close();
  }

  private void saveLeague(League league) throws IOException {
    fw.append("league " + league.name + "\n");
    for (int i = 0; i < league.teams.size(); i++) {
      this.saveTeam(league.teams.get(i));
    }
    fw.append("end league" + league.name + "\n");
  }

  private void saveTeam(Team team) throws IOException {
    fw.append(team.save());
    for (int i = 0; i < team.roster.getTeam().size(); i++) {
      this.savePlayer(team.roster.getTeam().get(i));
    }
    fw.append("end team " + team.name + "\n");
  }

  private void savePlayer(Player player) throws IOException {
    fw.append("player ");
    fw.append("name " + player.getName() + "\n");
    fw.append("ovr " + player.getOVR() + "\n");
    fw.append("pos " + player.getPosition() + "\n");
    Player.PlayerType[] vals = Player.PlayerType.values();
    for (int i = 0; i < vals.length; i++) {
      fw.append(player.getStatString(vals[i]) + "\n");
    }
    fw.append("PAC " + player.getPAC() + "\n");
    fw.append("PHY " + player.getPHY() + "\n");
    fw.append("ATT " + player.getATT() + "\n");
    fw.append("DEF " + player.getDEF() + "\n");
    fw.append("ssnGoals " + player.seasonGoals + "\n");
    fw.append("cGoals " + player.careerGoals + "\n");
    fw.append("end player" + "\n");
  }

}
