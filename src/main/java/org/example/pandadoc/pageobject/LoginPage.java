package org.example.pandadoc.pageobject;

import org.example.pandadoc.config.ExecutionContext;
import org.openqa.selenium.By;

public class LoginPage extends AbstractPage {

  private static final By USERNAME_INPUT = By.id("username");
  private static final By PASSWORD_INPUT = By.id("password");
  private static final By LOG_IN_BUTTON = By.id("submit_button");

  public LoginPage() {
    driver.get(ExecutionContext.get("app.url"));
  }

  public DashboardPage login(String username, String password) {
    driver.findElement(USERNAME_INPUT).sendKeys(username);
    driver.findElement(PASSWORD_INPUT).sendKeys(password);
    driver.findElement(LOG_IN_BUTTON).click();

    return new DashboardPage();
  }
}
