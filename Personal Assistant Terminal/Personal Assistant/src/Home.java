import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;


// the main page of the application
public class Home {

  JFrame frame;

  String id = "tom";
  JPanel home;
  JTextField outputField;
  JTextField inputField;
  Schedule schedule;
  ArrayList<String> stored = new ArrayList<String>();
  int current = 0;
  SaveFile save;

  ArrayList<App> applications;

  public Home() {

    frame = new JFrame();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.home = new JPanel();
    this.home.setPreferredSize(new Dimension(400,100));
    this.home.setLayout(new GridLayout(2,1));
    this.constructInputField();
    this.outputField = new JTextField();
    this.outputField.setEditable(false);
    this.outputField.setText("Welcome to personal assistant v1.0");
    this.schedule = new Schedule();
    this.applications = new ArrayList<>();
    save = new SaveFile(this);
    save.open();

    frame.add(getHome());
    frame.pack();

  }

  public JPanel getHome() {
    this.home.add(outputField);
    this.home.add(inputField);
    return home;
  }

  private void readCommand(String command) {
    if (command.equalsIgnoreCase("hello")) {
      outputField.setText("Hello!");
    } else if (command.contains("task")) {
      outputField.setText(schedule.commandReader(command));
    } else if (command.contains("save")) {
      outputField.setText(save.save());
    } else if (command.contains("open")) {
      outputField.setText(save.open());
    } else if (command.contains("pomodoro")) {
      if (command.contains("remove")) {
        outputField.setText(this.removeApplications(AppType.Pomodoro));
      } else {
        try {
          command = command.substring(command.indexOf("pomodoro") + 8);
          outputField.setText(this.addComponent(new Pomodoro(new Scanner(command))));
        } catch (Exception e) {
          outputField.setText("Command incorrect.");
        }
      }
    } else {
      outputField.setText("\"" + command + "\" not a valid command.");
    }
  }

  private void constructInputField() {
    this.inputField = new JTextField();
    this.inputField.setText("Insert Commands Here");
    inputField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        stored.add(inputField.getText());
        current = stored.size();
        readCommand(inputField.getText());
        inputField.setText("");
      }
    });
    inputField.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 38 && current != 0) {
          current--;
          if (current < 0) {
            current = 0;
          }
          inputField.setText(stored.get(current));
        } else if (e.getKeyCode() == 40) {
          current++;
          if (current >= stored.size()) {
            current = stored.size() - 1;
          }
          inputField.setText(stored.get(current));
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });
  }

  private String removeApplications(AppType type) {
    ArrayList<App> toRemove = new ArrayList<App>();
    for (App a : applications) {
      if (a.getType() == type) {
        toRemove.add(a);
        frame.remove(a.add());
      }
    }
    applications.removeAll(toRemove);
    this.frame.setLayout(new GridLayout(applications.size(), 1));
    this.frame.pack();
    return  "All " + type + " instances removed.";
  }

  private String addComponent(App app) {
    this.applications.add(app);
    this.frame.setLayout(new GridLayout(1 + applications.size(), 1));
    this.frame.add(app.add());
    this.frame.pack();
    return "Added a new " + app.getType() + " application.";
  }

}
