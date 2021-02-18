import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class News {


  public static void main(String[] args) throws IOException {

    String[] topics = {
      "national", "world", "politics", "business", "health", "science", "technology",
    };

    HashMap<String, String> headlines = new HashMap<String, String>();

    for (String topic : topics) {
      Document document = Jsoup.connect("https://npr.org/sections/" + topic).get();
      Elements story = document.select("#featured > div > article:nth-child(1) > div.item-info-wrap > div > h2 > a");
      String h = story.text();
      if (!headlines.containsKey(h)) {
        document = Jsoup.connect(story.attr("href")).get();
        String s = "";
        for (int i = 2; i < 5; i++) {
          String st = document.select("#storytext > p:nth-child(" + i + ")").text();
          Scanner scan = new Scanner(st);
          int lineTotal = 0;
          while (scan.hasNext()) {
            String next = scan.next();
            lineTotal += next.length();
            s += next + " ";
            if (lineTotal > 50) {
              lineTotal = 0;
              s += "\n    ";
            }
          }
          s += "\n";
        }
        headlines.put(h, s);
      }
    }

    Object[] arr = headlines.keySet().toArray();
    if (args.length == 0) {
      for (int i = 0; i < headlines.size(); i++) {
        System.out.println(i + 1 + ") " + arr[i]);
      }
    } else {
      int i = 0;
      for (String s : headlines.keySet()) {
        if (Integer.parseInt(args[0]) - 1 == (i)) {
          System.out.println(arr[i] + "\n\n" + headlines.get(arr[i]));
        }
        i++;
      }
    }


  }

}
