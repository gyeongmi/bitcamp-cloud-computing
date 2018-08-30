package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import bitcamp.pms.domain.Member;

public class MemberDao {
    SqlSessionFactory sqlSessionFactory;

/*    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String jdbcUrl; //DBMS 따로따로 관리하기 위해 인스턴스 변수로 만듬
    String username;
    String password;
    만들필요가없어짐*/

    
    public MemberDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;

/*        String resource = "org/mybatis/example/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
          new SqlSessionFactoryBuilder().build(inputStream);
          여기서 만들지 않는다.
        */
    }
    
    public List<Member> selectList(Map<String,Object> params) throws Exception{ 
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            return sqlSession.selectList("member.selectList", params);
            // MemberMapper의 <mapper namespace="member"> == member.
        }
    }
    
    public Member selectOne(String id) throws Exception{
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            return sqlSession.selectOne("member.selectOne", id);
        }
    }
    
    public int update(Member member) throws Exception{
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            int count = sqlSession.update("member.update", member);
            sqlSession.commit();
            return count;
        }
    }
    
    public int delete(String id) throws Exception{
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            int count = sqlSession.delete("member.delete", id);
            sqlSession.commit();
            return count;
        }
    }
    
    public int insert(Member member) throws Exception{
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            int count = sqlSession.insert("member.insert", member);
            sqlSession.commit();
            return count;
        }
    }
}
