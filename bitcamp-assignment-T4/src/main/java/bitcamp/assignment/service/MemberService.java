package bitcamp.assignment.service;

import bitcamp.assignment.domain.Member;

public interface MemberService {
    int add(Member member);

    Member getEmail(String email, String password);

}
