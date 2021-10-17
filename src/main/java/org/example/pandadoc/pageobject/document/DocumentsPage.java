package org.example.pandadoc.pageobject.document;

import org.example.pandadoc.pageobject.AbstractPageWithLeftMenu;
import org.openqa.selenium.By;

public class DocumentsPage extends AbstractPageWithLeftMenu {

  private static final String DOCUMENT_LINK_BY_NAME = "//a[contains(@href, '#/documents/') and contains(normalize-space(), '%s')]";

  public DocumentConstructorPage openConstructor(String name) {
    driver.findElement(By.xpath(String.format(DOCUMENT_LINK_BY_NAME, name))).click();

    return new DocumentConstructorPage();
  }
}
