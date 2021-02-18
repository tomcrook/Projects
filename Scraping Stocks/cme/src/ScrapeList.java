import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScrapeList {

  FileWriter fw = new FileWriter("out.csv");

  public ScrapeList() throws IOException {
  }


  String scrape() throws IOException {
    Document document;
    String scraped = "";
    fw.append("Globex,Exchange,Product Name,Group,Subgroup,Option?,Hours\n");
    for (int i = 1; i < 6; i++) {

      String url = "https://www.cmegroup.com/trading/products/#pageNumber=" + i + "&sortAsc=false";

      document = Jsoup.parse(Files.readString(Paths.get("page" + i)));

      for (int row = 1; row < 501; row++) {
        String info = "";
        String link = "https://www.cmegroup.com/" + document.select("#cmeProductSlate1 > tbody > tr:nth-child(" + row + ") > th > a").attr("href");
        try {
          ScrapeHours hours = new ScrapeHours(link);
          info += document.select("#cmeProductSlate1 > tbody > tr:nth-child(" + row + ") > td:nth-child(2)").text() + ",";
          info += document.select("#cmeProductSlate1 > tbody > tr:nth-child(" + row + ") > td:nth-child(6)").text() + ",";
          String name = document.select("#cmeProductSlate1 > tbody > tr:nth-child(" + row + ") > th > a").text() + ",";
          info += name;
          info += document.select("#cmeProductSlate1 > tbody > tr:nth-child(" + row + ") > td:nth-child(7)").text() + ",";
          info += document.select("#cmeProductSlate1 > tbody > tr:nth-child(" + row + ") > td:nth-child(8)").text() + ",";
          if (name.toLowerCase().contains("option")) {
            info += "true,";
          } else {
            info += "false,";
          }
          try {
            info += (hours.scrape());
          } catch (Exception e) {
            info += hours.justString() + ",ERROR," + e.getMessage();
          }
          fw.append(info + "\n");
          fw.flush();
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        System.out.println("page " + i + " row " + row);
      }
    }
    fw.close();
    return scraped;
  }


}

