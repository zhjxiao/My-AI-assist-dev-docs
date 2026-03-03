package com.template.mf;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
  private final Store store;
  public AuthController(Store s){this.store=s;}
  @GetMapping("/me")
  public ApiResponse<Map<String,Object>> me(){
    String userId="u1";
    Set<String> roleIds=store.userRoles.getOrDefault(userId,new HashSet<>());
    Set<String> perms=new HashSet<>();
    for(String rid:roleIds) perms.addAll(store.rolePerms.getOrDefault(rid,new HashSet<>()));
    Map<String,Object> d=new HashMap<>();
    d.put("id",userId); d.put("name","DemoAdmin"); d.put("permissions",new ArrayList<>(perms));
    Map<String,Object> scope=new HashMap<>(); scope.put("scopeType","ORG_AND_CHILD"); d.put("defaultScope",scope);
    return ApiResponse.ok(d);
  }
}
