package bitcamp.pms.servlet.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
/*        String id = ;
        String email = request.getParameter("email");
        String password =request.getParameter("password");*/
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta http-equiv='Refresh' content='1;url=list'>");
        out.println("<title>회원 변경</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>회원 변경 결과</h1>");
        
        try {
            //int count = memberDao.update(member);
            Class.forName("com.mysql.jdbc.Driver");
            try (
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://13.124.153.245:3306/studydb",
                    "study", "1111");
                PreparedStatement stmt = con.prepareStatement(
                    "update pms2_member set email=?, pwd=password(?) where mid=?");) {
                //sha2 x password
                stmt.setString(1, request.getParameter("email"));
                stmt.setString(2, request.getParameter("password"));
                stmt.setString(3, request.getParameter("id"));
                //int count =  stmt.executeUpdate(); 한번밖에 쓸 변수를 만들지 않는다*/ 
                if (stmt.executeUpdate() == 0) {
                    out.println("<p>해당 회원이 존재하지 않습니다.</p>");
                } else {
                    out.println("<p>변경하였습니다.</p>");
                }
            }
        } catch (Exception e) {
            out.println("<p>변경 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
    
    }
}
