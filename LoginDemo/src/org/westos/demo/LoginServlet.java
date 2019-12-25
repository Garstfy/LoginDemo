package org.westos.demo;

import com.alibaba.druid.pool.DruidDataSourceFactory;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //处理登录
        //1.获取到用户提交的用户名和密码
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String rember = request.getParameter("rember");
        //获取绝对路径
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/");
        System.out.println(realPath);
        //2.JDBC

        //3.登录失败
     /*   if(){
            response.getWriter().write("登录成功");
        }else{
            response.getWriter().write("登录失败");
        }*/
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(servletContext.getRealPath("/WEB-INF/druid.properties"))));
            DataSource ds = DruidDataSourceFactory.createDataSource(properties);
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();

            String sql = "select * from Users where username=? and passworld=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Cookie uCookie = new Cookie("username", username);
                Cookie pCookie = new Cookie("password", password);
                uCookie.setMaxAge(60*60*24*Integer.parseInt(rember));
                pCookie.setMaxAge(60*60*24*Integer.parseInt(rember));
                System.out.println(rember);
                response.addCookie(uCookie);
                response.addCookie(pCookie);
                response.getWriter().write("welcom");
            }else {
                response.getWriter().write("<script>alert('用户名或密码错误！');history.back();</script>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
