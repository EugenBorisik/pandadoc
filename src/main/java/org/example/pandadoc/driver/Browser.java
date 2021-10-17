package org.example.pandadoc.driver;

import lombok.Getter;

import java.util.Arrays;

public enum Browser {

  CHROME("chrome"),
  FIREFOX("firefox"),
  EDGE("edge");

  @Getter
  private final String value;

  Browser(String value) {
    this.value = value;
  }

  public static Browser of(String value) {
    return Arrays.stream(Browser.values()).filter(browser -> browser.getValue().equals(value))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Unknown browser " + value));
  }
}
