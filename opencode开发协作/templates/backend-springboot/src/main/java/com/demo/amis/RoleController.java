package com.demo.amis;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

  private final DemoStore store;

  public RoleController(DemoStore store) {
    this.store = store;
  }

  @GetMapping
  public ApiResponse<PageResult<DemoStore.Role>> list(
      @RequestParam(value="pageNo", defaultValue="1") int pageNo,
      @RequestParam(value="pageSize", defaultValue="20") int pageSize,
      @RequestParam(value="keyword", required=false) String keyword
  ) {
    List<DemoStore.Role> all = new ArrayList<DemoStore.Role>(store.roles.values());
    if (keyword != null && keyword.trim().length() > 0) {
      final String k = keyword.trim().toLowerCase();
      all = all.stream().filter(r -> (r.name!=null && r.name.toLowerCase().contains(k)) || (r.code!=null && r.code.toLowerCase().contains(k)))
        .collect(Collectors.toList());
    }
    all.sort(Comparator.comparing(r -> r.id));
    int from = Math.max(0, (pageNo-1)*pageSize);
    int to = Math.min(all.size(), from + pageSize);
    List<DemoStore.Role> page = from >= all.size() ? new ArrayList<DemoStore.Role>() : all.subList(from, to);
    return ApiResponse.ok(new PageResult<DemoStore.Role>(page, all.size()));
  }

  @PostMapping
  public ApiResponse<DemoStore.Role> create(@RequestBody Map<String,Object> body) {
    DemoStore.Role r = new DemoStore.Role();
    r.id = "r" + (store.roles.size()+1);
    r.name = str(body.get("name"));
    r.code = str(body.get("code"));
    r.remark = str(body.get("remark"));
    store.roles.put(r.id, r);
    store.rolePerms.put(r.id, new HashSet<String>());
    return ApiResponse.ok(r);
  }

  @GetMapping("/{id}/permissions")
  public ApiResponse<List<String>> getPerms(@PathVariable("id") String id) {
    Set<String> perms = store.rolePerms.getOrDefault(id, new HashSet<String>());
    return ApiResponse.ok(new ArrayList<String>(perms));
  }

  @PutMapping("/{id}/permissions")
  public ApiResponse<Object> setPerms(@PathVariable("id") String id, @RequestBody Map<String,Object> body) {
    Object permsObj = body.get("permissions");
    Set<String> perms = new HashSet<String>();
    if (permsObj instanceof List) {
      for (Object o : (List<?>)permsObj) perms.add(String.valueOf(o));
    }
    store.rolePerms.put(id, perms);
    return ApiResponse.ok(null);
  }

  private String str(Object o) { return o == null ? null : String.valueOf(o); }
}
