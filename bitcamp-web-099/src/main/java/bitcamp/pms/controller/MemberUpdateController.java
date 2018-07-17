package bitcamp.pms.controller;

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

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller("/member/update") //객체 저장할 때 어떤 이름으로 저장할지. value="/member/update"
public class MemberUpdateController{

        MemberDao memberDao;
        
        public MemberUpdateController() {}
        
        public MemberUpdateController(MemberDao memberDao){
            this.memberDao = memberDao;
        }
        
        @RequestMapping
        public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Member member = new Member();
        member.setId(request.getParameter("id"));
        member.setEmail(request.getParameter("email"));
        member.setPassword(request.getParameter("password"));
        if (memberDao.update(member) == 0) {
            return "/member/updatefail.jsp";
        } else {
            return "redirect:list";
        }
        
    }
}
