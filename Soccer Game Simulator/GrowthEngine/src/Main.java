import java.util.ArrayList;
import java.util.Scanner;
public class Main {


    public static void main(String[] args) {
        GameControl gc = new GameControl();
        gc.playGame();
    }
}

class Test {

    Test(){}

    void testQuickPlay () {
        int numSims = 10000;
        ArrayList<String> data = new ArrayList<String>();
        for (int rating = 1; rating < 4; rating++) {
            double hW = 0, d = 0, aW = 0;
            for (int i = 0; i < numSims; i++) {
                Match match = new Match(new NPCTeam(rating), new NPCTeam(1));
                match.quickPlay();
                if (match.homeScore > match.awayScore) {
                    hW++;
                } else if (match.awayScore > match.homeScore) {
                    aW++;
                } else {
                    d++;
                }
            }
            double wP = (hW / numSims) * 100;
            double dP = d / numSims * 100;
            double lP = aW / numSims * 100;
            data.add((rating) + ": " + wP + "% WR || " + dP + "% DR || " + lP + "% LR");
        }
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }
    }

    void testGetResults() {
        for (int i = 0; i < 10; i++) {
            Match match = new Match();
            match.quickPlay();
            match.presentResult();
        }
    }

    void testResultVariation(int home, int away) {
        for (int i = 0; i < 10; i++) {
            Match match = new Match(new NPCTeam("" + home, home), new NPCTeam("" + away, away));
            match.quickPlay();
            match.presentResult();
        }
    }

    void testPlayGame() {
        Match match = new Match();
        match.play();
    }

    void testMakePlayerTeam() {
        PlayerTeam team = new PlayerTeam();
        team.sortTeam();
        team.presentTeam();
    }

    void testGame(int diff) {
        int w=0, l=0, d=0;
        double wp, lp, dp;
        int gs=0, ga=0;
        int games = 10000;
        for (int i = 0; i < games; i++) {
            Match match = new Match(new NPCTeam(50 + diff), new NPCTeam(50));
            match.quickPlay();
            gs+=match.homeScore;
            ga+=match.awayScore;
            if (match.homeScore > match.awayScore) {
                w++;
            } else if (match.homeScore == match.awayScore) {
                d++;
            } else {
                l++;
            }
        }
        wp = (double)(w)/ games * 100;
        dp = (double)(d)/games * 100;
        lp = (double)(l)/games * 100;

        System.out.println(wp + "%W " + dp  +  "%D " + lp + "%L");
        System.out.println(gs + " scored total, " + ga + " against total.");
        System.out.println((double)(gs) / games + " - " + (double)(ga) / games);
    }

    void testMakeLeague(){
        League league = new League();
        league.simulateSeason();
        league.presentLeague();
    }

    void testMakeSchedule() {
        League league = new League();
        Schedule schedule = new Schedule(league.teams);
        schedule.printSchedule();
    }

    void testGameWeek() {
        League league = new League();
        for (int i = 0; i < 11; i++) {
            league.simulateGameWeek();
            System.out.println();
        }
    }

    void testPresentPyramid() {
        Pyramid pyramid = new Pyramid();
        pyramid.presentAllPlayers();

    }

    void testGetRandomGoalScorer() {
        Team team = new NPCTeam(80);
        team.presentTeam();
        for (int i = 0; i < 100; i++) {
            System.out.println(team.getRandomGoalscorer2());
        }
        for (int i = 0; i < team.players.size(); i++) {
            Player player = team.players.get(i);
            System.out.println(player.toString() + " " + player.goals);
        }

    }

}


