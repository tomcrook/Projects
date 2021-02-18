import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Pomodoro implements App {

  int working;
  int resting;
  JTextField timer;
  int minutes;
  int seconds;
  boolean go = true;
  Timer t;

  public AppType type = AppType.Pomodoro;


  public Pomodoro(int working, int resting) {
    t = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        changeTime();
      }
    });
    t.setRepeats(true);
    t.start();
    this.working = working;
    this.resting = resting;
    this.minutes = working;
    this.seconds = 1;
    this.timer = this.makeTimer();
    this.changeTime();
  }

  public Pomodoro(Scanner scan) {
    this(scan.nextInt(), scan.nextInt());
  }


  private JTextField makeTimer() {
    JTextField area = new JTextField();
    area.setEditable(false);
    area.setPreferredSize(new Dimension(100,100));
    return area;
  }


  public void changeTime() {
    String secs = "";
    if (seconds + minutes <= 0) {
      if (go) {
        this.minutes = resting - 1;
      } else {
        this.minutes = working - 1;
      }
      this.seconds = 59;
      secs = "59";
      this.go = !go;
    } else {
      if (this.seconds > 0) {
        this.seconds--;
      } else {
        this.minutes--;
        this.seconds = 59;
      }
      secs = this.seconds + "";
      if (secs.length() == 1) {
        secs = "0" + secs;
      }
    }
    String add = "Keep working: ";
    if (!go) {
      add = "Take a break: ";
    }
    this.timer.setText(add + minutes + ":" + secs);

  }

  public JComponent add() {
    return timer;
  }

  @Override
  public AppType getType() {
    return type;
  }

  public void stop() {
    t.stop();
  }

}
