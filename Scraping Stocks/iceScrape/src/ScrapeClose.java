import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrapeClose {

  Document document;
  boolean option;
  String close;

  ScrapeClose(String link) throws Exception {
    try {
      document = Jsoup.connect(link).get();
    } catch (Exception e) {
      throw new Exception("Unable to connect to link.");
    }
    this.option = this.isOption();
    this.close = this.scrapeInformation();
  }

  private String scrapeInformation() {
    String closeEST = document.select("body > div:nth-child(1) > div.sticky-header__main > div.container-fluid > div > div > div > div > div > div > div > div:nth-child(2) > div.true-grid-5.true-grid-offset-1 > div:nth-child(2) > table > tbody > tr:nth-child(1) > td:nth-child(2) > span:nth-child(2)").text();
    if (closeEST.equals("")) {
      return "ERROR : TIME CANNOT BE FOUND ON WEB PAGE";
    } else {
      if (closeEST.contains("P")) {
        closeEST = this.PMto24(closeEST);
      } else {
        closeEST = this.AMto24(closeEST);
        closeEST.replace("AM", "");
      }
    }

    return this.convertToUTC(closeEST, "ET");
  }

  private boolean isOption() {
    return document.select("body > div:nth-child(1) > div.sticky-header__main > div.container-fluid > div > div > div > div > div > div > header > h1").text().toLowerCase().contains("option")
      || document.select("body > div:nth-child(1) > div.sticky-header__main > div.container-fluid > div > div > div > div > div > div > div > div:nth-child(2) > div.true-grid-4 > div:nth-child(1) > p").text().toLowerCase().contains("option");
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
    return h + time.substring(time.indexOf(":"), time.indexOf(":") + 3) + "UTC";
  }

  public String PMto24(String time) {
    String num = "";
    for (int i = 0; i < time.indexOf(":"); i++) {
      try {
        Integer.parseInt(String.valueOf(time.charAt(i)));
        num += time.charAt(i);
      } catch (NumberFormatException e) {

      }
    }
    int hour = Integer.parseInt(num);
    if (hour < 12) {
      hour += 12;
    }

    if (hour >= 24) {
      hour = hour - 24;
    }

    return hour + time.substring(time.indexOf(":"), time.length() - 3);

  }

  public String AMto24(String time) {
    String num = "";
    for (int i = 0; i < time.indexOf(":"); i++) {
      try {
        Integer.parseInt(String.valueOf(time.charAt(i)));
        num += time.charAt(i);
      } catch (NumberFormatException e) {

      }
    }
    int hour = Integer.parseInt(num);
    if (hour == 12) {
      hour = 0;
    }

    return hour + time.substring(time.indexOf(":"), time.length() - 3);

  }

}
