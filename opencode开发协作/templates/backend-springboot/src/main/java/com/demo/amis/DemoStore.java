package com.demo.amis;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DemoStore {

  public static class User {
    public String id;
    public String username;
    public String realName;
    public String orgId;
    public String status;
    public String phone;
    public String createdAt;
  }

  public static class Role {
    public String id;
    public String name;
    public String code;
    public String remark;
  }

  public static class MenuNode {
    public String id;
    public String title;
    public String type; // MENU
    public String path;
    public String schemaRef;
    public Integer orderNo;
    public List<MenuNode> children = new ArrayList<MenuNode>();
  }

  public static class MenuAction {
    public String key;
    public String title;
    public boolean enabled;
    public MenuAction() {}
    public MenuAction(String key, String title, boolean enabled) {
      this.key = key; this.title = title; this.enabled = enabled;
    }
  }

  public static class FieldPolicy {
    public String resource;
    public String field;
    public String target;
    public String mode;
    public String when;
    public Integer priority;
    public String maskType;
    public String unmaskPermission;
  }

  public static class ActionPolicy {
    public String resource;
    public String actionKey;
    public String target;
    public String mode;
    public String when;
    public Integer priority;
    public String disabledReason;
    public String confirmText;
    public Boolean requireReason;
  }

  public final Map<String, User> users = new ConcurrentHashMap<String, User>();
  public final Map<String, Role> roles = new ConcurrentHashMap<String, Role>();
  public final Map<String, Set<String>> rolePerms = new ConcurrentHashMap<String, Set<String>>();
  public final Map<String, Set<String>> userRoles = new ConcurrentHashMap<String, Set<String>>();

  public final Map<String, MenuNode> menus = new ConcurrentHashMap<String, MenuNode>();
  public final Map<String, List<MenuAction>> menuActions = new ConcurrentHashMap<String, List<MenuAction>>();
  public final Map<String, List<FieldPolicy>> menuFieldPolicies = new ConcurrentHashMap<String, List<FieldPolicy>>();
  public final Map<String, List<ActionPolicy>> menuActionPolicies = new ConcurrentHashMap<String, List<ActionPolicy>>();

  public DemoStore() {
    // roles
    Role admin = new Role(); admin.id="r1"; admin.name="管理员"; admin.code="ADMIN";
    roles.put(admin.id, admin);
    rolePerms.put(admin.id, new HashSet<String>(Arrays.asList(
      "auth:me",
      "user:query","user:create","user:detail","user:update","user:delete","user:role:read","user:role:write",
      "role:query","role:create","role:perm:read","role:perm:write",
      "menu:tree","menu:action:read","menu:action:write","menu:field:read","menu:field:write","menu:actionPolicy:read","menu:actionPolicy:write",
      "dict:query"
    )));

    // users
    for (int i=1;i<=7;i++) {
      User u = new User();
      u.id = "u"+i;
      u.username = "user"+i;
      u.realName = "用户"+i;
      u.orgId = (i%2==0) ? "ORG-IT" : "ORG-FIN";
      u.status = (i%3==0) ? "DISABLED" : "ENABLED";
      u.phone = "1380000" + String.format("%04d", i);
      u.createdAt = OffsetDateTime.now().minusDays(i).toString();
      users.put(u.id, u);
    }
    userRoles.put("u1", new HashSet<String>(Collections.singletonList("r1")));

    // menus
    putMenu("m1","用户管理","/admin/users","/schemas/user/list.json",10);
    putMenu("m2","审批中心","/flow/approvals","/schemas/approval/list.json",20);
    putMenu("m3","角色权限","/sys/roles","/schemas/rbac/role-list.json",90);
    putMenu("m4","菜单管理","/sys/menus","/schemas/menu/list.json",95);

    // default actions for menu m1
    menuActions.put("m1", Arrays.asList(
      new MenuAction("user:query","查询", true),
      new MenuAction("user:create","新增", true),
      new MenuAction("user:update","编辑", true),
      new MenuAction("user:delete","删除", true),
      new MenuAction("user:role:write","分配角色", true),
      new MenuAction("user:export","导出", true)
    ));

    // Field policy demo: mask phone in LIST
    FieldPolicy fp = new FieldPolicy();
    fp.resource="user"; fp.field="phone"; fp.target="LIST"; fp.mode="MASKED"; fp.maskType="phone"; fp.priority=10;
    menuFieldPolicies.put("m1", new ArrayList<FieldPolicy>(Collections.singletonList(fp)));

    // Action policy demo: disable delete when not DISABLED
    ActionPolicy ap = new ActionPolicy();
    ap.resource="user"; ap.actionKey="user:delete"; ap.target="ROW"; ap.mode="DISABLE"; ap.when="row.status!='DISABLED'"; ap.priority=10;
    ap.disabledReason="仅禁用用户可删除（Demo 规则）";
    menuActionPolicies.put("m1", new ArrayList<ActionPolicy>(Collections.singletonList(ap)));
  }

  private void putMenu(String id, String title, String path, String schemaRef, int orderNo) {
    MenuNode m = new MenuNode();
    m.id=id; m.title=title; m.type="MENU"; m.path=path; m.schemaRef=schemaRef; m.orderNo=orderNo;
    menus.put(id, m);
  }

  public List<MenuNode> menuTree() {
    List<MenuNode> list = new ArrayList<MenuNode>(menus.values());
    list.sort(new Comparator<MenuNode>() {
      @Override public int compare(MenuNode a, MenuNode b) {
        return Integer.compare(a.orderNo==null?999:a.orderNo, b.orderNo==null?999:b.orderNo);
      }
    });
    return list;
  }
}
