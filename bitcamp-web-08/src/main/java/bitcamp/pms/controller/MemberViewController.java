package bitcamp.pms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

public class MemberViewController {
    
    MemberDao memberDao;
    //의존객체 주입
    public MemberViewController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    @RequestMapping
    public String view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        
        response.setContentType("text/html;charset=UTF-8");
        
            Member member = memberDao.selectOne(id);
            request.setAttribute("member", member);
            return "/member/view.jsp";

        

    }
            
}
