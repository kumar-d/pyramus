package fi.pyramus.ui.chrome;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import fi.pyramus.ui.base.AuthTestsBase;

public class AuthTestsIT extends AuthTestsBase {
  
  @Before
  public void setUp() {
    setWebDriver(new ChromeDriver());
  }
  
  @After
  public void tearDown() {
    getWebDriver().quit();
  }
  
}
