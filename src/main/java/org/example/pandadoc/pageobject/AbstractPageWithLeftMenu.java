package org.example.pandadoc.pageobject;

import lombok.Getter;

public abstract class AbstractPageWithLeftMenu extends AbstractPage {

  @Getter
  private final LeftMenu leftMenu;

  public AbstractPageWithLeftMenu() {
    leftMenu = new LeftMenu();
  }
}
