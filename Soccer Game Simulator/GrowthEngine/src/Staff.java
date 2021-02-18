abstract class Staff {

    String name;
    int rating;
    NameGenerator nameGenerator = new NameGenerator();

    Staff(String name) {
        this.name = name;
    }
    Staff() {
        this.name = nameGenerator.generateName();
    }
    Staff(int rating) {
        this.name = nameGenerator.generateName();
        this.rating = rating - 2 + (int)(Math.random() * 5);
        if (this.rating < 1) {
            this.rating = 1;
        } else if (this.rating > 99) {
            this.rating = 99;
        }
    }

}


class Coach extends Staff {

    Coach(String name) {
        super(name);
    }
    Coach() {
        super();
    }
    Coach(int rating) {
        super(rating);
    }

}