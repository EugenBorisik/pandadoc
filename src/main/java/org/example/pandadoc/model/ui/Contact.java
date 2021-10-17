package org.example.pandadoc.model.ui;

import lombok.Data;

@Data
public class Contact {

  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String company;
  private String jobTitle;

  public String getFullName() {
    return getFirstName() + " " + getLastName();
  }
}
