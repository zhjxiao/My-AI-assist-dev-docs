package com.demo.amis;

import java.util.UUID;

public class Trace {
  private static final ThreadLocal<String> TL = new ThreadLocal<String>();
  public static String traceId() {
    String v = TL.get();
    if (v == null) {
      v = UUID.randomUUID().toString().replace("-", "");
      TL.set(v);
    }
    return v;
  }
  public static void clear() { TL.remove(); }
}
