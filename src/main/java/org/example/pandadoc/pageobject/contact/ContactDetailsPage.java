package org.example.pandadoc.pageobject.contact;

import org.example.pandadoc.model.ui.Contact;
import org.example.pandadoc.pageobject.AbstractPageWithLeftMenu;
import org.openqa.selenium.By;

public class ContactDetailsPage extends AbstractPageWithLeftMenu {

  private static final String CARD_CONTAINER = "//div[contains(@class, 'ContactCard')]";
  private static final By NAME_LABEL = By.xpath(CARD_CONTAINER + "//h3[contains(@class, 'Title')]");
  private static final By COMPANY_LABEL = By.xpath(CARD_CONTAINER + "//div[contains(@class, 'SubPanelText')][1]");
  private static final By EMAIL_LABEL = By.xpath(CARD_CONTAINER + "//div[contains(@class, 'SubPanelText')][2]");
  private static final By PHONE_LABEL = By.xpath(CARD_CONTAINER + "//div[contains(@class, 'SubPanelText')][3]");

  public boolean isContactDisplayed(Contact contact) {
    String actualName = driver.findElement(NAME_LABEL).getText();
    String actualCompany = driver.findElement(COMPANY_LABEL).getText();
    String actualEmail = driver.findElement(EMAIL_LABEL).getText();
    String actualPhone = driver.findElement(PHONE_LABEL).getText();

    return actualName.equals(contact.getFullName())
        && actualCompany.equals(contact.getCompany())
        && actualEmail.equals(contact.getEmail())
        && actualPhone.equals(contact.getPhone());
  }
}
