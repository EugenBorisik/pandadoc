package org.example.pandadoc.driver;

import org.openqa.selenium.WebDriver;

public interface DriverStrategy {

  WebDriver getWebDriver(Browser browser);
}
