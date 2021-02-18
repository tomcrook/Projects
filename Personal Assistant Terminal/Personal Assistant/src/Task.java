import java.time.DateTimeException;
import java.util.Date;

public class Task implements Comparable {

  // time in minutes it takes to complete this task
  double time;

  String title;

  // when this assignment is due (null if its a habit)
  Date due;

  public Task(String title, double time, Date due) {
    this.time = time;
    this.title = title;
    this.due = due;
  }

  double getPriority(Date date) {
    return (due.getTime() - date.getTime()) / (time * 60);
  }

  String getTitle() {
    return this.title + " - due " + (1 + due.getMonth()) + "/" + due.getDate() + "/" + (1901 + due.getYear());
  }


  public int compareTo(Object other) {
    Date now = new Date();
    int toRet = (int) (this.getPriority(now) - ((Task)other).getPriority(now));
    if (toRet == 0) {
      return (int) (-this.time + ((Task)other).time);
    }
    return toRet;
  }

  public void setTime(double time) {
    this.time = time;
  }

  public void setDueDate(Date date) {
    this.due = date;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "task add \"" + title + "\" time " + time + " due " + (1 + due.getMonth()) + "/" + due.getDate() + "/" + (1901 + due.getYear());
  }

}


