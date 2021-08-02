package com.learn.dropwizard.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2021-08-02T18:39:59.630914900+03:00[Europe/Moscow]")public class Person   {
  
  private @Valid String email;
  private @Valid String name;
  private @Valid String surname;

  /**
   **/
  public Person email(String email) {
    this.email = email;
    return this;
  }

  

  
  @JsonProperty("email")
  @NotNull
 @Size(min=11,max=25)  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

/**
   **/
  public Person name(String name) {
    this.name = name;
    return this;
  }

  

  
  @JsonProperty("name")
  @NotNull
 @Size(min=2,max=20)  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

/**
   **/
  public Person surname(String surname) {
    this.surname = surname;
    return this;
  }

  

  
  @JsonProperty("surname")
  @NotNull
 @Size(min=2,max=30)  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(this.email, person.email) &&
        Objects.equals(this.name, person.name) &&
        Objects.equals(this.surname, person.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, name, surname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

