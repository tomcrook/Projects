import java.util.ArrayList;

public class Match {

    Team homeTeam, awayTeam;
    int minute;
    int homeScore, awayScore;
    ArrayList<Player> goalScorers = new ArrayList<Player>();
    ArrayList<String> homeOrAway = new ArrayList<String>();
    ArrayList<Integer> minuteGoals = new ArrayList<Integer>();

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

    void play() {
        boolean fullTime = false;
        this.pregameAnnouncement();
        while (!fullTime) {

            this.determinePossession(minute);
            if (minute == 90) {
                fullTime = true;
            }
            minute++;
        }
        this.postgameAnnouncement();
        this.updateTeamStats();
    }

    void quickPlay() {
        int hdiff = homeTeam.isXBetterThan(awayTeam);
        int adiff = awayTeam.isXBetterThan(homeTeam);
        if (hdiff < 0) {
            hdiff = 0;
        } else {
            adiff = 0;
        }

        for (int i = 0; i < 90; i++){
            if (Math.random() <= 0.5) {
                if (Math.random() < (1.0/45) + (.2/45 * hdiff)) {
                    homeScore++;
                    goalScorers.add(this.homeTeam.getRandomGoalscorer());
                    minuteGoals.add(i+1);
                    homeOrAway.add("(H)");
                }
            } else {
                if (Math.random() < 1.0/45 + (.2/45 * adiff)) {
                    awayScore++;
                    goalScorers.add(this.awayTeam.getRandomGoalscorer());
                    minuteGoals.add(i+1);
                    homeOrAway.add("(A)");
                }
            }
        }
        this.updateTeamStats();
    }

    ////////General Commentary//////

    void pregameAnnouncement() {
        System.out.println(homeTeam.name + " is hosting " + awayTeam.name + " at " + homeTeam.stadiumName);
    }

    void postgameAnnouncement() {
        System.out.print("Today's match sees ");
        if (homeScore > awayScore) {
            System.out.print(homeTeam.name + " get the best of " + awayTeam.name);
        } else if (awayScore > homeScore) {
            System.out.print(awayTeam.name + " get the best of " + homeTeam.name);
        } else {
            System.out.print(homeTeam.name + " and " + awayTeam.name + " draw");
        }
        System.out.println(" with a final score of " + homeScore + " - " + awayScore);
    }

    void presentResult() {
        String lineUp = homeTeam.name + ": ";
        int toDo = 35 -  lineUp.length();
        for (int i = 0; i < toDo; i++){
            lineUp = " " + lineUp;
        }
        String toPresent = lineUp + homeScore + " - " + awayScore + " :" + awayTeam.name;
        System.out.println(toPresent);
        this.printGoalScorers();
    }

    void printGoalScorers() {
        String toPresent = "";
        for (int i = 0; i < goalScorers.size(); i++) {
            toPresent=this.minuteGoals.get(i) + "' : " + this.goalScorers.get((i)).name + " " +
                    this.homeOrAway.get(i);
            int toDo = 100 - toPresent.length();
            for (int j = 0; j < toDo; j++){
                toPresent = " " + toPresent;
            }
            System.out.println(toPresent);
        }


    }

    ///////Game Functionality///////

    void determinePossession(int minute) {
        if (Math.random() > .75) {
            System.out.println(minute + "' :");
            if (Math.random() > (.5 - (.015 * homeTeam.isXBetterThan(awayTeam)))) { // does homeTeam get possession?
                System.out.println(homeTeam.name + " has possession.");
                ////// ADD MORE COMMENTARY OPTIONS HERE
                this.goalScoringChance(true);
            } else { // awayTeam has possession
                System.out.println(awayTeam.name + " has possession.");
                ////// ADD MORE COMMENTARY OPTIONS HERE
                this.goalScoringChance(false);
            }
        }
    }

    void goalScoringChance(boolean isHomeTeam) {
        System.out.println("They pass the ball into the final third.");

        if (isHomeTeam) {
            if (Math.random() < (.15 + (.005 * homeTeam.isXBetterThan(awayTeam)))) { // homeTeam score?
                homeScore++;
                System.out.println(homeTeam.name + " scores!");
                ///// MORE COMMENTARY OPTIONS

                presentResult();
            } else {
                ///// MORE COMMENTARY OPTIONS
                System.out.println("Well defended by " + awayTeam.name);
            }
        } else {
            if (Math.random() < (.15 + (.005 * awayTeam.isXBetterThan(homeTeam)))) { // awayTeam score?
                awayScore++;
                System.out.println(awayTeam.name + " scores!");
                presentResult();
                ///// MORE COMMENTARY OPTIONS

            } else {
                ///// MORE COMMENTARY OPTIONS
                System.out.println("Well defended by " + homeTeam.name);
            }
        }

    }

    void updateTeamStats() {
        homeTeam.gFor += homeScore;
        awayTeam.gFor += awayScore;
        homeTeam.gAgainst += awayScore;
        awayTeam.gAgainst += homeScore;
        //System.out.println(homeTeam.name + " " + homeScore + " - " + awayScore + " " + awayTeam.name);
        homeTeam.changeGoalDifference(homeScore - awayScore);
        awayTeam.changeGoalDifference(awayScore - homeScore);
    }

}

