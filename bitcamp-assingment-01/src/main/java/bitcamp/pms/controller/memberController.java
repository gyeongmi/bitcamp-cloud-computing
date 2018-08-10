package bitcamp.pms.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bitcamp.pms.domain.Member;
import bitcamp.pms.service.MemberService;

@RestController
@RequestMapping("/member")
public class memberController {
@Autowired MemberService memberService;
    
    @GetMapping("form")
    public void form() {
    }
    
    @PostMapping("add")
    public Object add(Member member) throws Exception {
        HashMap<String,Object> result = new HashMap<>();
        memberService.add(member);
        result.put("status", "success");
        return result;
    }
}
