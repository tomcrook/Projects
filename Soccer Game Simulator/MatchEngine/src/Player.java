import java.rmi.activation.ActivationGroup_Stub;
import java.util.ArrayList;

class Player {


    // Player Information
    int age, overall, potential;
    String name, position;
    Team team;
    int PAC, SHO, PAS, DRI, DEF, PHY;
    boolean gk = false, def = false, mid = false, att = false;

    // In-Match Information
    int x, y;
    int preferred_y;
    boolean home, hasBall = false;
    boolean justReceived = false;
    GameArea[] pitch;
    ArrayList<Player> startingTeam;




    Player(String name, int age, int overall, int potential, String position, Team team) {
        this.name = name;
        this.age = age;
        this.potential = potential;
        this.overall = overall;
        this.position = position;
        if (position.equals("GK")) {
            gk = true;
        } else if (position.equals("CB") || position.equals("LB") || position.equals("RB")) {
            def = true;
        } else if (position.equals("ST")) {
            att = true;
        } else {
            mid = true;
        }
        this.setRatings();
        this.team = team;
    }

    void setRatings() {
        PAC = overall;
        SHO = overall;
        PAS = overall;
        DRI = overall;
        DEF = overall;
        PHY = overall;
    }

    public String toString() {
        int bufferLength = 30 - this.name.length();
        String buffer = "";
        for (int i=0 ; i < bufferLength; i++){
            buffer += " ";
        }

        return buffer + this.name + " || " + this.overall + " OVR " + this.position + " || " + this.age + "Y/O";
    }

    String printPlayer() {
        String player = "";
        if (home) {
            player += "x";
        } else {
            player += "o";
        }
        if (hasBall) {
            player += "*";
        }
        return player;
    }

    void setPosition(int x, GameArea[] pitch, boolean isHome) {
        this.x = x;
        this.pitch = pitch;
        pitch[x].players.add(this);
        this.home = isHome;
    }

    void changeHasBall() {
        hasBall = !hasBall;
    }

    void makeDecision() {
        if (hasBall) {
            this.pass();
        } else {
            this.move();
        }
    }

    void pass() {
        if (hasBall) {
            if (!justReceived) {
                ArrayList<Double> nums = new ArrayList<Double>();
                ArrayList<Integer> locs = new ArrayList<Integer>();
                double total = 0;
                for (int i = 0; i < pitch.length; i++) {
                    double d = pitch[i].desirability(team, this);
                    if (d > 0) {
                        locs.add(i);
                        nums.add(d);
                        total += d;
                    }
                }
                double choice = Math.random() * total;
                for (int i = 0; i < nums.size(); i++) {
                    if (choice < nums.get(i)) {
                        pitch[locs.get(i)].passTo(team, this, x);
                        this.hasBall = false;
                        i = nums.size()-1;
                    } else {
                        choice -= nums.get(i);
                    }
                }
            }
            this.justReceived = false;
            this.move();
        }
    }

    void move() {
        int location = -1;
        for (int i = 0; i < pitch.length; i++) {
            if (pitch[i].hasBall()) {
                location = i;
            }
        }
        if (hasBall) {

        } else if (team.hasBall) {
            if (home) {
                this.moveAttack(this.x > location);
            } else {
                this.moveAttack(this.x < location);
            }
        } else {
            this.moveDefense(location);
        }
    }

    void shoot() {

    }

    int moveForward() {
        if (home && !pitch[x].isBottom) {
            return x+1;
        } else if (!home && !pitch[x].isTop) {
            return x-1;
        } else {
            return x;
        }
    }

    int moveBackward() {
        if (home && !pitch[x].isTop) {
            return x-1;
        } else if (!home && !pitch[x].isBottom){
            return x+1;
        } else {
            return x;
        }
    }

    void moveAttack(boolean ballBehind) {
        int px = x;
        if (att) {
            if (!ballBehind) {
                this.x = this.moveForward();
            } else if (this.isOffside(x)) {
                this.x = this.moveBackward();
            } else {
                this.x = this.moveForward();
            }
        } else if (mid) {
            if (this.isOffside(x)) {
                this.x = this.moveBackward();
            } else if (pitch[moveForward()].numberOfPlayersOnTeam(team) < 5){
                this.x = this.moveForward();
            }
        } else if (def) {
            if (ballBehind) {
                this.x = this.moveBackward();
            } else {
                this.x = this.moveForward();
            }
        }
        pitch[px].players.remove(this);
        pitch[x].players.add(this);
    }

    void moveDefense(int location) {
        int px = x;
        if (def) {
            if (home) {
                if (location > x + 2) {
                    this.x = this.moveForward();
                }
            } else {
                if (location < x - 2) {
                    this.x = this.moveForward();
                }
            }
        } else if (mid) {
            if (home) {
                if (location > x + 1) {
                    this.x = this.moveForward();
                }
            } else {
                if (location < x - 1) {
                    this.x = this.moveForward();
                }
            }
        } else if (att) {
            if (isOffside(x)) {
                this.x = moveBackward();
            } else {
                this.x = moveForward();
            }
        }
        pitch[px].players.remove(this);
        pitch[x].players.add(this);

    }

    boolean isOffside(int x) {
        int count = 0;
        if (home) {
            for (int i = x; i < pitch.length; i++) {
                count += pitch[i].numberOfPlayersNotOnTeam(team);
            }
        } else {
            for (int i = x; i > 0; i--) {
                count += pitch[i].numberOfPlayersNotOnTeam(team);
            }
        }
        return count < 2;
    }

}
