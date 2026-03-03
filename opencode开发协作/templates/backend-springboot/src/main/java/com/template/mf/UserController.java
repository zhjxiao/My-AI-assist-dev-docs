package com.template.mf;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
@RestController @RequestMapping("/api/users")
public class UserController {
  private final Store store;
  public UserController(Store s){this.store=s;}
  @GetMapping
  public ApiResponse<PageResult<Store.User>> list(@RequestParam(value="pageNo",defaultValue="1") int pageNo,@RequestParam(value="pageSize",defaultValue="20") int pageSize,@RequestParam(value="keyword",required=false) String keyword){
    List<Store.User> all=new ArrayList<>(store.users.values());
    if(keyword!=null && keyword.trim().length()>0){ final String k=keyword.trim().toLowerCase(); all=all.stream().filter(u->(u.username!=null && u.username.toLowerCase().contains(k))||(u.realName!=null && u.realName.toLowerCase().contains(k))).collect(Collectors.toList()); }
    all.sort(Comparator.comparing(u->u.id));
    int from=Math.max(0,(pageNo-1)*pageSize), to=Math.min(all.size(),from+pageSize);
    List<Store.User> page=from>=all.size()?new ArrayList<>():all.subList(from,to);
    return ApiResponse.ok(new PageResult<>(page, all.size()));
  }
  @PostMapping public ApiResponse<Store.User> create(@RequestBody Map<String,Object> body){
    Store.User u=new Store.User(); u.id="u"+(store.users.size()+1); u.username=String.valueOf(body.get("username")); u.realName=String.valueOf(body.get("realName"));
    u.orgId=String.valueOf(body.get("orgId")); u.status=String.valueOf(body.get("status")); u.phone=String.valueOf(body.get("phone")); u.createdAt=java.time.OffsetDateTime.now().toString();
    store.users.put(u.id,u); return ApiResponse.ok(u);
  }
}
