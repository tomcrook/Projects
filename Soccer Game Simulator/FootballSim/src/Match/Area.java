package Match;

public class Area {

  String view;
  boolean home;
  boolean goalBox;

  Area(boolean home, boolean goalBox) {
    if (home && goalBox) {
      view = "[H]";
    } else if (!home && goalBox) {
      view = "[A]";
    } else {
      view = "[ ]";
    }
    this.home = home;
  }

  @Override
  public String toString() {
    return this.view;
  }

}
