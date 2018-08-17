package bitcamp.assignment.web.json;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bitcamp.assignment.domain.Member;
import bitcamp.assignment.service.MemberService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired MemberService memberService;
    
    @PostMapping("signIn")
    
    //데이터가 10개 이상 넘어갈 때는 Member로 받지 않는게 좋다.. 지금은 3개라 단순하지만 혹시모름.. -> String email, String password..
    public Object signUp(String password, String email, boolean saveEmail) {
        //System.out.println(member);
        //=System.out.println(member.toString());
        
        
        HashMap<String, Object> result = new HashMap<>();
        try {
            System.out.println(member);
            System.out.println(saveEmail);
            //memberService.add(member);
            //이메일과 암호로 찾아보다
            Member loginUser = memberService.getEmail(password, email);
            System.out.println(loginUser);
            result.put("status", "success");
            
        }catch(Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());            
        }
        return result;
    }
}
