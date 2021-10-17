package org.example.pandadoc.pageobject;

import org.example.pandadoc.driver.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {

  protected final WebDriver driver;

  public AbstractPage() {
    driver = WebDriverSingleton.getInstance().getDriver();
  }

  protected WebDriverWait getWait(int timeout) {
    return new WebDriverWait(driver, timeout);
  }
}
