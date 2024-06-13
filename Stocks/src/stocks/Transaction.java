package stocks;

/**
 * The following class represents a Transaction (buying/selling) of a portfolio's stocks.
 */
public class Transaction {
  private final String stock;
  private final Double shares;
  private final String date;
  private final Double price;

  /**
   * A Transaction consists of the stock, shares, date, and price.
   * @param stock the given stock
   * @param shares the given number of shares
   * @param date the given date a transaction was made
   * @param price the closing price of the given stock
   */
  public Transaction(String stock, Double shares, String date, Double price) {
    this.stock = stock;
    this.shares = shares;
    this.date = date;
    this.price = price;
  }

  public String getStock() {
    return this.stock;
  }

  public Double getShares() {
    return this.shares;
  }

  public String getDate() {
    return this.date;
  }

  public Double getPrice() {
    return this.price;
  }
}
