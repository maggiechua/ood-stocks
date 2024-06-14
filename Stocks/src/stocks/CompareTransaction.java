package stocks;

import java.util.Comparator;

/**
 * The following class implements the Comparator class to compare two transactions to determine
 * which one occurred earlier in time.
 */
public class CompareTransaction implements Comparator<Transaction> {
  @Override
  public int compare(Transaction o1, Transaction o2) {
    return this.determineTotalDays(o1.getDate()) - this.determineTotalDays(o2.getDate());
  }

  /**
   * The following method determines the approximate total number of days a date is equivalent to.
   * @param date the given date of the transaction
   * @return the total approximate number of days as an integer
   */
  public int determineTotalDays(String date) {
    String[] dateSplit = date.split("-");
    int tYear = Integer.parseInt(dateSplit[0]);
    int tMonth = Integer.parseInt(dateSplit[1]);
    int tDay = Integer.parseInt(dateSplit[2]);
    return tYear * 365 + tMonth * 30 + tDay;
  }
}
