import java.util.ArrayList;

public class Match {

    Team homeTeam, awayTeam;
    int minute = 0, secondCount = 0;
    int homeScore = 0, awayScore = 0;
    boolean isHome = true;

    GameArea[] pitch = new GameArea[9];


    Match() {
        this.homeTeam = new NPCTeam(90);
        this.awayTeam = new NPCTeam(70);
        this.createPitch();
        this.kickOffPositions(isHome);
    }

    Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.createPitch();
        this.kickOffPositions(isHome);
    }

    void createPitch() {
        for (int i = 0; i < pitch.length; i++) {
            pitch[i] = new GameArea(i, this);
            if (i == 0) {
                pitch[i].isTop = true;
            }
            if (i == pitch.length - 1) {
                pitch[i].isBottom = true;
            }
        }
    }

    void printPitch() {
        for (int i = 0; i < pitch.length; i++) {
            pitch[i].print();
            System.out.println();
        }
        System.out.println(" ---------------------- ");
    }

    void kickOffPositions(boolean isHome) {
        this.homeTeam.kickOffPositions(isHome, true, pitch);
        this.awayTeam.kickOffPositions(!isHome, false, pitch);
        this.playRound();
    }

    void playRound() {
        this.homeTeam.makeDecision();
        this.awayTeam.makeDecision();
    }

}

