<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/3/12
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>show all users test</title>
    </head>
    <body>
        <table border="1" cellspacing="0" cellpadding="10">
            <tr>
                <th>ID</th>
                <th>name</th>
                <th>pwd</th>
                <th>auth</th>
                <th>picture_url</th>
            </tr>

            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <th>${user.id}</th>
                    <th>${user.userName}</th>
                    <th>${user.pwd}</th>
                    <th>${user.auth}</th>
                    <th>${user.pictureUrl}</th>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
