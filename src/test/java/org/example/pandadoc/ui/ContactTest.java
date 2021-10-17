package org.example.pandadoc.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.pandadoc.BaseUiTest;
import org.example.pandadoc.model.ui.Contact;
import org.example.pandadoc.pageobject.DashboardPage;
import org.example.pandadoc.pageobject.LoginPage;
import org.example.pandadoc.pageobject.contact.ContactsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactTest extends BaseUiTest {

  private Contact contact;

  @BeforeMethod
  public void setup() {
    contact = new Contact();

    contact.setFirstName(RandomStringUtils.randomAlphabetic(5));
    contact.setLastName(RandomStringUtils.randomAlphabetic(5));
    contact.setEmail(RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@test.com");
    contact.setPhone(RandomStringUtils.randomNumeric(8));
    contact.setCompany("Test company");
    contact.setJobTitle("Test title");
  }

  @Feature("Contact")
  @Description("[UI] Verify the ability to create contact")
  @Test
  public void createNewContactTest() {
    DashboardPage dashboardPage = new LoginPage().login(USERNAME, PASSWORD);
    ContactsPage contactsPage = dashboardPage.getLeftMenu().openContacts();

    Assert.assertTrue(contactsPage.createNewContact(contact).isContactDisplayed(contact),
        String.format("Contact %s is not displayed", contact));
  }

  @AfterMethod
  public void cleanup() {
    DashboardPage dashboardPage = new DashboardPage();
    ContactsPage contactsPage = dashboardPage.getLeftMenu().openContacts();
    contactsPage.deleteContact(contact);

    super.cleanup();
  }
}
