package bitcamp.pms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Service
public class MemberService2 {
    @Autowired MemberDao memberDao;
    
    public List<Member> list(int page, int size){
        HashMap<String, Object> params = new HashMap<>();
        params.put("startIndex", (page - 1) * size);
        params.put("pageSize", size);
        return memberDao.selectList(params);
    }
    
    public Member get(String id) {
        return memberDao.selectOne(id);
    }
    public int update(Member member) {
        int count = memberDao.update(member);
        return count;
    }
    public int delete(String id) {
        return memberDao.delete(id);
    }
    public void add(Member member) {
        memberDao.insert(member);
    }
    public int getTotalPage(int size) {
        int count = memberDao.countAll();
        int totalPage = count/size; //예) 11/3 => 3
        if(count % size > 0) {
            totalPage++;
        }
        return totalPage;
    }
}
