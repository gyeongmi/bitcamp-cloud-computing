package bitcamp.pms.servlet.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/view")
public class MemberViewServlet extends HttpServlet{
    @Override
    // view는 doget
    // response로 utf-8
    // </h1>까지는 list와 똑같음
    // <form action='update' method='post'> -> update 하기 위한 form 양식 
    // id는 getParameter로 가져옴 -> 아마.... list에서 view?id=%s 이거에서 파라미터를 가져온거라서??
    // 그래서 email도 getParmeter로 가져왔는데 null이다... -> rs.getString("email") 로 바꿈
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>멤버 보기</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>멤버 보기</h1>");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try(Connection con = DriverManager.getConnection(
                    "jdbc:mysql://13.124.153.245:3306/studydb", 
                    "study", "1111");
                PreparedStatement stmt = con.prepareStatement(
                    "select mid, email from pms2_member where mid=?");
                ){
                stmt.setString(1, request.getParameter("id"));
                try(ResultSet rs = stmt.executeQuery();){
                    if(!rs.next()) {
                        out.println("<p>유효하지 않은 멤버입니다.</p>");
                    }
                    out.println("<form action='update' method='post'>");
                    out.println("<table border='1'>");
                    out.println("<tr><th>아이디</th><td>");
                    out.printf("    <input type='text' name='id' value='%s' readonly></td></tr>\n",
                            id);
                    out.println("<tr><th>이메일</th><td>");
                    out.printf("    <input type='text' name='email' value='%s'></td></tr>\n",
                            rs.getString("email"));
                    out.println("<tr><th>암호</th>");
                    out.println("    <td><input type='password' name='password'></td></tr>\n");
                    out.println("</table>");
                    
                    
                    out.println("<p>");
                    out.println("<a href='list'>목록</a>");
                    out.println("<button>변경</button>");
                    out.printf("<a href='delete?id=%s'>삭제</a>", id);
                    out.println("</p>");
                    out.println("</form>");

                }
                
            }
            

        } catch (Exception e) {
            out.printf("<p>%s</p>\n", e.getMessage());
            e.printStackTrace(out);
        }

        out.println("</body>");
        out.println("</html>");
    }

}
