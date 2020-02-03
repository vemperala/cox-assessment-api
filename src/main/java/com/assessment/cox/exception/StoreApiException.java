package com.assessment.cox.exception;

/**
 * Created by akhilesh on 2/2/20.
 */
public class StoreApiException extends RuntimeException{
  public static final long serialVersionUID = 1L;

  private final String message;

  private final int code;

  @Override
  public String getMessage() {
    return message;
  }

  public int getCode() {
    return code;
  }

  public StoreApiException(int code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

}
