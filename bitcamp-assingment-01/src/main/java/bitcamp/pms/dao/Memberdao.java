package bitcamp.pms.dao;

import bitcamp.pms.domain.Member;

public interface Memberdao {
    int insert(Member member);
    int update(Member member);
    int delete(String id);

}
