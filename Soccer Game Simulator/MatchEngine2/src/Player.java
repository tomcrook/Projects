class Player {
    int age, overall, potential;
    int birthWeek;
    int form;
    String name, position;

    int goals;

    Player(String name, int age, int overall, int potential, String position) {
        this.name = name;
        this.age = age;
        this.potential = potential;
        this.overall = overall;
        this.birthWeek = 1 + (int)(Math.random() * 52);
        this.form = 0;
        this.position = position;
    }

    void simulateWeek(int week, int form) {
        if (week == birthWeek)
            age++;
        this.form = form;

        int diff = potential - overall;
        if (week % 4 == 0 && form >= 3) {
            double chance = Math.random();
            if (age < 27) { // highest increase
                if (chance > .8)
                    overall+= 1 + (int)(Math.random() * (diff / 5)) ;
            } else if (age < 32) { // plateau
                if (chance > .90)
                    overall+= 2;
            } else if (age < 37) { // slight decrease
                if (chance > .95)
                    overall+= 1;
            } else { // highest decrease
                if (chance > .975)
                    overall+= 1;
            }
            if (form ==  5) {
                overall++;
            }
            if (overall > potential) {
                overall = potential;
            }
        } else if (week % 4 == 0 && form <= -3) {
            double chance = Math.random();
            if (age < 27) { // highest increase
                if (chance > .975)
                    overall -= 1;
            } else if (age < 32) { // plateau
                if (chance > .95)
                    overall -= 1;
            } else if (age < 37) { // slight decrease
                if (chance > .9)
                    overall -= (int) (Math.random() * (overall / 10));
            } else { // highest decrease
                if (chance > .85)
                    overall -= (int) (Math.random() * (overall / 5));
            }
            if (form == -5) {
                overall--;
            }
            if (overall < 1) {
                overall = 1;
            }
        } else if (week % 4 == 0){
            double chance = Math.random();
            if (chance > 0.9) {
                if (age < 28) {
                    overall++;
                } else if (age < 32) {
                    if (form <= 0) {
                        overall--;
                    }
                } else {
                    overall--;
                }
            }
        }

    }

    public String toString() {
        int bufferLength = 30 - this.name.length();
        String buffer = "";
        for (int i=0 ; i < bufferLength; i++){
            buffer += " ";
        }

        return buffer + this.name + " || " + this.overall + " OVR " + this.position + " || " + this.age + "Y/O";
    }

}
