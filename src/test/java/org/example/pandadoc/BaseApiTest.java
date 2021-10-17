package org.example.pandadoc;

import org.example.pandadoc.api.DocumentTest;

import java.io.File;

public abstract class BaseApiTest {

  protected static final File TEST_DOCUMENT = new File(DocumentTest.class.getClassLoader().getResource("panda.png").getFile());

  protected static final String SANDBOX_PREFIX = "[DEV]";
}
