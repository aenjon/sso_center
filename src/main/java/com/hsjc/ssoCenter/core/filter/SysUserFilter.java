package com.hsjc.ssoCenter.core.filter;

import com.hsjc.ssoCenter.core.constant.Constant;
import com.hsjc.ssoCenter.core.domain.Role;
import com.hsjc.ssoCenter.core.domain.UserMain;
import com.hsjc.ssoCenter.core.service.ResourceService;
import com.hsjc.ssoCenter.core.service.RoleService;
import com.hsjc.ssoCenter.core.util.MenuUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @author : zga
 * @date : 2016-3-22
 *
 * 用户过滤器
 *
 */
public class SysUserFilter extends PathMatchingFilter {
    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @Override
    protected boolean onPreHandle(ServletRequest request,
                                  ServletResponse response,
                                  Object mappedValue) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        UserMain userMain = (UserMain) subject.getPrincipal();

        Session session = subject.getSession(true);
        session.setAttribute(Constant.CURRENT_USER,userMain);

        if(subject.hasRole("admin") || subject.hasRole("superAdmin")){
            List<HashMap> list = null;
            Long userId = Long.parseLong(userMain.getId().toString());
            Role role = roleService.selectRoleByUserId(userId);
            if(role != null){
                /**
                 * 添加用户的资源
                 */
                list = resourceService.selectResourcesByRoleId(userId);
            }

            List<HashMap> firstMenuList = MenuUtil.getMenuList(list);

            session.setAttribute("firstMenuList",firstMenuList);
            session.setAttribute("resourceList",list);
        }
        return true;
    }
}
