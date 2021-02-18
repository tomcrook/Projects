import java.util.ArrayList;
import java.util.Scanner;

abstract class Team {
    NameGenerator nameGenerator = new NameGenerator();
    int teamAverage;
    String stadiumName;
    String name;
    int points = 0, goalDifference = 0, gFor = 0, gAgainst = 0, gamesPlayed = 0, wins = 0, draws = 0, losses = 0;
    double form = 0;
    int titles = 0;
    int startingTeamAverage;
    String[] last5 = {
            "", "", "", "", "", ""
    };
    int highest = 9999999, lowest = 0;
    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<Player> startingXI = new ArrayList<Player>(11);
    ArrayList<Player> subs = new ArrayList<Player>();
    String[] formation = {
            "GK", "LB", "CB", "CB", "RB", "LM", "CM", "CM", "RM", "ST", "ST"
    };
    int DEF, MID, ATT;
    boolean hasBall = false;

    Team(String name) {
        this.name = name;
        this.teamAverage = 40 + (int) (Math.random() * 10);
        this.startingTeamAverage = teamAverage;
        this.stadiumName = nameGenerator.generateStadiumName();
        this.createTeam();
        this.updateTeamAverage();
        this.autoPickXI();
    }

    Team(String name, int teamAverage) {
        this.name = name;
        this.teamAverage = teamAverage + 1 + (int) (Math.random() * 9);
        this.stadiumName = nameGenerator.generateStadiumName();
        this.createTeam();
        this.updateTeamAverage();
        this.autoPickXI();
        this.startingTeamAverage = this.teamAverage;
    }

    Team(int teamAverage) {
        this.teamAverage = teamAverage + 1 + (int) (Math.random() * 9);
        this.name = nameGenerator.generateTeamName();
        this.stadiumName = nameGenerator.generateStadiumName();
        this.createTeam();
        this.updateTeamAverage();
        this.autoPickXI();
        this.startingTeamAverage = this.teamAverage;
    }

    void createTeam() {
        players.clear();
        NameGenerator nameGenerator = new NameGenerator();
        String[] positions = {
                "GK", "RB", "CB", "CB", "LB", "CM", "CM", "RM", "LM", "ST", "ST"
        };
        for (int i = 0; i < 16; i++) {
            int age = 17 + (int) (Math.random() * 20);
            int overall = 0;
            if (teamAverage > 50) {
                overall = (this.teamAverage - 10) + (int) (Math.random() * 21);
            } else if (teamAverage > 10) {
                overall = (this.teamAverage - 5) + (int) (Math.random() * 11);
            } else {
                overall = (this.teamAverage - 1) + (int) (Math.random() * 3);
            }
            int potential = overall + (int) (Math.random() * 25);
            if (potential > 99) {
                potential = 99;
            }
            if (overall < 1) {
                overall = 1;
            }
            String position = "";
            if (i < 11) {
                position = positions[i];
            } else {
                position = nameGenerator.getPosition();
            }
            players.add(new Player(nameGenerator.generateName(), age, overall, potential, position, this));
        }
        this.updateTeamAverage();
    }

    void updateTeamAverage() {
        int sum = 0;
        for (int i = 0; i < players.size(); i++) {
            sum += players.get(i).overall;
        }
        this.teamAverage = (int) (sum / players.size());

        sum = 0;

        for (int i = 0; i < 5; i++) {
            sum += players.get(i).overall;
        }

        this.DEF = (int) (sum / 5);

        sum = 0;
        for (int i = 5; i < 9; i++) {
            sum += players.get(i).overall;
        }

        this.MID = (int) (sum / 4);

        sum = 0;
        for (int i = 9; i < 11; i++) {
            sum += players.get(i).overall;
        }

        this.ATT = (int) (sum / 2);
    }

    void autoPickXI() {
        for (int i = 0; i < formation.length; i++) {
            Player playerPicked = new Player("Not Available", 0, 0, 0, "N/A", this);
            startingXI.add(playerPicked);
            for (int p = 0; p < players.size(); p++) {
                if (players.get(p).position.equals(formation[i]) &&
                        playerPicked.overall <= players.get(p).overall &&
                        !startingXI.contains(players.get(p))) {
                    this.startingXI.set(startingXI.indexOf(playerPicked), players.get(p));
                    if (i == formation.length - 1 && !playerPicked.name.equals("Not Available")) {
                        this.subs.add(playerPicked);
                    }
                    playerPicked = players.get(p);
                } else if (i == formation.length - 1 &&
                        !startingXI.contains(players.get(p))) {
                    this.subs.add(players.get(p));
                }
            }
        }
    }

    void kickOffPositions(boolean hasBall, boolean isHome, GameArea[] pitch) {
        this.hasBall = hasBall;
        if (isHome) {
            this.startingXI.get(0).setPosition(0, pitch, true);
            for (int i = 0; i < 4; i++) {
                this.startingXI.get(i+1).setPosition(1, pitch, true);
            }
            for (int i = 0; i < 4; i++) {
                this.startingXI.get(i+5).setPosition(2, pitch, true);
            }
            this.startingXI.get(9).setPosition(3, pitch, true);
            if (hasBall) {
                this.startingXI.get(10).setPosition(4, pitch, true);
                this.startingXI.get(10).changeHasBall();
            } else {
                this.startingXI.get(10).setPosition(3, pitch, true);
            }
        } else {
            this.startingXI.get(0).setPosition(8, pitch, false);
            for (int i = 0; i < 4; i++) {
                this.startingXI.get(i+1).setPosition(7, pitch, false);
            }
            for (int i = 0; i < 4; i++) {
                this.startingXI.get(i+5).setPosition(6, pitch, false);
            }
            this.startingXI.get(9).setPosition(5, pitch, false);
            if (hasBall) {
                this.startingXI.get(10).setPosition(4, pitch, false);
                this.startingXI.get(10).changeHasBall();
            } else {
                this.startingXI.get(10).setPosition(5, pitch, false);
            }
        }
        this.startingXI.get(0).gk = true;
        this.startingXI.get(1).def = true;
        this.startingXI.get(2).def = true;
        this.startingXI.get(3).def = true;
        this.startingXI.get(4).def = true;
        this.startingXI.get(5).mid = true;
        this.startingXI.get(6).mid = true;
        this.startingXI.get(7).mid = true;
        this.startingXI.get(8).mid = true;
        this.startingXI.get(9).att = true;
        this.startingXI.get(10).att = true;
    }

    void makeDecision() {
        for (int i = 0; i < startingXI.size(); i++) {
            startingXI.get(i).makeDecision();
        }
    }


}

class PlayerTeam extends Team {


    PlayerTeam(String name) {
        super(name, 50);
        this.createTeam();
    }
    PlayerTeam() {
        this("");
        this.name = nameGenerator.generateTeamName();
    }

}
class NPCTeam extends Team {

    NPCTeam(String name) {
        super(name);
    }
    NPCTeam() {
        this("");
        this.name = nameGenerator.generateTeamName();
    }
    NPCTeam(int teamAverage) {
        super(teamAverage);
    }
    NPCTeam(String name, int teamAverage) {
        super(name, teamAverage);
    }
}
