import java.io.FileWriter;
import java.io.IOException;

public class ScrapeLists {

  String link;
  String[] categories = {
      "Agriculture",
      "Credit",
      "Digital-Assets",
      "Energy",
      "Equity-Derivatives",
      "FX",
      "Freight",
      "Interest-Rates",
      "Metals",
      "US-Environmental"
  };

  ScrapeLists() {
   this.link = "https://www.theice.com/products/Futures-Options";
  }

  public void scrape() throws Exception {
    FileWriter writer = new FileWriter("out.csv");
    writer.append("Code,Exchange,Product Name,Group,Subgroup,Option?,Close\n");
    for (int i = 0; i < categories.length; i++) {
      new ScrapeCategory(link + "/" + categories[i], categories[i].replace("-", " ")).scrape(writer);
    }
  }


}
