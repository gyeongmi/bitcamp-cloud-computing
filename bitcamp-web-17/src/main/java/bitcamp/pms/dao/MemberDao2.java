package bitcamp.pms.dao;

import java.util.List;
import java.util.Map;

import bitcamp.pms.domain.Member;

public interface MemberDao2 {
    public List<Member> selectList(Map<String, Object> params);
    public Member selectOne(String id);
    public int update(Member member);
    public int insert(Member member);
    public int delete(String id);
    public int countAll();
}