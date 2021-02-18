public class PlayerGenerator {

    NameGenerator nameGenerator = new NameGenerator();

    PlayerGenerator() {

    }

    Player addPhysicals(Player player) {
        return player;
    }


    // High SHO, Med PAS, Med DRI, Low DEF
    Player attackingPlayer(int age, int overall, int potential) {
        Player newPlayer = new Player(this.nameGenerator.generateName(), age, overall, potential, 'C' , 'F');




    }

    Player finisher(Player player) {
        int variability = (int)(Math.random() * 6) + 4;
        int SHO = player.overall + variability;
        if (SHO > 99) {
            SHO = 99;
        } else if (SHO > 2 * player.overall) {
            SHO = player.overall;
        } else if (SHO < 1) {
            SHO = 1;
        }
        int PAS = player.overall - variability/2;
        if (PAS > 99) {
            PAS = 99;
        } else if (PAS > 2 * player.overall) {
            PAS = player.overall;
        } else if (PAS < 1) {
            PAS = 1;
        }
        int DRI = player.overall - variability/2;
        if (DRI > 99) {
            DRI = 99;
        } else if (DRI > 2 * player.overall) {
            DRI = player.overall;
        } else if (DRI < 1) {
            DRI = 1;
        }

        player.SHO = SHO;
        player.PAS = PAS;
        player.DRI = DRI;
        return player;
    }

    Player playmaker() {

    }


}
