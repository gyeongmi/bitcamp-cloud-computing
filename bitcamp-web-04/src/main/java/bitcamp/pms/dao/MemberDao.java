package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bitcamp.pms.domain.Member;

public class MemberDao {
    
    
/*    static String jdbcUrl = "jdbc:mysql://13.124.153.245:3306/studydb"; //스태틱은 같은 스태틱 멤버끼리...
    //같은 스태틱 멤버여야 한다그래서 static으로 선언
    static String username = "study";
    static String password = "1111";*/

    //memberdao 로딩될때 한번만 하도록 하겠음 Class.
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    String jdbcUrl; //DBMS 따로따로 관리하기 위해 인스턴스 변수로 만듬
    String username;
    String password;
    
    
    
    public MemberDao(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    } //MemberDao를 따로따로 관리할 수 있음
    
    public List<Member> selectList() throws Exception{ //위 인스턴스 변수를 만들고 나서 static을 없앰
      //Class.forName("com.mysql.jdbc.Driver");*/ //클래스 로딩
      //그리고 전체 Class.forname("com.mysql.jdbc.Driver") 삭제
        try (

                Connection con = DriverManager.getConnection(
                    jdbcUrl, username, password);
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
    }
    
    public Member selectOne(String id) throws Exception{

        try (
            Connection con = DriverManager.getConnection(
                    jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "select mid,email from pms2_member where mid=?");) { 
        
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if (!rs.next()) { 
                       return null;
                }
                Member member = new Member();
                member.setId(rs.getString("mid"));
                member.setEmail(rs.getString("email"));
                return member;
            }
            //out.println("<p>유효하지 않은 멤버 아이디입니다.</p>")
        }
    }
    
    public int update(Member member) throws Exception{

        try (
            Connection con = DriverManager.getConnection(
                    jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "update pms2_member set email=?, pwd=password(?) where mid=?");) {
            
            stmt.setString(1, member.getEmail());
            stmt.setString(2, member.getPassword());
            stmt.setString(3, member.getId());
            
            return stmt.executeUpdate();
        }
    }
    
    public int delete(String id) throws Exception{

        try (
            Connection con = DriverManager.getConnection(
                    jdbcUrl, username, password);
            PreparedStatement stmt = con.prepareStatement(
                "delete from pms2_member where mid=?");) {
            
            stmt.setString(1, id);

            return stmt.executeUpdate();
        }
    }
    
    public void insert(Member member) throws Exception{
        //memberDao.insert(member);

          try (
              Connection con = DriverManager.getConnection(
                      jdbcUrl, username, password);
              PreparedStatement stmt = con.prepareStatement(
                  "insert into pms2_member(mid,email,pwd) values(?,?,password(?))");) {
              
              stmt.setString(1, member.getId());
              stmt.setString(2, member.getEmail());
              stmt.setString(3, member.getPassword());
          
              stmt.executeUpdate();

          }
      }

}
