package itcast.web.Cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/CookieTest")
public class CookieTest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*在服务器中的Servlet判断是否有一个名为lastTime的cookie
        1、有：不是第一次访问
        1、响应数据：欢迎回来，您上一次的访问时间为：2018年6月10日11：50：20
        2、回写Cookie：lastTime=2018年6月10日23：50：20
*/
        response.setContentType("text/html;charset=utf-8");
        Cookie[] cookies = request.getCookies();

        boolean flag=false;//没有cookie设置false
        if(cookies!=null&&cookies.length>0){
            for(Cookie cookie:cookies){
                String name = cookie.getName();
                if("lastName".equals(name)){
                    flag=true;//有lastTime的cookie
                    // 响应数据

                    String value = cookie.getValue();
                    String decodeStr = URLDecoder.decode(value, "utf-8");
                    response.getWriter().write("<h1>欢迎回来，您上次访问时间为："+decodeStr+"</h1>");
                    //设置Cookie的value
                    //获取当前时间的字符串，重新设置cookie的值，重新发送cookie
                    Date date=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    String str_date = sdf.format(date);
                    //使用URL编码
                    System.out.println("编码前："+str_date);
                    str_date= URLEncoder.encode(str_date, "utf-8");
                    System.out.println("编码后"+str_date);
                    cookie.setValue(str_date);
                    //设置cookie的存活时间
                    cookie.setMaxAge(60*60*24*30);//一个月
                    response.addCookie(cookie);
                    break;
                }

            }

        }
        if(cookies==null||cookies.length==0||flag==false){
            //第一次访问
            //获取当前时间的字符串，重新设置cookie的值，重新发送cookie
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String str_date = sdf.format(date);
            String str_date2 = URLEncoder.encode(str_date, "utf-8");
            Cookie cookie=new Cookie("lastName",str_date2);
            cookie.setMaxAge(60*60*24*30);//一个月
            response.addCookie(cookie);
            response.getWriter().write("<h1>欢迎首次访问</h1>");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
