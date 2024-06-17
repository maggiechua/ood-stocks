import org.junit.Before;

import stocks.FileCreator;
import stocks.FileParser;

public class FilesTest {
  private FileParser fp;
  private FileCreator fc;

  @Before
  public void setUp() {
    fp = new FileParser();
    fc = new FileCreator();
  }

  
}
