package bitcamp.pms.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.MemberDao;

@Controller("/member/delete") //객체 저장할 때 어떤 이름으로 저장할지. value="/member/delete"
public class MemberDeleteController{
    MemberDao memberDao;
    
    public MemberDeleteController() {}
    
    public MemberDeleteController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    @RequestMapping
    public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
            memberDao.delete(request.getParameter("id"));
            return "redirect:list";
           
    }

}
