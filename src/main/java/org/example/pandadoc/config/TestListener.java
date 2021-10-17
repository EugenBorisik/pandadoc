package org.example.pandadoc.config;

import io.qameta.allure.Attachment;
import org.example.pandadoc.driver.WebDriverSingleton;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

  @Override
  public void onTestFailure(ITestResult tr) {
    captureScreenshot();
    super.onTestFailure(tr);
  }

  @Attachment(type = "image/png")
  private byte[] captureScreenshot() {
    return ((TakesScreenshot) WebDriverSingleton.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
  }
}
