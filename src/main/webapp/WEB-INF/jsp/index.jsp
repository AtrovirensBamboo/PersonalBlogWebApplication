<%--
  Created by IntelliJ IDEA.
  User: 89288
  Date: 2018/5/6
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <link href="css/index.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="javascript/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="javascript/submit.js"></script>
    <title>登录</title>
</head>

<body>
<h1>图书管理系统</h1>
<form id="submitform" name="userSubmit">
    <div>Username:<input id="username" type="text" name="username" placeholder="邮箱/用户名" /></div>
    <br />
    <div>Password:<input id="password" type="password" name="password" placeholder="密码" /></div>
    <br />
    <input type="button" id="submit" onclick="submitForm()" value="登录" />
    <span><button type="button" onClick="window.location.href='/html/regist.html'">注册</button></span>
</form>
</body>
</html>