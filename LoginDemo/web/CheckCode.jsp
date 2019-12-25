<%--
  Created by IntelliJ IDEA.
  User: Garstfyx
  Date: 2019/12/18
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/user" method="post">
    <h1>用户注册</h1>
    用户名：<input type="text" name="username" placeholder="请输入用户名"><br>
    密码：<input type="password" name="password" placeholder="请输入密码"><br>
    <img src="${pageContext.request.contextPath}/code"  id="myimg"><br>
    <input type="text" name="checkCode" placeholder="请输入验证码"><br>
    <a href="#" onclick="changeImg()">换一张</a><br>
    <input type="submit" value="注册">
</form>
<script>
    function changeImg() {
        var img = document.getElementById("myimg");
        //304 读取缓存 拼接个随机参数，然后让服务器每次都得响应
        img.src = "${pageContext.request.contextPath}/code?time=" + new Date().getTime();
    }

</script>
</body>
</html>
