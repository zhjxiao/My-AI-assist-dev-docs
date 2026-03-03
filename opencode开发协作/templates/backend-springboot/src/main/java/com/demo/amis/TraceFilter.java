package com.demo.amis;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class TraceFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      Trace.traceId();
      chain.doFilter(request, response);
    } finally {
      Trace.clear();
    }
  }
}
