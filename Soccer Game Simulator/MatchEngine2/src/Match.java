import java.util.ArrayList;

public class Match {

    Team homeTeam, awayTeam;
    int minute;
    int homeScore, awayScore;

    Match() {
        this.homeTeam = new NPCTeam(75);
        this.awayTeam = new NPCTeam(70);
        this.minute = 0;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.minute = 0;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    /////// Start/End Game /////////



}

