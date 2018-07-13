package bitcamp.pms.servlet.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");

        // 센드리다이렉트 할거기 떄문에 response.setContentType("text/html;charset=UTF-8"); 삭제

        try {
            MemberDao memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
            Member member = new Member();
            member.setId(request.getParameter("id"));
            member.setEmail(request.getParameter("email"));
            member.setPassword(request.getParameter("password"));

            if (memberDao.update(member) == 0) {
                request.setAttribute("view", "/member/updatefail.jsp");
            } else {
                request.setAttribute("view", "redirect:list");
            }
           
        } catch (Exception e) {
            request.setAttribute("error", e);
        }
    }
}
