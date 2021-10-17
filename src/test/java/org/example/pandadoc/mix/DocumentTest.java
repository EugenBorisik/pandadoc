package org.example.pandadoc.mix;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.pandadoc.BaseUiTest;
import org.example.pandadoc.api.DocumentClient;
import org.example.pandadoc.model.api.CreateDocumentRequest;
import org.example.pandadoc.model.api.CreateDocumentResponse;
import org.example.pandadoc.model.api.Recipient;
import org.example.pandadoc.pageobject.DashboardPage;
import org.example.pandadoc.pageobject.LoginPage;
import org.example.pandadoc.pageobject.document.DocumentConstructorPage;
import org.example.pandadoc.pageobject.document.DocumentsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;

public class DocumentTest extends BaseUiTest {

  private static final String EXPECTED_STATUS = "Document has been sent";

  private final File testDocument = new File(getClass().getClassLoader().getResource("panda.png").getFile());
  private CreateDocumentResponse createDocumentResponse;

  @BeforeMethod
  public void setup() {
    Recipient recipient = new Recipient();
    recipient.setEmail(RandomStringUtils.randomAlphabetic(5) + "@test.com");

    CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest();
    createDocumentRequest.setName(RandomStringUtils.randomAlphabetic(10));
    createDocumentRequest.setRecipients(Collections.singletonList(recipient));

    createDocumentResponse = DocumentClient.create(testDocument, createDocumentRequest, 201);
  }

  @Feature("Document")
  @Description("[UI+API] Verify the ability to send document")
  @Test
  public void sendDocumentTest() {
    DashboardPage dashboardPage = new LoginPage().login(USERNAME, PASSWORD);
    DocumentsPage documentsPage = dashboardPage.getLeftMenu().openDocuments();
    DocumentConstructorPage documentConstructorPage = documentsPage.openConstructor(createDocumentResponse.getName())
        .sendDocument();

    Assert.assertEquals(documentConstructorPage.getSendDocumentComponent().getStatusLabel(), EXPECTED_STATUS);
  }

  @AfterMethod
  public void cleanup() {
    DocumentClient.delete(createDocumentResponse.getId(), 204);
    super.cleanup();
  }
}
