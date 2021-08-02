package com.learn.dropwizard.model;

import com.learn.dropwizard.model.Person;
import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2021-08-02T18:39:59.630914900+03:00[Europe/Moscow]")public class ReadPerson   {
  
  private @Valid Integer id;
  private @Valid String email;
  private @Valid String name;
  private @Valid String surname;

  /**
   **/
  public ReadPerson id(Integer id) {
    this.id = id;
    return this;
  }

  

  
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

/**
   **/
  public ReadPerson email(String email) {
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
  public ReadPerson name(String name) {
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
  public ReadPerson surname(String surname) {
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
    ReadPerson readPerson = (ReadPerson) o;
    return Objects.equals(this.id, readPerson.id) &&
        Objects.equals(this.email, readPerson.email) &&
        Objects.equals(this.name, readPerson.name) &&
        Objects.equals(this.surname, readPerson.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, name, surname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReadPerson {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

