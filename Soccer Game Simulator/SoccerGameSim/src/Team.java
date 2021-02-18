public class Team {

    int rating;
    String name;

    Team(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String toString() {
        return name;
    }

}
