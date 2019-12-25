package org.westos.demo;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

@WebServlet(name = "CheckCodeServlet", value = "/code")
public class CheckCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        int width = 200;
        int height = 100;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        //中间美化图片
        //获取画笔
        Graphics g = img.getGraphics();
        g.setColor(Color.PINK);
        //填充背景
        g.fillRect(0, 0, width, height);
        //画边框
        g.setColor(Color.BLUE);
        //注意减一 因为边框会占1个像素
        g.drawRect(0, 0, width - 1, height - 1);

        //写字
        String str = "abcdefghijklmnopqrstuvwxyz";
        g.setColor(Color.BLACK);
        String s = "";
        g.setFont(new Font("宋体", Font.PLAIN, 30));
        Random random = new Random();

        for (int i = 1; i <= 4; i++) {
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            s += "" + str.charAt(index);
            g.drawString(c + "", width / 5 * i, height / 2);
        }
        System.out.println(s.toCharArray());
        this.getServletContext().setAttribute("checkcode",s);
        //画干扰线
        g.setColor(Color.green);

        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);

            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        //把这张画的图片响应给浏览器
        ImageIO.write(img, "png", response.getOutputStream());
        /*//1.获取到用户提交的用户名和密码
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
        if (usercheckCode.toCharArray().equals(s.toCharArray())) {
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
                    response.getWriter().write("welcom");
                    response.sendRedirect("http://localhost:8080/index.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            response.getWriter().write("<script>alert('验证码错误！');history.back();</script>");
        }*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
