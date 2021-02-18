

public class WordPair {

  String english;
  String foreign;
  int diff;

  public WordPair(String toParse, int diff) {
    this.foreign = toParse.substring(0, toParse.indexOf(";"));
    this.english = toParse.substring(foreign.length() + 1);
    this.diff = diff;
  }

  public String getEnglish() {
    return english;
  }

  public String getForeign() {
    return foreign;
  }

  public int getDiff() {
    return diff;
  }

  public void setDiff(int diff) {
    this.diff = diff;
  }


}
