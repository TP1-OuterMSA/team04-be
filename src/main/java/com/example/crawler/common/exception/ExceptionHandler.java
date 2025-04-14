package com.example.crawler.common.exception;

import java.io.IOException;

public class ExceptionHandler {

  public static void handle(Exception e) {
    if (e instanceof IOException) {
      handleIOException((IOException) e);
    } else {
      handleGeneralException(e);
    }
  }

  public static void handleIOException(IOException e) {

    System.err.println("IOException 발생: " + e.getMessage());
    e.printStackTrace();
  }

  public static void handleGeneralException(Exception e) {

    System.err.println("일반 예외 발생: " + e.getMessage());
    e.printStackTrace();
  }
}