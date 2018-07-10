package bitcamp.pms.servlet.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        
        try {

            MemberDao memberDao = (MemberDao) getServletContext().getAttribute("memberDao");

            List<Member> list = memberDao.selectList();
            request.setAttribute("list", list);
            //member dao 리턴하는 list(select list 호출)를 리퀘스트에 담는다.
            //요청이 들어올 때 http 서블릿리퀘스트 객체가 만들어져서 응답할때까지 계속 유지된다
            //aaa라는 값을 저장하면 꺼내 쓸수 있다는 것
            
            //인클루드 하기 위해서 요청 배달자
            RequestDispatcher rd = request.getRequestDispatcher("/member/list.jsp"); //어디로 배달할지 경로
            rd.include(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e);
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp"); //어디로 배달할지 경로
            rd.forward(request, response);
        }

    }
  
}




