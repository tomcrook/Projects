import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class SaveFile {

  Home home;
  boolean opened;

  public SaveFile(Home home) {
    this.home = home;
    this.opened = false;
  }

  public String save() {
    try {
      FileWriter writer = new FileWriter(home.id + ".txt");
      for (Task t : home.schedule.tasks) {
        writer.append(t.toString() + "\n");
      }
      writer.close();
      return "Saved successfully.";
    } catch (Exception e) {
      return "Unable to save.";
    }

  }

  public String open() {
    try {
      Scanner scan = new Scanner(new FileReader(home.id + ".txt"));
      Schedule s = new Schedule();
      while (scan.hasNextLine()) {
        s.commandReader(scan.nextLine());
      }
      home.schedule = s;
      return "Opened successfully.";
    } catch (Exception e) {
      return "Unable to open.";
    }
  }
}
