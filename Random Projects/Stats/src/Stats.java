public class Stats {

  public static void main(String[] args) {
    int count1 = 0;
    int count2 = 0;
    int pa1 = 50;
    int pa2 = 50;

    for (int i = 0; i < 10000; i++) {
      if (1.1 * Math.random() * pa1 > Math.random() * pa2) {
        count1++;
      } else {
        count2++;
      }
    }

    System.out.println("" + count1 * .0001 + "\n" + count2 * .0001);


  }

}
