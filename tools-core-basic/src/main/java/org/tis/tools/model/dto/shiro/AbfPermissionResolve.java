package org.tis.tools.model.dto.shiro;


import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

public class AbfPermissionResolve implements PermissionResolver {

    @Override
    public Permission resolvePermission(String permissionString) {
        // TODO 数据行列权限解析器
        /*if(permissionString.startsWith("+")) {
            return new BehaviorPermission(permissionString);
        }*/
        return new BehaviorPermission(permissionString);
    }
}
