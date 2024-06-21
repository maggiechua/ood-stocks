package stocks.model;

import java.util.List;

/**
 *
 */
public class ViewModel implements ReadOnlyModel {
  private StocksModel model;

  public ViewModel(StocksModel model) {
    this.model = model;
  }

  @Override
  public String getStock() {
    return model.getStock();
  }

  @Override
  public List<Portfolio> getPortfolios() {
    return model.getPortfolios();
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    return model.getPortfolioValue(name, date);
  }
}
