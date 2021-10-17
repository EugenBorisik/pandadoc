package org.example.pandadoc.pageobject.document;

import lombok.Getter;
import org.example.pandadoc.driver.WebDriverSingleton;
import org.example.pandadoc.pageobject.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DocumentConstructorPage extends AbstractPage {

  private static final By SEND_DOCUMENT_BUTTON = By.xpath("//div[contains(@class, '__send-document-button__')]//button");
  private static final By SEND_VIA_EMAIL_BUTTON = By.xpath("//div[@data-kit='menu-item' and normalize-space()='Send via email']");
  private static final By CONTENT_FRAME = By.id("kolas-editor-iframe");

  @Getter
  private final SendDocumentComponent sendDocumentComponent;

  public DocumentConstructorPage() {
    getWait(30).until(ExpectedConditions.visibilityOfElementLocated(CONTENT_FRAME));
    driver.switchTo().frame(driver.findElement(CONTENT_FRAME));
    getWait(10).until(ExpectedConditions.visibilityOfElementLocated(SEND_DOCUMENT_BUTTON));

    sendDocumentComponent = new SendDocumentComponent(this);
  }

  public DocumentConstructorPage sendDocument() {
    driver.findElement(SEND_DOCUMENT_BUTTON).click();
    driver.findElement(SEND_VIA_EMAIL_BUTTON).click();

    return sendDocumentComponent.sendDocument();
  }


  public static class SendDocumentComponent {

    private static final By SEND_AND_CONTINUE_BUTTON = By.xpath("//button[text()='Save and continue']");
    private static final By CONFIRM_SEND_BUTTON = By.xpath("//div[@data-testid='document-send-by-email-dialog']//button[@type='submit']");
    private static final By STATUS_LABEL = By.xpath("//p[@data-testid='regular-send-header']");

    private final WebDriver driver;
    private final DocumentConstructorPage documentConstructorPage;

    public SendDocumentComponent(DocumentConstructorPage documentConstructorPage) {
      driver = WebDriverSingleton.getInstance().getDriver();
      this.documentConstructorPage = documentConstructorPage;
    }

    private DocumentConstructorPage sendDocument() {
      driver.findElement(SEND_AND_CONTINUE_BUTTON).click();
      driver.findElement(CONFIRM_SEND_BUTTON).click();
      documentConstructorPage.getWait(10).until(ExpectedConditions.visibilityOfElementLocated(STATUS_LABEL));

      return documentConstructorPage;
    }

    public String getStatusLabel() {
      return driver.findElement(STATUS_LABEL).getText();
    }
  }
}
