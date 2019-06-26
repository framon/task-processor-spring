package br.eti.monzeu.taskprocessor;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

public class TaskStatus<T> {

  private String code;

  private String user;
  private Instant createdAt;
  private Instant modifiedAt;

  private String name;
  private Status status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T result;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Instant modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }
}
