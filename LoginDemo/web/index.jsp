<%--
  Created by IntelliJ IDEA.
  User: ShenMouMou
  Date: 2019/12/17
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <%
      String username="";
      String password="";
      Cookie[] cookies = request.getCookies();
      if (cookies!=null){
        for (Cookie cookie : cookies) {
          if (cookie.getName().equals("username")){
              username = cookie.getValue();
          }
          if (cookie.getName().equals("password")){
              password = cookie.getValue();
          }
        }
      }
    %>
  <form action="${pageContext.request.contextPath}/login" method="post">
    用户名：<input type="text" name="username" placeholder="请输入用户名" value="<%=username%>"/><br>
    密码：<input type="password" name="password" placeholder="请输入密码" value="<%=password%>"/><br>
    记住密码：<input type="radio" name="rember" value="7" />记住密码
    <input type="radio" name="rember" value="0" />不记住密码<br>
    <input type="submit" value="登录">
    <a href="CheckCode.jsp">注册</a>
  </form>

  </body>
</html>
