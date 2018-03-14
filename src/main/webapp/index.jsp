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
    </body>
</html>
