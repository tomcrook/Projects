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
            "","","","","",""
    };
    int highest = 9999999, lowest = 0;
    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<Player> startingXI = new ArrayList<Player>(11);
    ArrayList<Player> subs = new ArrayList<Player>();
    String[] formation  = {
            "GK", "LB", "CB", "CB", "RB", "LM", "CM", "CM", "RM", "ST", "ST"
    };
    int DEF, MID, ATT;


    Team(String name) {
        this.name = name;
        this.teamAverage = 40 + (int)(Math.random() * 10);
        this.startingTeamAverage = teamAverage;
        this.stadiumName = nameGenerator.generateStadiumName();
        this.createTeam();
        this.updateTeamAverage();
        this.autoPickXI();
    }
    Team(String name, int teamAverage) {
        this.name = name;
        this.teamAverage =  teamAverage + 1 + (int)(Math.random() * 9);
        this.stadiumName = nameGenerator.generateStadiumName();
        this.createTeam();
        this.updateTeamAverage();
        this.autoPickXI();
        this.startingTeamAverage = this.teamAverage;
    }
    Team(int teamAverage) {
        this.teamAverage =  teamAverage + 1 + (int)(Math.random() * 9);
        this.name = nameGenerator.generateTeamName();
        this.stadiumName = nameGenerator.generateStadiumName();
        this.createTeam();
        this.updateTeamAverage();
        this.autoPickXI();
        this.startingTeamAverage = this.teamAverage;
    }

    int isXBetterThan(Team other) {
        return (this.teamAverage + (int)this.form) - (other.teamAverage + (int)(other.form));
    }
    void whenInterested(int num) {
        System.out.println((num+1) + ") " + this.name + " (" + teamAverage + " OVR)");
    }
    void leaguePresent(int position) {
        String toPresent = position + ". " + this.name;
        int num = 50 - toPresent.length();
        for (int i = 0; i < num; i++) {
            toPresent = toPresent + " ";
        }

        String stats = "|  " + this.points + "pts";
        num = 9 - stats.length();
        for (int i = 0; i < num; i++) {
            stats =  stats + " ";
        }
        String goalD = " (" + this.goalDifference + "/" + gFor + "/" + gAgainst + ")";
        num = 14-goalD.length();
        for (int i = 0; i < num; i ++) {
            goalD = goalD + " ";
        }
        System.out.print(toPresent + stats + goalD
                + "|   " + this.gamesPlayed + "GP" +
                "   |   " + wins + " - " + draws + " - " + losses);
        String last5games = "";
        for (int i = 0; i < 5; i++){
            last5games += last5[i];
        }
        System.out.println(" " + last5games);
    }
    void changeLast5(String result) {
        last5[5] = result;
        for (int i = 4; i > -1; i--) {
            this.swap(i, 5);
        }
    }
    void presentStats(int position) {
        String toPresent = position + ". " + this.name + " (" + teamAverage + ")" +
                " (" + (this.teamAverage - this.startingTeamAverage) + ")";
        int num = 50 - toPresent.length();
        for (int i = 0; i < num; i++) {
            toPresent = toPresent + " ";
        }
        System.out.print(toPresent);

        toPresent =  "Highest: " + highest + " Lowest: " + lowest + " || ";
        num = 30 - toPresent.length();
        for (int i = 0; i < num; i++) {
            toPresent = " " + toPresent;
        }
        System.out.print(toPresent);


        for (int i = 0; i < titles; i++) {
            System.out.print("X");
        }

        System.out.println();

    }
    void swap(int i, int j) {
        String s = last5[i];
        last5[i] = last5[j];
        last5[j] = s;
    }
    void changeHighLow(int finish) {
        if (this.lowest < finish) {
            this.lowest = finish;
        }
        if (this.highest > finish) {
            this.highest = finish;
        }
    }
    void changeForm(int gD) {
        form += ((double)(gD)/10);

        if (form > 5) {
            this.form = 5;
        }
        if (form < -5) {
            this.form = -5;
        }
    }
    void addWin() {
        this.points+=3;
        this.gamesPlayed++;
        this.wins++;
        this.changeLast5("W");
    }
    void addDraw(){
        this.points+=1;
        this.gamesPlayed++;
        this.draws++;
        this.changeLast5("D");
    }
    void addLoss() {
        this.gamesPlayed++;
        this.losses++;
        this.changeLast5("L");
    }
    void changeGoalDifference(int value) {
        this.goalDifference+=value;
        if (value > 0) {
            this.addWin();
        } else if (value == 0) {
            this.addDraw();
        } else {
            this.addLoss();
        }
        this.changeForm(value);
    }
    void endSeason(int leagueValue) {

        if (Math.random() > 0.01 * teamAverage) {
                this.teamAverage += (int)(Math.random() * 7);
        } else if (Math.random() < 0.01 * teamAverage) {
                this.teamAverage -= (int)(Math.random() * 7);
        } else {
                this.teamAverage += (int) (Math.floor(this.wins / 15))
                        - (int) (Math.floor(this.losses / 15));
        }
        if (leagueValue > 3) {
            leagueValue = 3;
        }
        teamAverage+= (2 - leagueValue);


        this.form = 0;
        this.last5 = new String[]{"","","","","",""};
        this.gAgainst = 0;
        this.gFor = 0;
        this.goalDifference = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.points = 0;
        this.gamesPlayed = 0;

        if (this.teamAverage < 2) {
            this.teamAverage = 1;
        }
        if (this.teamAverage > 99) {
            this.teamAverage = 99;
        }

    }

    PlayerTeam makePlayerTeam() {
        PlayerTeam pt = new PlayerTeam(this.name);
        pt.stadiumName = this.stadiumName;
        pt.teamAverage = this.teamAverage;
        pt.createTeam();
        pt.titles = this.titles;
        return pt;
    }

    void createTeam() {
        players.clear();
        NameGenerator nameGenerator = new NameGenerator();
        String[] positions = {
                "GK", "RB", "CB", "CB", "LB", "CM", "CM", "RM", "LM", "ST", "ST"
        };
        for (int i = 0; i < 16; i++) {
            int age = 17 + (int)(Math.random() * 20);
            int overall = 0;
            if (teamAverage > 50) {
                overall = (this.teamAverage - 10) + (int) (Math.random() * 21);
            } else if (teamAverage > 10) {
                overall = (this.teamAverage - 5) + (int) (Math.random() * 11);
            } else {
                overall = (this.teamAverage - 1) + (int) (Math.random() * 3);
            }
            int potential = overall + (int)(Math.random() * 25);
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
            players.add(new Player(nameGenerator.generateName(), age, overall, potential, position));
        }
        this.updateTeamAverage();
    }

    void updateTeamAverage() {
        int sum = 0;
        for (int i = 0; i < players.size(); i++) {
            sum+= players.get(i).overall;
        }
        this.teamAverage = (int) (sum / players.size());

        sum = 0;

        for (int i = 0; i < 5; i++) {
            sum+= players.get(i).overall;
        }

        this.DEF = (int) (sum / 5);
        sum = 0;
        for (int i = 5; i < 9; i++) {
            sum+= players.get(i).overall;
        }

        this.MID = (int) (sum / 4);
        sum = 0;
        for (int i = 9; i < 11; i++) {
            sum+= players.get(i).overall;
        }

        this.ATT = (int) (sum / 2);
    }

    void autoPickXI() {
        for (int i = 0; i < formation.length; i++) {
            Player playerPicked = new Player("Not Available", 0, 0, 0, "N/A");
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

    void updateRatings(int week) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).simulateWeek(week, (int)this.form);
        }
    }

    void presentTeam() {
        String toPresent = this.name;
        for (int j = 0; j < 40 - this.name.length(); j++)
            toPresent = " " + toPresent;
        System.out.println(toPresent);
        for (int i = 0; i < players.size(); i++) {
            if (i < 11) {
                System.out.print("(" + formation[i] + ")");
                System.out.println(startingXI.get(i).toString());
            } else {
                if (i == 11) {
                    System.out.println("------------------");
                }
                System.out.print("(SUB)");
                System.out.println(subs.get(i-11).toString());
            }
        }
    }

    Player getRandomGoalscorer() {
        double position = Math.random();
        if (position < .125) {
            return this.startingXI.get((int)(Math.random() * 4) + 1);
        } else if (position < .425) {
            return this.startingXI.get((int)(Math.random() * 4) + 5);
        } else {
            return this.startingXI.get((int)(Math.random() * 2) + 9);
        }
    }

    String getRandomGoalscorer2() {
        double position = Math.random();
        Player player;
        if (position < .1) {
            player = this.startingXI.get((int)(Math.random() * 4) + 1);
            player.goals++;
            return player.name + " " + player.position;
        } else if (position < .5) {
            player = this.startingXI.get((int)(Math.random() * 4) + 5);
            player.goals++;
            return player.name + " " + player.position;
        } else {
            if (Math.random() < .5 + (.025 * (this.startingXI.get(9).overall - this.ATT))) {
                player = this.startingXI.get(9);
            } else {
                player = this.startingXI.get(10);
            }
            player.goals++;
            return player.name + " " + player.position;
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

    void sortTeam() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j< players.size(); j++) {
                if (players.get(i).age > players.get(j).age) {
                    this.swap(i, j);
                }
            }
        }
    }

    void swap(int first, int second) {
        Player player = players.get(first);
        players.set(first, players.get(second));
        players.set(second, player);
    }

    void printSquadReport() {
        String toPresent = "Squad Report";
        for (int j = 0; j < 40 - this.name.length(); j++)
            toPresent = " " + toPresent;
        System.out.println(toPresent);
        for (int i = 0; i < players.size(); i++) {
            if (i < 11) {
                System.out.print(i+1 + ") ");
                System.out.print("(" + formation[i] + ")");
                System.out.println(startingXI.get(i).toString());
            } else {
                if (i == 11) {
                    System.out.println("------------------");
                }
                System.out.print(i+1 + ") ");
                System.out.print("(SUB)");
                System.out.println(subs.get(i-11).toString());
            }
        }
        this.updateTeamAverage();
        System.out.println("Team Average: " + this.teamAverage);
    }

    void teamManagement() {
        Scanner kb = new Scanner(System.in);
        this.printSquadReport();
        System.out.println("1) Change Lineup?");
        System.out.println("2) Leave");
        int choice = kb.nextInt();

        if (choice == 1) {
            while(true) {
                System.out.println("_________________________________");
                System.out.println("Enter 2 player's numbers to swap\n" +
                                "Enter 0 to end");
                int choice1 = kb.nextInt();
                if (choice1 == 0 || choice1 > this.players.size()) {
                    break;
                }
                System.out.println("Swap with:");
                int choice2 = kb.nextInt();
                if (choice2 == 0 || choice2 > this.players.size()) {
                    break;
                }
                this.teamManagementSwap(choice1, choice2);
                this.printSquadReport();

            }
        }

    }

    void teamManagementSwap(int i, int j) {
        Player p1;
        Player p2;
        if (i > 11) {
            p1 = this.subs.get(i - 12);
        } else {
            p1 = startingXI.get(i - 1);
        }
        if (j > 11) {
            p2 = this.subs.get(j - 12);
            this.subs.set(j - 12, p1);
        } else {
            p2 = startingXI.get(j - 1);
            this.startingXI.set(j - 1, p1);
        }
        if (i > 11) {
            this.subs.set(i - 12, p2);
        } else {
            this.startingXI.set(i - 1, p2);
        }


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
