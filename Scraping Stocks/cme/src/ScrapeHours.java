import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.management.remote.JMXConnector;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;


public class ScrapeHours {


  boolean isSwap;
  String link;
  Document document;

  public ScrapeHours(String link) {
    this.link = link;
    try {
      this.document = Jsoup.connect(link).get();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new IllegalArgumentException("Unable to find link : " + link);
    }

    //System.out.println(document);
  }

  String scrape() {
    String text = this.justString();
    Scanner scan = new Scanner(text);
    ArrayList<String> ret = new ArrayList<>();
    String times = "";
    while(scan.hasNext()) {
      String next = scan.next();

      if (next.contains(":")) {
        try {
          int n = 0;
          if (next.charAt(0) == '(') {
            n = 1;
          }
          Integer.parseInt(String.valueOf(next.charAt(n)));
          String num = "";
          for (int i = 0; i < next.indexOf(":"); i++) {
            try {
              Integer.parseInt(String.valueOf(next.charAt(i)));
              num += next.charAt(i);
            } catch (NumberFormatException e) {

            }
          }
          String p = scan.next();
          if (ret.size() < 4) {
            if (p.contains("p")) {
              times += (Integer.parseInt(num) + 12) + next.substring(next.indexOf(":")) + ",";
              ret.add((Integer.parseInt(num) + 12) + next.substring(next.indexOf(":")));
            } else {
              times += next + ",";
              ret.add(next);
            }
            if (p.contains("CT")) {
              times += "CT,";
              ret.set(ret.size() - 1, this.convertToUTC(ret.get(ret.size() - 1), "CT"));
              ret.set(ret.size() - 2, this.convertToUTC(ret.get(ret.size() - 2), "CT"));
            } else if (p.contains("ET")) {
              times += "ET,";
              ret.set(ret.size() - 1, this.convertToUTC(ret.get(ret.size() - 1), "ET"));
              ret.set(ret.size() - 2, this.convertToUTC(ret.get(ret.size() - 2), "ET"));
            }
          }


        } catch (Exception e) {
          e.getMessage();
        }
      } else if (next.contains("CT")) {
        if (ret.size() % 2 == 0) {
          times += "CT,";
          ret.set(ret.size() - 1, this.convertToUTC(ret.get(ret.size() - 1), "CT"));
          ret.set(ret.size() - 2, this.convertToUTC(ret.get(ret.size() - 2), "CT"));
        }
      } else if (next.contains("ET")) {
        if (ret.size() % 2 == 0) {
          times += "ET,";
          ret.set(ret.size() - 1, this.convertToUTC(ret.get(ret.size() - 1), "ET"));
          ret.set(ret.size() - 2, this.convertToUTC(ret.get(ret.size() - 2), "ET"));
        }
      }
    }


//    if (this.type == PageType.FUTURE || this.type == PageType.OPTION) {
//      Elements elem = document.getElementsContainingText("Trading Hours");
//      for (Element e : elem) {
//        String text = e.text().substring(e.text().indexOf("Trading Hours") + 14).substring(0, 200);
//
//        return text;
//      }
//      throw new IllegalArgumentException("Could not find trading hours.");
//    }
//    throw new IllegalArgumentException("Not including swaps.");
    return text + "," + this.decipherTimes(ret);
  }

  String justString() {
    String html = this.document.outerHtml();
    String text = html.substring(html.indexOf("Trading Hours"));
    text = text.substring(0, 1000);
    if (text.contains("Minimum Price Fluctuation")) {
      text = text.substring(0, text.indexOf("Minimum Price Fluctuation"));
    }
    if (text.indexOf("CME Globex") < 100 && text.indexOf("CME Globex") > -1) {
      text = text.substring(text.indexOf("CME Globex") + 9);
      if (text.indexOf("<td") > -1) {
        text = text.substring(text.indexOf("<td>") + 4);
        text = text.substring(0, text.indexOf("</td>"));
      }
    } else {
      if (text.indexOf("<td") > -1) {
        text = text.substring(text.indexOf("<td") + 3);
        text = text.substring(text.indexOf(">") + 1);
        text = text.substring(0, text.indexOf("</td>"));
      }
    }
    while (text.contains(",")) {
      text = text.substring(0, text.indexOf(",")) + " " + text.substring(text.indexOf(",") + 1);
    }

    if (text.length() > 100) {
      text = text.substring(0, 100);
    }
    return text;
  }

  public String convertToUTC(String time, String timeZone) {
    String num = "";
    for (int i = 0; i < time.indexOf(":"); i++) {
      num += time.charAt(i);
    }
    int h = Integer.parseInt(num);
    if (timeZone.equals("CT")) {
      h += 6;
    } else if (timeZone.equals("ET")) {
      h += 5;
    }
    if (h > 24) {
      h -= 24;
    }
    return h + time.substring(time.indexOf(":")) + "UTC";
  }

  public String decipherTimes(ArrayList<String> times) {
    ArrayList<Boolean> booleans = new ArrayList<>();
    ArrayList<String> newTimes = new ArrayList<String>();
    for (int i = 1; i < times.size(); i+=2) {
      newTimes.add(times.get(i));
    }
    times = newTimes;
    int i = 0;
    for (String s : times) {
      if (i < 4) {
        if (s.contains("UTC")) {
          booleans.add(true);
        } else {
          booleans.add(false);
        }
        i++;
      }
    }
    if (!booleans.contains(true)) {
      for (int x = 0; x < booleans.size(); x++) {
        times.set(x, this.convertToUTC(times.get(x), "CT"));

        // if NYMEX then do ET
      }
    } else {
      for (int x = 0; x < booleans.size(); x++) {
        if (booleans.get(x) == false) {
          times.remove(x);
          booleans.remove(x);
          x--;
        }
      }
    }

    int max = 0;
    for (int x = 0; x < times.size(); x ++) {
      if (this.getHours(times.get(x)) > this.getHours(times.get(max))) {
        max = x;
      }
    }

    return times.get(max);

  }

  public int getHours(String time) {
    String num = "";
    for (int i = 0; i < time.indexOf(":"); i++) {
      num += time.charAt(i);
    }
    return Integer.parseInt(num);
  }

}
