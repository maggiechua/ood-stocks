package stocks;

public class Transaction {
  String stock;
  String date;
  Double price;

  public Transaction(String stock, String date, Double price) {
    this.stock = stock;
    this.date = date;
    this.price = price;
  }
}
