package org.example.pandadoc.model.api;

import lombok.Data;

import java.util.List;

@Data
public class CreateDocumentRequest {

  private String name;
  private List<Recipient> recipients;
}
