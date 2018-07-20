package bitcamp.pms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Service
//@Component 붙이면 좋지만 구분하기 좋게.. @Repository, @Controller, @Service 가 있다.
public class MemberService {
    @Autowired MemberDao memberDao;

    public List<Member> list(int page, int size) {
        // DB에서 가져올 데이터의 페이지 정보
        HashMap<String,Object> params = new HashMap<>();
        params.put("startIndex", (page - 1) * size);
        params.put("pageSize", size);

        return memberDao.selectList(params);
    }

    public Member get(String id) {
        return memberDao.selectOne(id);
    }

    public int update(Member member) {
        return memberDao.update(member);
    }

    public int delete(String id) {
        return memberDao.delete(id);
        
    }

    public void add(Member member) {
        memberDao.insert(member);
        
    }
    
    public int getTotalPage(int size) {
        int count = memberDao.countAll();
        int totalPage = count / size; //예) 11/3 => 3 그런데 11을 다 출력하려면 4페이지 사이즈가 필요
        if(count % size > 0)
            totalPage++;
        return totalPage;
    }
    
    

}
