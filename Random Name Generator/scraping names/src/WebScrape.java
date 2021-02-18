import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebScrape {

    public static void main(String[] args) {


        try {
            String startURL =
                    "https://www.mithrilandmages.com/utilities/CityBrowse.php?letter=";
            String[] letters = {
                    "a", "b", "c", "d",
                    "e", "f", "g", "h",
                    "i", "j", "k", "l",
                    "m", "n", "o", "p",
                    "q", "r", "s", "t",
                    "u", "v", "w", "x",
                    "y", "z"
            };

            for (int i = 0; i < letters.length; i++) {
                String letter = letters[i].toUpperCase();
                String url = startURL + letter + "&country=united_states_city_names";
                Document document = Jsoup.connect(url).get();
                String toCut = document.select("#medNameColumns").text();
                String name = "";
                boolean stop = false;
                int count = 0;
                while (!stop) {
                    if (toCut.indexOf(letter, 1) < 0) {
                        stop = true;
                    } else {
                        name = toCut.substring(0, toCut.indexOf(letter, 1) - 1);
                        if (count >= 6) {
                            if (Math.random() > .94) {
                                System.out.println("\"" + name + "\", ");
                                count = 0;
                            }
                        } else {
                            if (Math.random() > .94) {
                                System.out.print("\"" + name + "\", ");
                                count++;
                            }
                        }
                        toCut = toCut.substring(name.length() + 1);
                    }
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
