package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import bitcamp.pms.domain.Classroom;

public class ClassroomDao {
    
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //jdbc:mysql://13.124.153.245:3306/studydb
    String jdbcUrl;
    String username;
    String password;
    
    public ClassroomDao(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }
    
    public List<Classroom> selectList() throws Exception{
        try (
            Connection con = DriverManager.getConnection(
                jdbcUrl,username, password);
            PreparedStatement stmt = con.prepareStatement(
                "select crno,titl,sdt,edt,room from pms2_classroom");
            ResultSet rs = stmt.executeQuery();) {
            
            ArrayList<Classroom> list = new ArrayList<>();
            while (rs.next()) {
                Classroom classroom = new Classroom();
                classroom.setNo(rs.getInt("crno"));
                classroom.setTitle(rs.getString("titl"));
                classroom.setStartDate(rs.getDate("sdt"));
                classroom.setEndDate(rs.getDate("edt"));
                classroom.setRoom(rs.getString("room"));
                list.add(classroom);
            }
            return list;
        }
    }
    
    
    public Classroom selectOne(int no) throws Exception{
        try (
            Connection con = DriverManager.getConnection(
                jdbcUrl,username, password);
            PreparedStatement stmt = con.prepareStatement(
                "select crno,titl,sdt,edt,room from pms2_classroom where crno=?");) {
            stmt.setInt(1, no);
            
            try (ResultSet rs = stmt.executeQuery()){
                if(!rs.next()) {
                    return null;
                }
                Classroom classroom = new Classroom();
                classroom.setNo(rs.getInt("crno"));
                classroom.setTitle(rs.getString("titl"));
                classroom.setStartDate(rs.getDate("sdt"));
                classroom.setEndDate(rs.getDate("edt"));
                classroom.setRoom(rs.getString("room"));
                
                return classroom;
                
            }

        }
    }
    
    public int update(Classroom classroom) throws Exception{
        try (
            Connection con = DriverManager.getConnection(
                jdbcUrl,username, password);
            PreparedStatement stmt = con.prepareStatement(
                "update pms2_classroom set titl=?, sdt=?, edt=?, room=? where crno=?");){
            stmt.setString(1, classroom.getTitle());
            stmt.setDate(2, classroom.getStartDate(), Calendar.getInstance(Locale.KOREA));
            stmt.setDate(3, classroom.getEndDate(), Calendar.getInstance(Locale.KOREA));
            stmt.setString(4, classroom.getRoom());
            stmt.setInt(5, classroom.getNo());
            
            return stmt.executeUpdate();
            
        }
        
    }
    
    public int delete(int no) throws Exception{
        try (
            Connection con = DriverManager.getConnection(
                jdbcUrl,username, password);
            PreparedStatement stmt = con.prepareStatement(
                "delete from pms2_classroom where crno=?");) {
            stmt.setInt(1, no);
            
            return stmt.executeUpdate();
        } 
        
    }
    
    public void insert(Classroom classroom) throws Exception{
        try (
            Connection con = DriverManager.getConnection(
                jdbcUrl,username, password);
            PreparedStatement stmt = con.prepareStatement(
                "insert into pms2_classroom(titl,sdt,edt,room) values(?,?,?,?)");) {
            
            stmt.setString(1, classroom.getTitle());
            stmt.setDate(2, classroom.getStartDate(), Calendar.getInstance(Locale.KOREA));
            stmt.setDate(3, classroom.getEndDate(), Calendar.getInstance(Locale.KOREA));
            stmt.setString(4, classroom.getRoom());

            stmt.executeUpdate();
        }
    }

}
