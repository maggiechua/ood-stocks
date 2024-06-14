package stocks;

import java.util.Comparator;

/**
 * The following class implements the Comparator class to compares the desired date with valid
 * market dates to determine which one is the closest in the future.
 */
public class CompareDate implements Comparator<Transaction> {
  @Override
  public int compare(Transaction o1, Transaction o2) {
    return 0;
  }
}
