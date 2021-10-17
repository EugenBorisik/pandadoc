package org.example.pandadoc;

import org.example.pandadoc.config.ExecutionContext;
import org.example.pandadoc.config.TestListener;
import org.example.pandadoc.driver.WebDriverSingleton;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public abstract class BaseUiTest {

  protected static final String USERNAME = ExecutionContext.get("user.id");
  protected static final String PASSWORD = ExecutionContext.get("user.password");

  @AfterMethod
  public void cleanup() {
    WebDriverSingleton.getInstance().closeDriver();
  }
}
