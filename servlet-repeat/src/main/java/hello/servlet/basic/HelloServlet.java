package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    /**
     * HTTP 요청을 통해 매핑된 url이 호출되면 서블릿 컨테이넌 밑의 service 메서드를 실행한다.
     * HttpServletRequest 역할 : 개발자가 직접 HTTP요청 메시지를 개발자가 직접 파싱해서 사용해도 되지만, 불편하다
     * 서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱한다. 그 결과를 HttpServletRequest 객체에 담아 제공
     * 특징 : 임시 저정소 기능, 세션 관리 기능
     */

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // 헤더정보
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //바디
        response.getWriter().write("hello " + username);
    }
}
