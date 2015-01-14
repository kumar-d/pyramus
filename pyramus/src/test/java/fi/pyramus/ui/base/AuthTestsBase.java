package fi.pyramus.ui.base;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

import fi.pyramus.SqlAfter;
import fi.pyramus.SqlBefore;
import fi.pyramus.ui.AbstractUITest;

public class AuthTestsBase extends AbstractUITest {

  @Test
  @SqlBefore ("sql/basic-before.sql")
  @SqlAfter ("sql/basic-after.sql")
  public void testPromptedAuth() throws InterruptedException{
    login(ADMIN_USERNAME, ADMIN_PASSWORD);
    getWebDriver().get(getAppUrl(true) + "/users/authorize.page?client_id=854885cf-2284-4b17-b63c-a8b189535f8d&response_type=code&redirect_uri=https%3A%2F%2Fdev.muikku.fi%3A8443%2Flogin%3F_stg%3Drsp#at-auth");
    waitForUrlNotMatches(".*/index.*");
      
    Boolean authorizeElementExists = getWebDriver().findElements(By.name("authorize")).size() > 0;
    assertEquals(true, authorizeElementExists);
    Boolean denyElementExists = getWebDriver().findElements(By.name("deny")).size() > 0;
    assertEquals(true, denyElementExists);
  }
  
  @Test
  @SqlBefore ("sql/basic-before.sql")
  @SqlAfter ("sql/basic-after.sql")
  public void testSkipPrompAuth() throws InterruptedException{
    login(ADMIN_USERNAME, ADMIN_PASSWORD);
    getWebDriver().get(getAppUrl(true) + "/users/authorize.page?client_id=567765cf-1114-4b17-b63c-awbe89535f8d&response_type=code&redirect_uri=https%3A%2F%2Fdev.muikku.fi%3A8443%2Flogin%3F_stg%3Drsp#at-auth");
    waitForUrlNotMatches(".*/index.*");
    String currentUrl = getWebDriver().getCurrentUrl();
    assertEquals(true, currentUrl.startsWith("https://dev.muikku.fi"));
  }

}
