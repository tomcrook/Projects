class Player {
    int age, overall, potential;
    int birthWeek;
    int form;
    String name;
    char loc, pos;
    int PAC, SHO, PAS, DRI, DEF, PHY;


    Player(String name, int age, int overall, int potential, char loc, char pos) {
        this.name = name;
        this.age = age;
        this.potential = potential;
        this.overall = overall;
        this.birthWeek = 1 + (int)(Math.random() * 52);
        this.form = 0;
        this.loc = loc;
        this.pos = pos;
    }

    void makeStats() {




    }

    void simulateWeek(int week, int form) {

    }

    public String toString() {
        int bufferLength = 30 - this.name.length();
        String buffer = "";
        for (int i=0 ; i < bufferLength; i++){
            buffer += " ";
        }

        return buffer + this.name + " || " + this.overall + " OVR " + this.loc + this.pos + " || " + this.age + "Y/O";
    }

}
