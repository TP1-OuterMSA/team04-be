package com.example.crawler.common.exception;

import java.sql.SQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<String> handleNullPointerException() {
    return error("필수 데이터가 누락되었습니다.");
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<String> handleSQLException() {
    return error("데이터베이스 오류가 발생했습니다.");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException() {
    return error("알 수 없는 오류가 발생했습니다.");
  }

  private ResponseEntity<String> error(String message) {
    return ResponseEntity.status(500).body(message);
  }
}
