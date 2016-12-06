package com.hsjc.ssoCenter.core.authrealm;

import com.hsjc.ssoCenter.core.domain.Role;
import com.hsjc.ssoCenter.core.domain.UserMain;
import com.hsjc.ssoCenter.core.service.ApiBaseService;
import com.hsjc.ssoCenter.core.service.ResourceService;
import com.hsjc.ssoCenter.core.service.RoleService;
import com.hsjc.ssoCenter.core.service.UserMainService;
import com.hsjc.ssoCenter.core.util.PasswordUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2015-11-24
 *
 * 认证、授权类
 *
 */
@SuppressWarnings("ALL")
public class MyAuthRealm extends AuthorizingRealm {

    ApplicationContext applicationContext;

    @Resource
    UserMainService userMainService;

    @Resource
    ApiBaseService apiBaseService;

    @Resource
    RoleService roleService;

    @Resource
    ResourceService resourceService;

    public void setAc(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public UserMainService getUserMainService() {
        return (userMainService == null) ? (UserMainService)applicationContext.getBean("userService") : userMainService;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());

        UserMain userMain = userMainService.findByEmailOrPhoneOrUserName(username);
        SimpleAuthenticationInfo simpleAuthenticationInfo;

        if (userMain == null) {
            throw new UnknownAccountException("No account found for user[" + token.getUsername() + "]");
        }

        if(!"xxxxxx".equals(password)){
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(userMain, userMain.getPassword(),
                    ByteSource.Util.bytes(userMain.getCredentialsSalt()),getName());
        } else {
            PasswordUtil passwordUtil = new PasswordUtil();
            userMain.setPassword("xxxxxx");
            passwordUtil.encryptPassword(userMain);

            simpleAuthenticationInfo = new SimpleAuthenticationInfo(userMain, userMain.getPassword(),
                    ByteSource.Util.bytes(userMain.getCredentialsSalt()),getName());
        }

        return simpleAuthenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserMain userMain = (UserMain) principalCollection.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(userMain != null){
            Long userId = Long.parseLong(userMain.getId().toString());
            Role role = roleService.selectRoleByUserId(userId);

            if(role != null){
                /**
                 * 添加用户的角色
                 */
                info.addRole(role.getRoleKey());

                /**
                 * 添加用户的资源
                 */
                List<HashMap> list = resourceService.selectResourcesByRoleId(userId);
                for(HashMap hashMap : list){
                    info.addStringPermission(hashMap.get("resKey").toString());
                }

                if("user".equals(role.getRoleKey())){
                    List<HashMap> list1 = resourceService.selectResourcesByUserId(userId);
                    for(HashMap hashMap : list1){
                        info.addStringPermission(hashMap.get("resKey").toString());
                    }
                }
            }
        }
        return info;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
