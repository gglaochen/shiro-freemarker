<!DOCTYPE html>
<script src="/js/jquery.min.js"></script>
<script src="/js/jquery.cookie.js"></script>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>shiro登录</title>
</head>
<body>
<#if message??>
<b>${message}</b>
</#if>
<form action="/login" id="form-login" method="post">
    用户名:<input type="text" id="username" name="username" placeholder="请输入用户名" minlength="6" maxlength="12" required> <br>
    密码:<input type="text" id="password" name="password" placeholder="请输入密码" minlength="3" maxlength="15" required><br>
    <input type="submit" value="登录" id="bt-login">
</form>
</body>
</html>