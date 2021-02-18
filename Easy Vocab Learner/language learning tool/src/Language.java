import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class will get words from a particular .txt file in order to be presented
 */
public class Language {

  String file;
  WordPair curr;
  ArrayList<ArrayList<WordPair>> words;
  boolean nativeFirst = true;

  ArrayList<WordPair> last10 = new ArrayList<WordPair>();

  public Language(String file) throws IOException {
    ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(file));
    this.words = this.getWords(lines);
    this.file = file;
  }

  private ArrayList<ArrayList<WordPair>> getWords(ArrayList<String> lines) {
    ArrayList<WordPair> ret = new ArrayList<WordPair>();
    int currDiff = 0;
    for (String s : lines) {
      if (s.contains("%")) {
        currDiff++;
      } else {
        ret.add(new WordPair(s, currDiff));
      }
    }

    ArrayList<ArrayList<WordPair>> toRet = new ArrayList<ArrayList<WordPair>>();
    for (int i = 0; i < 5; i++) {
      ArrayList<WordPair> currTier = new ArrayList<WordPair>();
      for (WordPair w : ret) {
        if (w.getDiff() == i) {
          currTier.add(w);
        }
      }
      toRet.add(currTier);
    }
    return toRet;
  }

  public void getNewPair() {
    double choice = Math.random();
    int arr = 0;
    if (choice <= .5 && words.get(0).size() > 0) {
      arr = 0;
    } else if (choice <= .75 && words.size() >= 2 && words.get(1).size() > 0) {
      arr = 1;
    } else if (choice <= .875 && words.size() >= 3 && words.get(2).size() > 0) {
      arr = 2;
    } else if (choice <= .95 && words.size() >= 4 && words.get(3).size() > 0) {
      arr = 3;
    } else if (words.size() == 5 && words.get(4).size() > 0) {
      arr = 4;
    }
    try {
      this.curr = words.get(arr).get((int) (Math.random() * words.get(arr).size()));
      this.adjustLast10(curr);
    } catch (Exception e) {
      this.getNewPair();
    }
  }

  public String getTranslation() {
    return this.curr.getForeign();
  }

  public String getEnglish() {
    return this.curr.getEnglish();
  }

  public void correct() {
    if (this.curr.getDiff() + 1 < 5) {
      this.curr.setDiff(curr.getDiff() + 1);
      this.moveWordPair(curr, curr.getDiff() - 1, curr.getDiff());
    }
  }

  public void incorrect() {
    if (curr.getDiff() - 1 > 1) {
      this.curr.setDiff(curr.getDiff() - 1);
      this.moveWordPair(curr, curr.getDiff() + 1, curr.getDiff());
    }
  }

  private void moveWordPair(WordPair pair, int orig, int now) {
    words.get(orig).remove(pair);
    words.get(now).add(pair);
  }

  public void save() throws IOException {
    FileWriter writer = new FileWriter(file);
    for (int i = 0; i < words.size(); i++) {
      if (i != 0) {
        writer.append("%\n");
      }
      for (int j = 0; j < words.get(i).size(); j++) {
        writer.append(words.get(i).get(j).getEnglish() + ";" + words.get(i).get(j).getForeign() + "\n");
      }
      writer.flush();
    }
    writer.close();
  }

  public void adjustLast10(WordPair pair) {
    if (this.last10.contains(pair)) {
      this.getNewPair();
    } else if (this.last10.size() < 10) {
      this.last10.add(pair);
    } else {
      this.last10.remove(0);
      this.last10.add(pair);
    }
  }
}
