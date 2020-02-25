<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8">
    <title>管理员界面</title>
</head>

<body>
<@shiro.hasPermission name="admin::item">
    <li> 拥有admin::index权限</li>
</@shiro.hasPermission>

<@shiro.hasRole name="admin">
    <li>用户[<@shiro.principal/>]拥有角色admin</li>
</@shiro.hasRole>
</body>