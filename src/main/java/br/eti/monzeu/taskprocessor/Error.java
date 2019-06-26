package br.eti.monzeu.taskprocessor;

public class Error {

  private boolean error = true;
  private String message;

  public boolean isError() {
    return error;
  }

  void setError(boolean error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
