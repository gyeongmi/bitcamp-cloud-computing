package bitcamp.pms.servlet.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>멤버 목록</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>멤버 목록</h1>");
        
        out.println("<p><a href='form.html'>새회원</a></p>");
        out.println("<table border='1'>");
        out.println("<tr>");
        out.println("    <th>아이디</th><th>이메일</th>");
        out.println("</tr>");
        
        try {
            //ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
            //자기가 상속받은 거는 this 생략 가능
            
            
/*            MemberDao memberDao = new MemberDao(
                    "jdbc:mysql://13.124.153.245:3306/studydb",
                    "study", "1111");*/
            
            //ArrayList<Member> list = selectList(); 
            //List<Member> list = MemberDao.selectList(); //memberDao 만들때.
            List<Member> list = memberDao.selectList();
            for(Member member : list ) {
                out.println("<tr>");
                out.printf("    <td><a href='view?id=%s'>%s</a></td><td>%s</td>\n",
                        member.getId(),
                        member.getId(),
                        member.getEmail());
                out.println("</tr>");
            }
         
        } catch (Exception e) {
            out.println("<tr>목록 가져오기 실패!</tr>");
            e.printStackTrace(out);
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
    
/*    private ArrayList<Member> selectList() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        try (
                //String s = "oho"; -> 이것을 try안에 객체를 넣으면 오류가남
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://13.124.153.245:3306/studydb",
                    "study", "1111");
                PreparedStatement stmt = con.prepareStatement(
                    "select mid, email from pms2_member");
                ResultSet rs = stmt.executeQuery();) {
                
                ArrayList<Member> list = new ArrayList<>();
                while (rs.next()) {
                    Member member = new Member();
                    member.setId(rs.getString("mid")); 
                    member.setEmail(rs.getString("email"));
                    list.add(member);
                }
                return list;

            }
    }*/
    //-> 메소드
    // DAO만들면 이거 잘라내고 DAO에 복사 붙여넣기
  
}




