package org.example.pandadoc.model.api;

import lombok.Data;

@Data
public class CreateDocumentResponse {

  private String id;
  private String name;
  private String status;
}
