import java.io.StringReader;
import java.util.*;

public class Schedule {

  ArrayList<Task> tasks;

  public Schedule() {
    tasks = new ArrayList<>();
  }

  public String commandReader(String cmd) {
    if (cmd.contains("add")) {
      try {
        return this.addTask(cmd);
      } catch (Exception e) {
        e.printStackTrace();
        return "Something went wrong.";
      }
    } else if (cmd.contains("get")) {
      return this.getTask();
    } else if (cmd.contains("change")) {
      return this.changeTask(cmd);
    } else if (cmd.contains("remove")) {
      return this.remove(cmd.substring(cmd.indexOf("remove") + 7));
    } else {
      return "Not a correct command.";
    }
  }

  // format : add task "title" due MM/DD HH:MM time 999
  // add task "new task" due 12/31/2021 10:39 time 999
  // returns the new task's toString
  public String addTask(String cmd) throws Exception {
    if (cmd.contains("task add")) {
      cmd = cmd.substring(cmd.indexOf("task add") + 9);

      String title = cmd.substring(cmd.indexOf("\"") + 1, cmd.indexOf("\"", cmd.indexOf("\"") + 1));

      cmd = cmd.substring(title.length() + 2);

      Date date = null;
      double t = 0;

      Scanner scan = new Scanner(cmd);
      while (scan.hasNext()) {
        String next = scan.next();
        if (next.equals("due")) {
          String time = scan.next();
          int month = Integer.parseInt(time.substring(0, time.indexOf("/")));
          int day = Integer.parseInt(time.substring(time.indexOf("/") + 1, time.indexOf("/") + 3));
          int year = new Date().getYear();
          if (time.length() > 5) {
            year = Integer.parseInt(time.substring(6)) - 1901;
          }
          date = new Date(year, month - 1, day);

        } else if (next.equals("time")) {
          t = Double.parseDouble(scan.next());
        }
      }
      Task task = new Task(title, t, date);
      this.tasks.add(task);
      return task.getTitle();
    } else {
      throw new Exception("invalid command");
    }
  }

  public void sortTasks() {
    Collections.sort(tasks);
  }

  public String getTask() {
    if (!tasks.isEmpty()) {
      double sum = 0;
      for (Task t : tasks) {
        sum += t.getPriority(new Date());
      }
      double choice = sum * Math.random();
      System.out.println(choice);
      Task toDo = null;
      for (Task t : tasks) {
        System.out.println(t.getPriority(new Date()) / sum);
        if (t.getPriority(new Date()) <= choice) {
          toDo = t;
          break;
        }
        sum -= t.getPriority(new Date());
      }
      if (toDo == null) {
        toDo = tasks.get(tasks.size() - 1);
      }

      return "Do this task right now: " + toDo.getTitle();
    } else {
      return "Looks like you don't have any tasks to do!";
    }

  }

  public String changeTask(String cmd) {
    cmd = cmd.substring(5);
    String orig = cmd.substring(cmd.indexOf("\"") + 1, cmd.indexOf("\"", cmd.indexOf("\"") + 1));
    System.out.println(orig);
    if (cmd.contains("change title")) {

      this.find(orig).setTitle(cmd.substring(cmd.indexOf(orig) + orig.length() + 2));

      return "Task \"" + orig + "\" title changed";

    } else if (cmd.contains("change time")) {
      double t = this.find(orig).time;

      Scanner scan = new Scanner(cmd);
      while (scan.hasNext()) {
        String next = scan.next();

        try {
          t = Integer.parseInt(next);
        } catch (Exception e) {
          try {
            t = Double.parseDouble(next);
          } catch (Exception f) {

          }
        }

      }

      this.find(orig).setTime(t);
      return "Task \"" + orig + "\" time changed";
    } else if (cmd.contains("change due")) {

      String add = this.find(orig).toString();
      add = add.substring(0, add.indexOf("due"));
      tasks.remove(find(orig));
      String t = "";

      Scanner scan = new Scanner(cmd);
      while (scan.hasNext()) {
        String next = scan.next();
        if (next.indexOf("/") != -1) {
          t = next;
        }
      }

      this.commandReader(add + t);
      return "Task \"" + orig + "\" due changed";

    } else {
      return "Command invalid.";
    }

  }

  public Task find(String name) {
    for (Task t : tasks) {
      if (t.title.equals(name)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Task doesn't exist.");
  }

  public String remove(String name) {
    try {
      this.tasks.remove(find(name));
      return name + " removed successfully.";
    } catch (Exception e) {
      return e.getMessage();
    }
  }

}
