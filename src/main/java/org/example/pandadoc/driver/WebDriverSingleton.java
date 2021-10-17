package org.example.pandadoc.driver;

import org.example.pandadoc.config.ExecutionContext;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class WebDriverSingleton {

  private static WebDriverSingleton instance;

  private final ThreadLocal<WebDriver> driverHolder;
  private final Browser browser;

  private WebDriverSingleton() {
    driverHolder = new ThreadLocal<>();
    browser = Browser.of(ExecutionContext.get("browser").toLowerCase());
  }

  public static WebDriverSingleton getInstance() {
    if (Objects.isNull(instance)) {
      synchronized (WebDriverSingleton.class) {
        if (Objects.isNull(instance)) {
          instance = new WebDriverSingleton();
        }
      }
    }

    return instance;
  }

  public WebDriver getDriver() {
    if (Objects.isNull(driverHolder.get())) {
      DriverStrategy driverStrategy = Boolean.parseBoolean(ExecutionContext.get("remote"))
          ? new RemoteDriverStrategy()
          : new LocalDriverStrategy();
      WebDriver driver = driverStrategy.getWebDriver(browser);

      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.of(5, ChronoUnit.SECONDS));

      driverHolder.set(driver);
    }

    return driverHolder.get();
  }

  public void closeDriver() {
    driverHolder.get().quit();
    driverHolder.set(null);
  }
}
