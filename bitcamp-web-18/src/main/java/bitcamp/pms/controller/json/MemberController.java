package bitcamp.pms.controller.json;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;
import bitcamp.pms.service.MemberService;

@RestController
@RequestMapping("/member") // /member 반복되니까
public class MemberController { //이름이 같지만 ioc 컨테이너가 다르니까 상관없다..
    
    @Autowired MemberService memberService;

    @RequestMapping("list")
    public Object list(
            @RequestParam(defaultValue="1") int page,
            @RequestParam(defaultValue="3") int size) throws Exception {
        
        if(page < 1) page =1;
        if(size <1 || size > 20) size = 3;
        
        List<Member> list = memberService.list(page, size);
        
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("page", page);
        data.put("size", size);
        data.put("totalPage",
                memberService.getTotalPage(size));

        return data;
    }
    
    @GetMapping("form")
    public void form() {
    }
    
    @PostMapping("add") 
    public Object add(Member member) throws Exception {
      //udpate 거 그대로 복사
        HashMap<String, Object> result = new HashMap<>();
        memberService.add(member);
        result.put("status", "success"); //add는 등록 아니면 아예 실패이다.
        return result;
    }
    
    @RequestMapping("delete")
    public Object delete(String id) throws Exception {
        //udpate 거 그대로 복사
        HashMap<String, Object> result = new HashMap<>();
        if (memberService.delete(id) == 0) {
            result.put("status", "fail");
            result.put("error", "해당 아이디가 없습니다.");

        } else {
            result.put("status", "success");
        }
        return result;
    }

    @RequestMapping("update")
    public Object update(Member member) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        if (memberService.update(member) == 0) {
            result.put("status", "fail");
            result.put("error", "해당 아이디가 없습니다.");

        } else {
            result.put("status", "success");
        }
        return result;
    }
    
    @RequestMapping("view/{id}")
    public Object view(@PathVariable String id) throws Exception {
        HashMap<String,Object> data = new HashMap<>();
        data.put("member", memberService.get(id));
        return data;
    }
}










