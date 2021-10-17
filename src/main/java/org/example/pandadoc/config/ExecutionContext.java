package org.example.pandadoc.config;

import java.io.IOException;
import java.util.Properties;

public final class ExecutionContext {

  private static final Properties PROPS;

  static {
    PROPS = new Properties();
    try {
      PROPS.load(ExecutionContext.class.getResourceAsStream("/application.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String get(String key) {
    return PROPS.getProperty(key);
  }
}
