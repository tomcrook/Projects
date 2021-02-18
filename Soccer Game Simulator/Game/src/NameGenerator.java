public class NameGenerator {

    String[] fns = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    };

    String[] lns = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
            "Hawkins", "Arnold", "Pierce", "Kelley", "Spencer", "Knight", "Elliot", "Lane", "Ray", "Delgado", "Pena",
            "Solis", "Klein", "Erickson", "Reeves", "Chang", "Baldwin", "Daniel", "Simon", "Roberson", "Huff"
    };
    
    NameGenerator() {

    }
    
    String generateName() {
        String fn = fns[(int) Math.random() * fns.length];
        String ln = lns[(int) Math.random() * lns.length];
        
        return fn + ". " + ln;
    }

}

