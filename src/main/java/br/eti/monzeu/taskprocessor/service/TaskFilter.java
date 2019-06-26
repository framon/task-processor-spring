package br.eti.monzeu.taskprocessor.service;

import java.time.Instant;
import java.util.UUID;

/**
 * Task Filter parameter object. Besides the builder pattern, it is not immutable.
 */
public class TaskFilter {

  private UUID id;
  private String user;
  private String name;
  private Instant modifiedSince;

  public TaskFilter id(UUID id) {
    this.id = id;
    return this;
  }

  public TaskFilter user(String user) {
    this.user = user;
    return this;
  }

  public TaskFilter name(String name) {
    this.name = name;
    return this;
  }

  public TaskFilter modifiedSince(Instant modifiedSince) {
    this.modifiedSince = modifiedSince;
    return this;
  }
}
