package com.template.mf;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/platform")
public class PlatformController {
  @GetMapping("/capabilities")
  public ApiResponse<Map<String,Object>> caps(){
    Map<String,Object> d=new HashMap<>();
    d.put("apiVersion","1.0.0");
    d.put("enabledFeatures", Arrays.asList("menu.routeKey","capabilityProbe"));
    d.put("compatibleFrontendMinVersion","1.0.0");
    d.put("notes","Template backend");
    return ApiResponse.ok(d);
  }
}
