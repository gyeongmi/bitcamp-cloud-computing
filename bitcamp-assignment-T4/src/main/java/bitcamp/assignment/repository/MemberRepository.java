package bitcamp.assignment.repository;

import java.util.Map;

import bitcamp.assignment.domain.Member;

public interface MemberRepository {
    int insert(Member member); //마이바티스에서 한다.

    Member findByEmailAndPassword(Map<String, Object> params);

    
}
