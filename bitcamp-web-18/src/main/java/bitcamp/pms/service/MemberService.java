package bitcamp.pms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
   
    // @Transactional(readOnly=true)
    // 메서드에 @Transactional 애노테이션을 붙이면,
    // 메서드에서 수행하는 작업들을 한 단위로 묶어서 다루겠다는 의미다.
    // 따라서 작업들 중에 한 개의 작업이라도 실패하면
    // 이전에 했던 성공했떤 모든 작업들이 취소된다.
    // 메서드 호출이 정상적으로 예외 없이 정상적으로 끝났을 때만이
    // DBMS에 commit을 요청하여 지금까지 한 작업을 실제 테이블에 적용시킨다.
    // @Transactional() //readOnly=false 가 default
    // @Transactional() -> 애노테이션 대신 xml에 태그로 지정할 수 있다.
    
    public int update(Member member) {
        int count = memberDao.update(member);
        
        //insert
        //update
        //insert
        //delete
        //이 중에 한개라도 오류가 뜨더라도 이전까지 작업, 실행했던 거 다 취소된다.
        //트랜잭션으로 가둬 둬야 한다.
        
        if(count != 100)
            throw new RuntimeException("일부러 예외 발생"); //서버리스타트 하면 java.lang.RuntimeException: 일부러 예외 발생
        //어떤 예외를 던진다고 보고 하지만
        //적을 필요가 없다.. 무조건 뜨게 되어 있다.
        //트랜잭션으로 묶지 않았기 때문에 update한 데이터가 유효한다.
        
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
        int totalPage = count / size; //예) 11/3 => 3 그런데 11을 다 출력하려면 4페이지 사이즈가 필요
        if(count % size > 0)
            totalPage++;
        return totalPage;
    }
    
    

}
