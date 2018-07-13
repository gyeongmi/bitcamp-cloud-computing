package bitcamp.pms.servlet.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;

@SuppressWarnings("serial")
@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        
        // 지정된 시간이 경과하면 특정 서블릿을 요청하도록 태그를 삽입!
        // => 웹브라우저는 meta 태그의 내용대로 동작한다.
        //    content='경과시간(초);url=요청할URL'
        //
        out.println("<meta http-equiv='Refresh' content='1;url=list'>");
        
        out.println("<title>게시물 등록</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>게시물 등록 결과</h1>");
        try {
            BoardDao boardDao = (BoardDao) getServletContext().getAttribute("boardDao");

            Board board = new Board();
            board.setTitle(request.getParameter("title"));
            board.setContent(request.getParameter("content"));
/*            board.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));*/
            boardDao.insert(board);
            
            out.println("<p>등록 성공!</p>");
        } catch (Exception e) {
            out.println("<p>등록 실패!</p>");
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
    }
    
/*    private void insert(Board board) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        try (
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://13.124.153.245:3306/studydb",
                "study", "1111");
            PreparedStatement stmt = con.prepareStatement(
                "insert into pms2_board(titl,cont,cdt) values(?,?,now())");) {
            
            stmt.setString(1, board.getTitle());
            stmt.setString(2, board.getContent());
            stmt.executeUpdate();
        }
        
    }*/

}
