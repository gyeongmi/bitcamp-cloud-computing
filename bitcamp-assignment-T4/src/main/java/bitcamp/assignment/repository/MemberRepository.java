package bitcamp.assignment.repository;

import bitcamp.assignment.domain.Member;

public interface MemberRepository {
    int insert(Member member); //마이바티스에서 한다.

    Member findByEmailAndPassword(Member member);

    
}
