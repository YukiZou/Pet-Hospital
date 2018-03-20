<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <head>
        <title>test</title>
    </head>
    <body>
        <h2>SSM框架测试</h2>
        <form:form method="GET" action="api/user/listAll">
            <table>
                <tr>
                    <td>
                        <input type="submit" value="userListall 数据库测试"/>
                    </td>
                </tr>
            </table>
        </form:form>

        <!--h2>Login</h2-->
        <!--form:form method="POST" action="api/user/login">
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
            </table-->
        <!--/form:form-->
    </body>
</html>
