package stocks;

public class Transaction {
  private final String stock;
  private final Double shares;
  private final String date;
  private final Double price;

  public Transaction(String stock, Double shares, String date, Double price) {
    this.stock = stock;
    this.shares = shares;
    this.date = date;
    this.price = price;
  }

  public Transaction getTransaction() {
    return this;
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
