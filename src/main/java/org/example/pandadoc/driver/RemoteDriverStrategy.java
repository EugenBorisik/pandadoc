package org.example.pandadoc.driver;

import org.example.pandadoc.config.ExecutionContext;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDriverStrategy implements DriverStrategy {
  @Override
  public WebDriver getWebDriver(Browser browser) {
    Capabilities caps;
    switch (browser) {
      case CHROME:
        caps = new ChromeOptions();
        break;
      case FIREFOX:
        caps = new FirefoxOptions();
        break;
      case EDGE:
        caps = new EdgeOptions();
        break;
      default:
        throw new RuntimeException("Unsupported browser " + browser.getValue());
    }

    URL url;
    try {
      url = new URL(ExecutionContext.get("remote.url"));
    } catch (MalformedURLException e) {
      throw new RuntimeException("Incorrect grid hub URL", e);
    }

    return new RemoteWebDriver(url, caps);
  }
}
