package bitcamp.pms.servlet.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
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
        
        // DB에서 가져올 데이터의 페이지 정보
        HashMap<String, Object> params = new HashMap<>();
        if(request.getParameter("page") != null && 
                request.getParameter("size") != null) {
            int page = Integer.parseInt(request.getParameter("page"));
            int size = Integer.parseInt(request.getParameter("size"));
            params.put("startIndex", (page-1) * size);
            params.put("pageSize", size);
        }
        
        // 여기서 할 필요가 없음 response.setContentType("text/html;charset=UTF-8");
        //디스패처 서블릿이 다 처리

        
        try {

            MemberDao memberDao = (MemberDao) getServletContext().getAttribute("memberDao");

            List<Member> list = memberDao.selectList(params);
            
            request.setAttribute("list", list);
            //member dao 리턴하는 list(select list 호출)를 리퀘스트에 담는다.
            //요청이 들어올 때 http 서블릿리퀘스트 객체가 만들어져서 응답할때까지 계속 유지된다
            //aaa라는 값을 저장하면 꺼내 쓸수 있다는 것
            
            //인클루드 하기 위해서 요청 배달자
            request.setAttribute("view", "/member/list.jsp"); // 이 뷰를 실행해주세요 하고 리퀘스트에 담아버림 view란 이름으로 저장
            //보내고 다시 돌아옴  =인클루드
        } catch (Exception e) {
            request.setAttribute("error", e);  //에러를 저장
        }

    }
  
}




