package com.template.mf;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Store {
  public static class User { public String id,username,realName,orgId,status,phone,createdAt; }
  public static class Role { public String id,name,code,remark; }
  public static class MenuNode { public String id,title,type,path,routeKey,schemaRef; public Integer orderNo; public List<MenuNode> children=new ArrayList<>(); }
  public static class MenuAction { public String key,title; public boolean enabled; public MenuAction(){} public MenuAction(String k,String t,boolean e){key=k;title=t;enabled=e;} }
  public static class FieldPolicy { public String resource,field,target,mode,when; public Integer priority; }
  public static class ActionPolicy { public String resource,actionKey,target,mode,when,disabledReason,confirmText; public Integer priority; }

  public final Map<String,User> users=new ConcurrentHashMap<>();
  public final Map<String,Role> roles=new ConcurrentHashMap<>();
  public final Map<String,Set<String>> rolePerms=new ConcurrentHashMap<>();
  public final Map<String,Set<String>> userRoles=new ConcurrentHashMap<>();
  public final Map<String,MenuNode> menus=new ConcurrentHashMap<>();
  public final Map<String,List<MenuAction>> menuActions=new ConcurrentHashMap<>();
  public final Map<String,List<FieldPolicy>> menuFieldPolicies=new ConcurrentHashMap<>();
  public final Map<String,List<ActionPolicy>> menuActionPolicies=new ConcurrentHashMap<>();

  public Store(){
    Role admin=new Role(); admin.id="r1"; admin.name="管理员"; admin.code="ADMIN"; roles.put(admin.id,admin);
    rolePerms.put(admin.id,new HashSet<>(Arrays.asList("platform:caps","auth:me","user:query","user:create","user:detail","user:update","user:delete","menu:tree","menu:action:read","menu:action:write","menu:field:read","menu:field:write","menu:actionPolicy:read","menu:actionPolicy:write","dict:query")));
    for(int i=1;i<=7;i++){ User u=new User(); u.id="u"+i; u.username="user"+i; u.realName="用户"+i; u.orgId=(i%2==0)?"ORG-IT":"ORG-FIN"; u.status=(i%3==0)?"DISABLED":"ENABLED"; u.phone="1380000"+String.format("%04d",i); u.createdAt=OffsetDateTime.now().minusDays(i).toString(); users.put(u.id,u); }
    userRoles.put("u1", new HashSet<>(Collections.singletonList("r1")));
    putMenu("m1","用户管理","/admin/users","user.list",10);
    putMenu("m2","审批中心","/flow/approvals","approval.list",20);
    putMenu("m3","角色权限","/sys/roles","rbac.role.list",90);
    putMenu("m4","菜单管理","/sys/menus","sys.menu.list",95);
  }
  private void putMenu(String id,String title,String path,String routeKey,int order){ MenuNode m=new MenuNode(); m.id=id;m.title=title;m.type="MENU";m.path=path;m.routeKey=routeKey;m.orderNo=order; menus.put(id,m); }
  public List<MenuNode> menuTree(){ List<MenuNode> list=new ArrayList<>(menus.values()); list.sort(Comparator.comparingInt(a->a.orderNo==null?999:a.orderNo)); return list; }
}
