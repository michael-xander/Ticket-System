<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
  <body>
    You must log in to access the query site. <br /><br />
    <c:if test="${loginFailed}">
        <b>The username and password you entered are not correct. Please try again.</b>
        <br /><br />
    </c:if>
    <form method="POST" action="<c:url value="/login" />">
        UserID<br />
        <input type="text" name="userID"/><br /><br />
        Password<br />
        <input type="password" name="password" /><br /><br />
        <input type="submit" value="Log In" />
    </form>
  </body>
</html>
