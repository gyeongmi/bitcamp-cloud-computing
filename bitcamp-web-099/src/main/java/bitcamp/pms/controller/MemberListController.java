package bitcamp.pms.controller;

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

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller
public class MemberListController {
    /*implements PageController*/
        MemberDao memberDao;
        
        public MemberListController() {}
        
        public MemberListController(MemberDao memberDao) {
            this.memberDao = memberDao;
        }
        
        @RequestMapping
        public String list(
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
            //메소드 이름 자유롭게 service -> list
        
        // DB에서 가져올 데이터의 페이지 정보
        HashMap<String, Object> params = new HashMap<>();
        if(request.getParameter("page") != null && 
                request.getParameter("size") != null) {
            int page = Integer.parseInt(request.getParameter("page"));
            int size = Integer.parseInt(request.getParameter("size"));
            params.put("startIndex", (page-1) * size);
            params.put("pageSize", size);
        }
        

        List<Member> list = memberDao.selectList(params);
        request.setAttribute("list", list);
        
        return "/member/list.jsp";
 
    }
  
}




