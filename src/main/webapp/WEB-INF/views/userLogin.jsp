<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/3/19
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<h2>Login</h2>
<form:form method="POST" action="login">
    <table>
        <tr>
            <td>用户名：</td>
            <td><input id="userName" name="userName" type="text"/></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input id="pwd" name="pwd" type="password"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="登录"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
