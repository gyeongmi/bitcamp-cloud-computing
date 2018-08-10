package bitcamp.ass.dao;

import java.util.List;
import java.util.Map;

import bitcamp.ass.domain.Member;

public interface MemberDao {
    List<Member> selectList();
    Member selectOne(int mno);
    int update(Member member);
    int delete(int mno);
    int insert(Member member);
    int countAll();
}
