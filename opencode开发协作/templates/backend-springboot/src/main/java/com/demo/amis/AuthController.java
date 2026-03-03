package com.demo.amis;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final DemoStore store;

  public AuthController(DemoStore store) {
    this.store = store;
  }

  @GetMapping("/me")
  public ApiResponse<Map<String,Object>> me() {
    String userId = "u1"; // demo current user
    Set<String> roleIds = store.userRoles.getOrDefault(userId, new HashSet<String>());
    Set<String> perms = new HashSet<String>();
    for (String rid : roleIds) perms.addAll(store.rolePerms.getOrDefault(rid, new HashSet<String>()));

    Map<String,Object> data = new HashMap<String,Object>();
    data.put("id", userId);
    data.put("name", "DemoAdmin");
    data.put("permissions", new ArrayList<String>(perms));
    Map<String,Object> defaultScope = new HashMap<String,Object>();
    defaultScope.put("scopeType","ORG_AND_CHILD");
    data.put("defaultScope", defaultScope);
    return ApiResponse.ok(data);
  }
}
