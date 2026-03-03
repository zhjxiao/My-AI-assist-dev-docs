package com.demo.amis;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dicts")
public class DictController {

  @GetMapping("/{code}")
  public ApiResponse<List<Map<String,String>>> dict(@PathVariable("code") String code) {
    List<Map<String,String>> items = new ArrayList<Map<String,String>>();
    if ("sys_user_status".equals(code)) {
      items.add(item("启用","ENABLED"));
      items.add(item("禁用","DISABLED"));
    } else {
      items.add(item(code+"-A","A"));
      items.add(item(code+"-B","B"));
    }
    return ApiResponse.ok(items);
  }

  private Map<String,String> item(String label, String value) {
    Map<String,String> m = new HashMap<String,String>();
    m.put("label", label);
    m.put("value", value);
    return m;
  }
}
