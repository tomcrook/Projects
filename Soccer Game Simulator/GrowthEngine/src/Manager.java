import java.util.ArrayList;
import java.util.Scanner;

public class Manager {

    String firstName, lastName;
    int reputation;
    PlayerTeam team;
    Pyramid pyramid;
    Scanner kb = new Scanner(System.in);
    Coach assistantCoach;
    int division;
    int season = 0, week = 1;


    Manager(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reputation = 5;
        this.pyramid = new Pyramid();
        this.team = this.pickTeam();
    }

    void play() {
        System.out.println(this.team.name + " selected. \nTeam Sheet:");
        this.team.autoPickXI();
        this.team.presentTeam();
        System.out.println("--------------------------------------------------------");
        System.out.println("Now it is time to pick your assistant coach.\n" +
                "He will be responsible for finding you potential transfer targets,\n" +
                "help sell your players, and give analysis of our current squad.");
        this.pickAssistantCoach();

        while(true) {
            System.out.println("______ Season " + this.season + " : Week " + this.week + " ______");
            System.out.println("1) Team Management & Squad Report");
            System.out.println("2) View League Table");
            boolean seasonDone = false;
            if (this.week == 39) {
                System.out.println("3) End Season");
                seasonDone = true;
            } else {
                System.out.println("3) Simulate GameWeek");
            }
            System.out.print("");
            System.out.print("");
            int choice = kb.nextInt();

            switch(choice) {
                case 1: {
                    this.team.teamManagement();
                    break;
                }
                case 2: {
                    this.pyramid.leagues[division-1].presentLeague();
                    this.presentLeagueTable();
                    break;
                }
                case 3: {
                    if (seasonDone) {
                        pyramid.endSeason();
                        for (int i = week; i < 53; i++) {
                            this.team.updateRatings(i);
                            this.team.updateTeamAverage();
                        }
                        this.week = 0;
                        this.season++;
                    } else {
                        this.pyramid.simulateGameWeeksManager(this.division - 1);
                        this.team.updateRatings(week);
                        this.team.updateTeamAverage();
                        this.week++;
                    }
                    break;
                }
            }

        }
    }

    PlayerTeam pickTeam() {
        ArrayList<Team> interestedClubs = this.pyramid.getInterestedClubs(this.reputation);
        for (int i = 0; i < interestedClubs.size(); i++) {
            interestedClubs.get(i).whenInterested(i);
        }
        System.out.println("Which team would you like to join?");
        int choice = kb.nextInt();
        Team chosenTeam = interestedClubs.get(choice - 1);
        division = 9;
        return chosenTeam.makePlayerTeam();
    }

    void pickAssistantCoach() {
        ArrayList<Coach> coaches = new ArrayList<Coach>();
        for (int i = 0; i < 5; i++) {
            coaches.add(new Coach(this.reputation));
            System.out.println((i+1) + ") " +coaches.get(i).name + " (" + coaches.get(i).rating + ")");
        }
        int choice = kb.nextInt();
        this.assistantCoach = coaches.get(choice - 1);

        System.out.println(assistantCoach.name + " is your new assistant.");
    }

    void presentLeagueTable() {
        while(true) {
            System.out.println("________________________________________");
            System.out.println("1) League Prediction");
            System.out.println("2) View Other League Table");
            System.out.println("3) Leave");
            System.out.print("");
            System.out.print("");
            int choice = kb.nextInt();

            if (choice == 3) {
                break;
            }
            switch(choice) {
                case 1: {
                    this.pyramid.leagues[division-1].leaguePrediction();
                    break;
                }
                case 2: {
                    System.out.println("0) View All");
                    for (int i = 0; i < pyramid.leagues.length; i++) {
                        System.out.println((i+1) +") View Division " + (i+1));
                    }
                    choice = kb.nextInt();
                    if (choice == 0) {
                        for (int i = 0; i < pyramid.leagues.length; i++) {
                            pyramid.leagues[i].presentLeague();
                        }
                    } else {
                        this.pyramid.leagues[choice-1].presentLeague();
                    }
                    break;
                }
            }
        }
    }

}
