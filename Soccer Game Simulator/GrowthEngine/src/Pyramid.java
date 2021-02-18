import java.util.ArrayList;
import java.util.Scanner;

public class Pyramid {

    League[] leagues;
    String nation = "England";
    int season;
    int week;

    Pyramid() {
        this.leagues = this.makeLeagues(9);
        this.season = 0;
        this.week = 1;
    }

    void play(int numLeagues) {
        this.leagues = this.makeLeagues(numLeagues);
        int choice = 0;
        while (true) {
            this.week = 1;
            this.setWeek(week);
            this.setSeason(season);
            while (week < (this.leagues[0].teams.size() - 1) * 2) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Season " + season + ":");
                System.out.println("Week " + week + ":");
                System.out.println("1) View leagues");
                System.out.println("2) Simulate Games");
                System.out.println("3) Simulate Season");
                System.out.println("4) See Upcoming Matches");
                System.out.println("5) End Game");
                choice = scan.nextInt();
                if (choice == 5) {
                    break;
                }

                switch (choice) {
                    case 1: {
                        System.out.println("0) View All");
                        for (int i = 0; i < numLeagues; i++) {
                            System.out.println((i+1) +") View Division " + (i+1));
                        }
                        choice = scan.nextInt();
                        if (choice == 0) {
                            this.presentLeagues();
                        } else {
                            this.leagues[choice-1].presentLeague();
                        }
                        break;
                    }
                    case 2: {
                        this.simulateGameWeeks();
                        break;
                    }
                    case 3: {
                        this.simulateSeason();
                        break;
                    }
                    case 4: {
                        System.out.println("0) View All");
                        for (int i = 0; i < numLeagues; i++) {
                            System.out.println((i+1) +") View Division " + (i+1));
                        }
                        choice = scan.nextInt();
                        if (choice == 0) {
                            for (int i = 0; i < leagues.length; i++) {
                                leagues[i].seeNextMatch();
                            }
                        } else {
                            this.leagues[choice-1].seeNextMatch();
                        }
                        break;
                    }
                }
                System.out.println("----------------------");
            }
            if (choice == 5) {
                season = 1;
                week = 1;
                break;
            }
            this.endSeason();
        }
    }

    void printStats() {
        int count = 1;
        for (int i = 0; i < leagues.length; i++) {
            for (int j = 0; j < leagues[0].teams.size(); j++) {
                leagues[i].teams.get(j).presentStats(count);
                count++;
            }
        }
    }

    void updateStats() {
        int count = 1;
        for (int i = 0; i < leagues.length; i++) {
            for (int j = 0; j < leagues[0].teams.size(); j++) {
                leagues[i].teams.get(j).changeHighLow(count);
                count++;
            }
        }
    }

    void setSeason(int season) {
        for (int i = 0; i < leagues.length; i++) {
            leagues[i].season = season;
            leagues[i].schedule = new Schedule(leagues[i].teams);
        }
    }

    void setWeek(int week) {
        for (int i = 0; i < leagues.length; i++) {
            leagues[i].week = week;
        }
    }

    void presentLeagues() {
        for (int i = 0; i < leagues.length; i++) {
            leagues[i].presentLeague();
            System.out.println();
        }
    }

    void simulateSeason() {
        for (int i = 0; i < leagues.length; i++) {
            leagues[i].simulateSeason();
        }
        week = this.leagues[0].teams.size() * 2;
    }

    void simulateGameWeeks() {
        this.week++;
        for (int i = 0; i < leagues.length; i++) {
            leagues[i].simulateGameWeek();
            System.out.println();
        }
    }

    void simulateGameWeeksManager(int league) {
        this.week++;
        for (int i = 0; i < leagues.length; i++) {
            if (league == i) {
                leagues[i].simulateGameWeek();
            } else {
                leagues[i].simulateGameWeek2();
            }
        }
    }

    League[] makeLeagues(int numLeagues) {
        League[] l = new League[numLeagues];
        for (int i = 0; i < numLeagues; i++) {
            l[i] = new League(i);
        }
        return l;
    }

    void endSeason() {
        this.presentLeagues();
        this.updateStats();
        leagues[0].addTitles();
        for (int i = 0; i < leagues.length ; i++) {
            leagues[i].setPromoted();
        }
        for (int i = 0; i < leagues.length ; i++) {
            leagues[i].endSeason();
        }
        for (int i = 0; i < leagues.length - 1; i++) {
            Team[] toAdd = leagues[i+1].promoted;
            Team[] toRemove = leagues[i].relegated;
            for (int t = 0; t < 3; t++){
                leagues[i].addTeam(toAdd[t]);
                leagues[i+1].removeTeam(toAdd[t]);
            }
            for (int t = 0; t < 3; t++){
                leagues[i+1].addTeam(toRemove[t]);
                leagues[i].removeTeam(toRemove[t]);
            }
        }
        this.printStats();
        this.season++;
    }

    ArrayList<Team> getInterestedClubs(int num) {
        ArrayList<Team> teams = new ArrayList<Team>();
        for (int i = 0; i < leagues.length; i++) {
            for (int team = 0; team < leagues[i].teams.size(); team++) {
                if (leagues[i].teams.get(team).teamAverage <= num) {
                    teams.add(leagues[i].teams.get(team));
                }
            }
        }
        return teams;
    }

    void presentAllPlayers() {
        for (int i = 0 ; i < leagues.length; i++) {
            leagues[i].presentAllPlayers();
        }
    }

}