package stocks;

import java.util.HashMap;
import java.util.List;

public class Portfolio {
  HashMap<String, Double> portfolio;
  List<Transaction> transactions;

  public Portfolio(HashMap<String, Double> portfolio, List<Transaction> transactions) {
    this.portfolio = portfolio;
    this.transactions = transactions;
  }
}
