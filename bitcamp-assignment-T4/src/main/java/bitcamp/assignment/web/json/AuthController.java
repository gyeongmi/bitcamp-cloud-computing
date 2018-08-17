package bitcamp.assignment.web.json;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

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
    public Object signIn(
            String email,
            String password,
            boolean saveEmail,
            HttpSession session) {
        //System.out.println(member);
        //=System.out.println(member.toString());
        
        HashMap<String, Object> result = new HashMap<>();
        try {
            //System.out.println(member);
            //memberService.add(member);
            
            //이메일과 암호로 찾아보다
            //System.out.println(saveEmail);
            Member loginUser = memberService.getMember(email, password);
            //System.out.println(loginUser);
            
            if(loginUser == null)
                throw new Exception("로그인 실패!");
            session.setAttribute("loginUser", loginUser);
            result.put("status", "success");            
        }catch(Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());            
        }
        return result;
    }
}
