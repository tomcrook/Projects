import java.util.ArrayList;
import java.util.Scanner;

public class League {

    NameGenerator nameGenerator;
    ArrayList<Team> teams = new ArrayList<Team>();
    String title;
    Schedule schedule;
    int week;
    int season;
    Team[] relegated;
    Team[] promoted;
    int leagueRank;
    int numTeams = 20;

    League() {
        this.title = "Premier Division";
        this.teams = this.populateLeague(numTeams, 3);
        this.week = 0;
        this.season = 1;
        this.leagueRank = 0;
    }

    League(int leagueRank) {
        this.title = "Division " + (1 + leagueRank);
        this.teams = this.populateLeague(numTeams, leagueRank);
        this.week = 0;
        this.season = 1;
        this.leagueRank = leagueRank;
    }

    League(String name) {
        this.title = name;
        this.teams = this.populateLeague(numTeams, 3);
        this.week = 0;
        this.season = 1;
    }

    void play() {
        while (true) {
            this.week = 0;

            while (week < (this.teams.size() - 1) * 2) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Season " + season + ":");
                System.out.println("Week " + week + ":");
                System.out.println("1) View league");
                System.out.println("2) View schedule");
                System.out.println("3) Simulate Games");
                System.out.println("4) Simulate Season");
                int choice = scan.nextInt();

                switch (choice) {
                    case 1: {
                        this.presentLeague();
                        break;
                    }
                    case 2: {
                        this.schedule.printSchedule();
                        break;
                    }
                    case 3: {
                        this.simulateGameWeek();
                        break;
                    }
                    case 4: {
                        this.simulateSeason();
                        week = this.teams.size() * 2;
                        break;
                    }
                }
                System.out.println("----------------------");
            }
            this.presentLeague();
            this.endSeason();
            this.season++;
        }
    }

    Team[] getTop3() {
        Team[] top3 = new Team[3];
        for (int i = 0; i < 3; i++) {
            top3[i] = this.teams.get(i);
        }
        return top3;
    }

    Team[] getBottom3() {
        Team[] bot3 = new Team[3];
        int count = 0;
        for (int i = teams.size() - 1; i > teams.size() - 4; i--) {
            bot3[count] = this.teams.get(i);
            count++;
        }
        return bot3;
    }

    void addTeam(Team team) {
        this.teams.add(team);
    }

    void addTitles() {
        teams.get(0).titles++;
    }

    void removeTeam(Team team) {
        this.teams.remove(team);
    }

    void setPromoted() {
        relegated = this.getBottom3();
        promoted = this.getTop3();
    }

    void endSeason() {
        this.week = 0;
        this.season++;
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).endSeason(leagueRank);
        }
    }

    // add 'num' teams to the league
    ArrayList<Team> populateLeague(int num, int averageModifier) {
        ArrayList<Team> t = new ArrayList<Team>();
        for (int i = 0; i < num; i++)
            t.add(new NPCTeam(80 - 10 * averageModifier));
        this.schedule = new Schedule(t);
        return t;
    }

    void presentLeague() {
        this.sortLeague();
        System.out.println(this.title + ":");
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).leaguePresent(i + 1);
        }
    }

    void simulateSeason() {
        for (int i = week; i < (teams.size() - 1) * 2; i++) {
            this.simulateGameWeek2();
        }

    }

    void sortLeague() {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                if (teams.get(i).points < teams.get(j).points) {
                    this.swap(i, j);
                }
            }
        }
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                if (teams.get(i).points == teams.get(j).points
                        && teams.get(i).goalDifference < teams.get(j).goalDifference) {
                    this.swap(i, j);
                }
            }
        }

    }

    void swap(int first, int second) {
        Team team = teams.get(first);
        teams.set(first, teams.get(second));
        teams.set(second, team);
    }

    void simulateGameWeek2() {
        week++;
        for (int i = 0; i < teams.size(); i++) {
            if (schedule.contains(i, week)) {
                Match game = this.schedule.getMatch(i, week);
                game.quickPlay();
            }
        }
    }

    void presentAllPlayers() {
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).presentTeam();
            System.out.println("_______________________________________");
        }
    }

    void simulateGameWeek() {
        week++;
        for (int i = 0; i < teams.size(); i++) {
            if (schedule.contains(i, week)) {
                Match game = this.schedule.getMatch(i, week);
                game.quickPlay();
                game.presentResult();
            }
        }

    }

    void leaguePrediction() {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                if (teams.get(i).teamAverage + teams.get(i).form < teams.get(j).teamAverage + teams.get(j).form) {
                    this.swap(i, j);
                }
            }
        }
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                if (teams.get(i).points == teams.get(j).points
                        && teams.get(i).points < teams.get(j).points) {
                    this.swap(i, j);
                }
            }
        }
        System.out.println(this.title + ":");
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).leaguePresent(i + 1);
        }
    }

    void seeNextMatch() {
        for (int i = 0; i < teams.size(); i++) {
            if (schedule.contains(i, week + 1)) {
                Match game = this.schedule.getMatch(i, week + 1);
                game.pregameAnnouncement();
            }
        }
    }
}
