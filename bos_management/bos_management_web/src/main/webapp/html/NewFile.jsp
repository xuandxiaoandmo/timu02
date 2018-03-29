<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
你好

<shiro:authenticated>
    你登录了
</shiro:authenticated>
<shiro:hasPermission name="areaAction_pageFindAll">

  areaAction_pageFindAll权限
</shiro:hasPermission>
<shiro:hasPermission name="info">

  info权限
</shiro:hasPermission>


<shiro:hasRole name="admin">
admin角色权限
</shiro:hasRole>

<shiro:hasRole name="override">
override角色权限
</shiro:hasRole>



</body>
</html>