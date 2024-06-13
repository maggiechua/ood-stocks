package stocks;

import java.util.Comparator;

/**
 * The following class implements the Comparator class to compare two transactions to determine
 * which one occurred earlier in time.
 */
public class CompareTransaction implements Comparator<Transaction> {
  @Override
  public int compare(Transaction o1, Transaction o2) {
    String[] t1Date = o1.getDate().split("-");
    int t1Year = Integer.parseInt(t1Date[0]);
    int t1Month = Integer.parseInt(t1Date[1]);
    int t1Day = Integer.parseInt(t1Date[2]);
    int t1TotalDays = t1Year * 365 + t1Month * 30 + t1Day;

    String[] t2Date = o2.getDate().split("-");
    int t2Year = Integer.parseInt(t2Date[0]);
    int t2Month = Integer.parseInt(t2Date[1]);
    int t2Day = Integer.parseInt(t2Date[2]);
    int t2TotalDays = t2Year * 365 + t2Month * 30 + t2Day;

    return t1TotalDays - t2TotalDays;
  }
}
