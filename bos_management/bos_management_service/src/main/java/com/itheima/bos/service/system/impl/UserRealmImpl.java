package com.itheima.bos.service.system.impl;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.system.Permission;
import com.itheima.bos.system.Role;
import com.itheima.bos.system.User;

/**
 * ClassName:UserRealmImpl <br/>
 * Function: <br/>
 * Date: 2018年3月26日 下午1:39:12 <br/>
 */
@Component
@Transactional
public class UserRealmImpl extends AuthorizingRealm implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	
	
	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//Subject subject=SecurityUtils.getSubject();
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		//创建授权对象
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		if("admin".equals(user.getUsername())){
			List<Role> findAll = roleRepository.findAll();
			for (Role role : findAll) {
				info.addRole(role.getKeyword());
			}
			
			
			List<Permission> findAll2 = permissionRepository.findAll();
			for (Permission permission : findAll2) {
				info.addStringPermission(permission.getKeyword());
			}
			return info;
		}else{
			List<Permission>permissions=permissionRepository.queryByUid(user.getId());
			
			List<Role>roles=roleRepository.queryByUid(user.getId());
			
			for (Role role : roles) {
				info.addRole(role.getKeyword());
			}
			for (Permission permission : permissions) {
				info.addStringPermission(permission.getKeyword());
			}
			return info;
		}
		
		
	}

	//认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken passwordToken=(UsernamePasswordToken)token;
		
		String username = passwordToken.getUsername();
		User user = userRepository.findByUsername(username);
		
		if (user != null) {
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
			return simpleAuthenticationInfo;
		} 
		
		return null;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public void save(User model) {
		userRepository.save(model);
	}
}
