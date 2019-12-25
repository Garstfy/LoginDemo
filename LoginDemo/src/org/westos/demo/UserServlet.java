package org.westos.demo;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String s = (String) this.getServletContext().getAttribute("checkcode");
        request.getParameter(s);
        //1.获取到用户提交的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usercheckCode = request.getParameter("checkCode");
        System.out.println(username + "===" + password + "===" + usercheckCode);
        System.out.println(usercheckCode.toCharArray());
        System.out.println(s.toCharArray());
        boolean b = usercheckCode.equals(s);
        System.out.println(b);
        //response.sendRedirect("/index.jsp/");
        //usercheckCode.toCharArray().equals(s.toCharArray())
        if (usercheckCode.equals(s)) {
            //注册逻辑
            //获取绝对路径
            ServletContext servletContext = this.getServletContext();
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(new File(servletContext.getRealPath("/WEB-INF/druid.properties"))));
                DataSource ds = DruidDataSourceFactory.createDataSource(properties);
                Connection connection = ds.getConnection();
                Statement statement = connection.createStatement();
                String sql = "insert into users values(?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                int i = preparedStatement.executeUpdate();
                System.out.println("i=" + i);
                if (i > 1) {
                    response.getWriter().write("<script>alert('注册成功！');history.back();</script>");
                    /*response.sendRedirect("http://localhost:8080/index.jsp");*/
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            response.getWriter().write("<script>alert('验证码错误！');history.back();</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
