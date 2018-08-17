package bitcamp.assignment.web.json;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bitcamp.assignment.domain.Member;
import bitcamp.assignment.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
    //@RequestMapping(value="signUp", method=RequestMethod.POST)
    //= @RequestMapping(path="signUp", method=RequestMethod.POST)
    /*@GetMapping("signUp")
    public Object signUp(Member member) {
        System.out.println(member);
        //=System.out.println(member.toString());
        
        HashMap<String, Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
        
        
    } html 하기 전 toString 테스트하기 위해서*/
    
    @Autowired MemberService memberService;
    @PostMapping("signUp")
    public Object signUp(Member member) {
        //System.out.println(member);
        //=System.out.println(member.toString());
        
        HashMap<String, Object> result = new HashMap<>();
        try {
            memberService.add(member);
            result.put("status", "success");
            
        }catch(Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());            
        }
        return result;
        
        
    }

}
