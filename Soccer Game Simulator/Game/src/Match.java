public abstract class Match {
    Team team1, team2;

    Match(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

}

class NPCMatch extends Match {

    NPCMatch(NPCTeam team1, NPCTeam team2) {
        super(team1, team2);
    }


}

class PlayerMatch extends Match {

    PlayerMatch(NPCTeam team1, PlayerTeam team2) {
        super(team1, team2);
    }
    PlayerMatch(PlayerTeam team1, NPCTeam team2) {
        super(team1, team2);
    }

}