import java.util.ArrayList;

public class GameArea {

    ArrayList<Player> players = new ArrayList<Player>();
    boolean isTop, isBottom;
    int x;
    Match match;

    GameArea(int x, Match match) {
        this.x = x;
        this.match = match;
    }

    void print() {
        System.out.print("[");
        System.out.print(this.printPlayers());
        System.out.print("]");
    }

    String printPlayers() {
        String toPrint = "";
        for (int i = 0; i < players.size(); i++) {
            toPrint+=players.get(i).printPlayer();
        }
        int num = toPrint.length();
        for (int i = 0; i < 22 - num; i++) {
            toPrint += " ";
        }
        return toPrint;
    }

    double desirability(Team team, Player player) {
        if (players.contains(player)) {
            if (numberOfPlayersOnTeam(team) == 1) {
                return 0;
            }
        }
        double passChance = (double)(numberOfPlayersOnTeam(team)) / this.players.size();
        passChance += (Math.abs(this.x - player.x)) * -.2;
        return passChance;
    }

    int numberOfPlayersOnTeam(Team team) {
        int count = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).team.equals(team)) {
                count++;
            }
        }
        return count;
    }

    int numberOfPlayersNotOnTeam(Team team) {
        return players.size() - this.numberOfPlayersOnTeam(team);
    }

    void passTo(Team team, Player player, int loc) {
        ArrayList<Player> toReceive = new ArrayList<Player>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).team.equals(team) && player != players.get(i)) {
                toReceive.add(players.get(i));
            }
        }
        if (numberOfPlayersNotOnTeam(team) == 0) {
            Player receiving = toReceive.get((int) (Math.random() * toReceive.size()));
            receiving.hasBall = true;
            receiving.justReceived = true;
        } else {
            ArrayList<Player> toDefend= new ArrayList<Player>();
            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).team.equals(team)) {
                    toDefend.add(players.get(i));
                }
            }
            Player defending = toDefend.get((int)(Math.random() * toDefend.size()));
            double choice = Math.random();
            if (choice < .5 + player.PAS * 0.05) {

            } else {
                this.looseBall();
            }

        }
    }

    boolean hasBall() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).hasBall) {
                return true;
            }
        }
        return false;
    }

    void looseBall() {
        Player player = this.players.get((int)(Math.random() * players.size()));
        player.hasBall = true;
        player.justReceived = true;
        if (player.team.equals(match.homeTeam)) {
            match.isHome = true;
        } else {
            match.isHome = false;
        }
    }


}
