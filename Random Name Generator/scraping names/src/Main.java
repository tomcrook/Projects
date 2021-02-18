import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    for (int i = 0; i < 11; i++) {
      String url = "https://fmdataba.com/football-manager-2021-wonderkids-best-players/";
      if (i != 0) {
        url += i;
      }
      ScrapePage scrapePage = new ScrapePage(url);
      String[] nations = scrapePage.getAttributes("body > div.container > div.col-lg-12.col-md-12.col-sm-12 > div.col-lg-10.col-md-10.col-sm-12 > div.panel.panel-default > div.panel-body > div.table-responsive > table > tbody > tr:nth-child(^) > td:nth-child(2) > a > small", "text", '^', 1, 50);
      String[] names = scrapePage.getAttributes("body > div.container > div.col-lg-12.col-md-12.col-sm-12 > div.col-lg-10.col-md-10.col-sm-12 > div.panel.panel-default > div.panel-body > div.table-responsive > table > tbody > tr:nth-child(^) > td:nth-child(4) > a > strong", "text", '^', 1, 50);
      for (int x = 0 ; x < nations.length; x++) {
        String nat = nations[x];
        String s  = names[x];
        if (s.contains(" ")) {
          System.out.println(nat + "," + s.substring(0, s.indexOf(" ")) + "," + s.substring(s.indexOf(" ") + 1));
        } else {
          System.out.println(nat + "," + s);
        }
      }

    }

  }//#mw-content-text > div.mw-parser-output > table.wikitable.plainrowheaders.sortable.jquery-tablesorter > tbody > tr:nth-child(1) > th > a
   //#mw-content-text > div.mw-parser-output > table.wikitable.plainrowheaders.sortable.jquery-tablesorter > tbody > tr:nth-child(2) > th > a
}
//body > div.container > div.col-lg-12.col-md-12.col-sm-12 > div.col-lg-10.col-md-10.col-sm-12 > div.panel.panel-default > div.panel-body > div.table-responsive > table > tbody > tr:nth-child(12) > td:nth-child(2) > a > small

//body > div.container > div.col-lg-12.col-md-12.col-sm-12 > div.col-lg-10.col-md-10.col-sm-12 > div.panel.panel-default > div.panel-body > div.table-responsive > table > tbody > tr:nth-child(1) > td:nth-child(4) > a > strong
//body > div.container > div.col-lg-12.col-md-12.col-sm-12 > div.col-lg-10.col-md-10.col-sm-12 > div.panel.panel-default > div.panel-body > div.table-responsive > table > tbody > tr:nth-child(3) > td:nth-child(4) > a > strong