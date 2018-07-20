package bitcamp.pms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.pms.domain.Member;

public interface MemberDao {

    public List<Member> selectList(Map<String,Object> params);
    public Member selectOne(String id);
    public int update(Member member);
    public int delete(String id);
    public int insert(Member member);
    public int countAll();

}



