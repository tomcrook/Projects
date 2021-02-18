
public abstract class Team {

    int teamAverage;
    String name;

    Team(String name, int teamAverage) {
        this.name = name;
        this.teamAverage = teamAverage;
    }

    public int getTeamAverage() {
        return teamAverage;
    }

    public String getName() {
        return name;
    }

    int isXBetterThan(Team other) {
        return this.teamAverage - other.getTeamAverage();
    }

}

class NPCTeam extends Team {

    NPCTeam(String name) {
        super(name, 50);
    }
    NPCTeam(String name, int teamAverage) {
        super(name, teamAverage);
    }


}

class PlayerTeam extends Team {

    int form;
    Player[] teamSheet;

    PlayerTeam(String name, int teamAverage) {
        super(name, teamAverage);
        this.form = 0;
    }
    PlayerTeam() {
        super("Player Team", 50);
        this.form = 0;
    }

    void generateTeamSheet() {
        NameGenerator nameGenerator = new NameGenerator();
        for (int i = 0; i < 15; i++) {
            int diff = (int)(Math.random() * 11) - 5;
            int age = (int)(Math.random() * 20) + 16;
            int potential = this.teamAverage + (36 - age);
            teamSheet[i] = new Player(nameGenerator.generateName(), 50 + diff, age, potential);
        }
    }

    void getTeamSheet() {
        for (int i = 0; i < teamSheet.length; i++) {
            System.out.println(teamSheet[i].toString());
        }
    }



}

