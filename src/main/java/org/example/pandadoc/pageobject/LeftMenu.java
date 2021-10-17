package org.example.pandadoc.pageobject;

import org.example.pandadoc.driver.WebDriverSingleton;
import org.example.pandadoc.pageobject.contact.ContactsPage;
import org.example.pandadoc.pageobject.document.DocumentsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeftMenu {

  private static final By EXPAND_BUTTON = By.id("at_nav_panel_expander");
  private static final By CONTACTS_BUTTON = By.xpath("//div[contains(@class, 'leftPanelButton')]//div[contains(@class, 'TextContainer') and text()='Contacts']");
  private static final By DOCUMENTS_BUTTON = By.xpath("//div[contains(@class, 'leftPanelButton')]//div[contains(@class, 'TextContainer') and text()='Documents']");

  private final WebDriver driver;

  public LeftMenu() {
    driver = WebDriverSingleton.getInstance().getDriver();
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(EXPAND_BUTTON));
  }

  public ContactsPage openContacts() {
    expandSidebarMenu();
    driver.findElement(CONTACTS_BUTTON).click();

    return new ContactsPage();
  }

  public DocumentsPage openDocuments() {
    expandSidebarMenu();
    driver.findElement(DOCUMENTS_BUTTON).click();

    return new DocumentsPage();
  }

  private LeftMenu expandSidebarMenu() {
    driver.findElement(EXPAND_BUTTON).click();

    return this;
  }
}
