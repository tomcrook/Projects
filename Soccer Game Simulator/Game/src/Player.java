public class Player {

    String name;
    // 0 -> 99
    int overall;
    // overall -> 99
    int potential;
    // -5 -> 5
    int form;
    // 16+
    int age;


    Player(String name, int overall) {
        this.name = name;
        this.overall = overall;
        this.form = 0;
        this.age = 18;
    }

    Player(String name, int overall, int age, int potential) {
        this.name = name;
        this.overall = overall;
        this.form = 0;
        this.age = age;
        this.potential = potential;
    }

    boolean willRetire() {
        return age > 43 || (age > 30 && Math.random() > (1 - (.05 * (age - 30))));
    }

    void endOfSeason() {
        this.age++;

        int toChange = 0;
        // form - age - potential relationship decides if change happens
        // if negative change, potential decreases
        // if no change, potential decreases
        // if positive change, potential stays the same

        toChange+=form;
        if (form < 0) {
            this.potential += form;
        } else if (form >= 4) {
            this.potential++;
        }
        if (age < 30) {
            toChange += (this.potential - this.overall) / (30 - this.age);
        } else if (age >= 30) {
            toChange--;
        }

        this.overall+=toChange;
    }

    public String toString() {
        return this.name + ", " + this.overall + " OVR";
    }
}
