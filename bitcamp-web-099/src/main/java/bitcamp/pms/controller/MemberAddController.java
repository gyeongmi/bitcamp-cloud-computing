package bitcamp.pms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

public class MemberAddController {
    
    MemberDao memberDao;
    public MemberAddController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @RequestMapping
    public String add(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        if(request.getMethod().equals("GET")) {
            //get과 post를 구분할 수 있다
            //get일 경우 실행한다
            return "/member/form.jsp";
           
        }
        Member member = new Member();
        member.setId(request.getParameter("id"));
        member.setEmail(request.getParameter("email"));
        member.setPassword(request.getParameter("password"));
        
        memberDao.insert(member);
        return "redirect:list";
    }

}
