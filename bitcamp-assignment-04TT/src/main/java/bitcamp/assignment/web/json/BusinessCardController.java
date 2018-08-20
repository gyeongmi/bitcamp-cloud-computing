package bitcamp.assignment.web.json;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bitcamp.assignment.domain.BusinessCard;
import bitcamp.assignment.domain.Member;
import bitcamp.assignment.service.BusinessCardService;

@RestController
@RequestMapping("/businesscard")
public class BusinessCardController2 {
    
    
    //@Autowired BusinessCardService bizcardService;
    //BusinessCardService bizcardService;
    
    //생성자에서 받도록 한다면
    //public BusinessCardController2(BusinessCardService bizcardService) {
    //    this.bizcardService = bizcardService;
    //}
    
    @GetMapping("list")
    public Object list(HttpSession session) {
        Member loginUser =
                (Member)session.getAttribute("loginUser");
        System.out.println(loginUser);
        
        HashMap<String, Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
        
    }
}
