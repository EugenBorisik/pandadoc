package org.example.pandadoc.pageobject.contact;

import org.example.pandadoc.driver.WebDriverSingleton;
import org.example.pandadoc.model.ui.Contact;
import org.example.pandadoc.pageobject.AbstractPageWithLeftMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactsPage extends AbstractPageWithLeftMenu {

  private static final String CHECKBOX_BY_FULL_NAME = "//div[contains(@class, 'StyledName') and text()='%s']//ancestor::td//preceding-sibling::td//label";

  private static final By NEW_CONTACT_BUTTON = By.xpath("//button[normalize-space()='New contact']");
  private static final By DELETE_BUTTON = By.xpath("//button[text()='Delete']");
  private static final By CONFIRM_BUTTON = By.xpath("//button[contains(@class, 'confirmationDialog__button')]");

  public ContactsPage() {
    getWait(10).until(ExpectedConditions.elementToBeClickable(NEW_CONTACT_BUTTON));
  }

  public ContactDetailsPage createNewContact(Contact contact) {
    new Actions(driver).moveToElement(driver.findElement(NEW_CONTACT_BUTTON)).perform();
    driver.findElement(NEW_CONTACT_BUTTON).click();

    return new NewContactComponent().createNewContact(contact);
  }

  public ContactsPage deleteContact(Contact contact) {
    By checkboxByFullName = By.xpath(String.format(CHECKBOX_BY_FULL_NAME, contact.getFullName()));

    driver.findElement(checkboxByFullName).click();
    driver.findElement(DELETE_BUTTON).click();
    driver.findElement(CONFIRM_BUTTON).click();

    getWait(10).until(ExpectedConditions.invisibilityOfElementLocated(checkboxByFullName));

    return this;
  }

  public static class NewContactComponent {
    private static final By FIRST_NAME_INPUT = By.xpath("//div[@label='First Name']//input");
    private static final By LAST_NAME_INPUT = By.xpath("//div[@label='Last Name']//input");
    private static final By EMAIL_INPUT = By.xpath("//div[@label='Email *']//input");
    private static final By PHONE_INPUT = By.xpath("//div[@label='Phone']//input");
    private static final By COMPANY_INPUT = By.xpath("//div[@label='Company']//input");
    private static final By JOB_TITLE_INPUT = By.xpath("//div[@label='Job title']//input");
    private static final By ADD_CONTACT_BUTTON = By.xpath("//button[@form='contacts:new_full_contact_form']");

    private final WebDriver driver;

    public NewContactComponent() {
      driver = WebDriverSingleton.getInstance().getDriver();
    }

    public ContactDetailsPage createNewContact(Contact contact) {
      driver.findElement(FIRST_NAME_INPUT).sendKeys(contact.getFirstName());
      driver.findElement(LAST_NAME_INPUT).sendKeys(contact.getLastName());
      driver.findElement(EMAIL_INPUT).sendKeys(contact.getEmail());
      driver.findElement(PHONE_INPUT).sendKeys(contact.getPhone());
      driver.findElement(COMPANY_INPUT).sendKeys(contact.getCompany());
      driver.findElement(JOB_TITLE_INPUT).sendKeys(contact.getJobTitle());

      driver.findElement(ADD_CONTACT_BUTTON).click();

      return new ContactDetailsPage();
    }
  }
}
