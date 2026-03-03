package com.demo.amis;

public class ApiResponse<T> {
  public int code;
  public String message;
  public String traceId;
  public T data;

  public static <T> ApiResponse<T> ok(T data) {
    ApiResponse<T> r = new ApiResponse<T>();
    r.code = 0;
    r.message = "OK";
    r.traceId = Trace.traceId();
    r.data = data;
    return r;
  }

  public static <T> ApiResponse<T> fail(int code, String message) {
    ApiResponse<T> r = new ApiResponse<T>();
    r.code = code;
    r.message = message;
    r.traceId = Trace.traceId();
    r.data = null;
    return r;
  }
}
