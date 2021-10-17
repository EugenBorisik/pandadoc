package org.example.pandadoc.api;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.pandadoc.BaseApiTest;
import org.example.pandadoc.model.api.CreateDocumentRequest;
import org.example.pandadoc.model.api.CreateDocumentResponse;
import org.example.pandadoc.model.api.Recipient;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

public class DocumentTest extends BaseApiTest {

  private static final String DRAFT_STATUS = "document.draft";

  private CreateDocumentRequest createDocumentRequest;
  private CreateDocumentResponse createDocumentResponse;

  @BeforeMethod
  public void setup() {
    Recipient recipient = new Recipient();
    recipient.setEmail(RandomStringUtils.randomAlphabetic(5) + "@test.com");

    createDocumentRequest = new CreateDocumentRequest();
    createDocumentRequest.setName(RandomStringUtils.randomAlphabetic(10));
    createDocumentRequest.setRecipients(Collections.singletonList(recipient));
  }

  @Feature("Document")
  @Description("[API] Verify the ability to create document")
  @Test
  public void createDocumentTest() {
    createDocumentResponse = DocumentClient.create(TEST_DOCUMENT, createDocumentRequest, 201);

    SoftAssert softAssert = new SoftAssert();

    String expectedName = String.format("%s %s", SANDBOX_PREFIX, createDocumentRequest.getName());
    softAssert.assertNotNull(createDocumentResponse.getId());
    softAssert.assertEquals(createDocumentResponse.getName(), expectedName);

    softAssert.assertAll();
  }

  @AfterMethod
  public void cleanup() {
    DocumentClient.waitForDocumentToHaveStatus(createDocumentResponse.getId(), DRAFT_STATUS);
    DocumentClient.delete(createDocumentResponse.getId(), 204);
  }
}
