public class ScheduleTest {

  public static void main(String[] args) {
    Schedule s = new Schedule(16);
    s.makeSchedule();
    s.printBoard();
    System.out.println(s.isValidBoard());
  }
}
