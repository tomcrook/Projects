import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class ScrapeCategory {

  String link;
  String category;

  ScrapeCategory(String link, String category) throws IOException {
    this.link = link;
    this.category = category;
  }

  public void scrape(FileWriter writer) throws Exception {


    // probably want to change to while loops to detect errors.
    String html = "";
    String last = Jsoup.connect(link).get().select("body > div:nth-child(1) > div.sticky-header__main > div.container-fluid > div > div > div > div > div > div:nth-child(3) > div > div > div > ul > li:nth-child(9) > a").attr("href");
    int lastPage = 0;
    try {
      last = last.substring(last.indexOf("=") + 1);
      lastPage = Integer.parseInt(last);
    } catch (Exception e) {
      lastPage = 1;
    }
    for (int page = 1; page <= lastPage; page++) {
      WebDriver driver = new ChromeDriver();
      String url = link + "?page=" + page;
      driver.get(url);
      Thread.sleep(1000);
      Document document = Jsoup.parse(driver.getPageSource());
      driver.close();
      for (int row = 1; row < 1000000; row++) {

        String rowCss = "body > div:nth-child(1) > div.sticky-header__main > div.container-fluid > div > div > div > div > div > div:nth-child(3) > div > table > tbody:nth-child(4) > tr:nth-child(" + row + ")";

        String code = document.select(rowCss + " > td:nth-child(1)").text();
        String productName = document.select(rowCss + " > td:nth-child(3) > a").text(); // works
        productName = productName.replaceAll(",", " ");
        String subGroup = document.select(rowCss + " > td:nth-child(4)").text();; // works
        String exchange = document.select(rowCss + " > td:nth-child(5)").text();; // works
        String link = document.select(rowCss + " > td:nth-child(3) > a").attr("href"); // works
        if (link.equals("")) {
          break;
        }
        ScrapeClose close = new ScrapeClose("https://theice.com" + link);
        String toRet = code + ","  + exchange + "," + productName + "," + category + "," + subGroup + "," + close.option + "," + close.close;
        writer.append(toRet + "\n");
        writer.flush();

      }


    }

  }

}
