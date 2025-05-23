package stocks.model;

import java.util.Comparator;

/**
 * The following class implements the Comparator class to compares the desired date with valid
 * market dates to determine which one is the closest in the future.
 */
public class CompareDate implements Comparator<String> {
  @Override
  public int compare(String o1, String o2) {
    return this.getDayVal(o1) - this.getDayVal(o2);
  }

  /**
   * The following method retrieves the day value of the given date.
   * @param date the given date.
   * @return the day value as an integer
   */
  public int getDayVal(String date) {
    String[] dateSplit = date.split("-");
    int tYear = Integer.parseInt(dateSplit[0]);
    int tMonth = Integer.parseInt(dateSplit[1]);
    int tDay = Integer.parseInt(dateSplit[2]);
    return tYear * 365 + tMonth * 30 + tDay;
  }
}
