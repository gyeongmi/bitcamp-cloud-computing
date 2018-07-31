package bitcamp.pms.controller;

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

import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller
@RequestMapping("/member") // /member 반복되니까
public class MemberController {

    //실무에서
    @Autowired MemberDao memberDao;
    
/*    
    MemberDao memberDao;
    
    public MemberListController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
*/

    //@RequestMapping("/member/list")
    @RequestMapping("list")
    public String list(
            @RequestParam(defaultValue="1") int page,
            @RequestParam(defaultValue="3") int size,
            Model model ) throws Exception {
        
        // DB에서 가져올 데이터의 페이지 정보
        if(page < 1) page =1;
        if(size <1 || size > 20) size = 3;
        
        HashMap<String,Object> params = new HashMap<>();
        params.put("startIndex", (page - 1) * size);
        params.put("pageSize", size);

        List<Member> list = memberDao.selectList(params);
        model.addAttribute("list",list);
        
        return "member/list";
            
    }
    
    //스프링 버전 4.3 이상부터 GetMapping
    // = @RequestMapping(value="/member/form", method=RequestMethod.GET)
    @GetMapping("form")
    public void form() {
        //return "/member/form";*
        // 클라이언트가 요청한 경로와 jsp 경로가 같은 통로에 있을 때는 굳이 리턴하지 않아도 된다.
    }
    
    // =@RequestMapping(value="/member/add", method=RequestMethod.POST)
    @PostMapping("add") 
    public String add(Member member) throws Exception {
        memberDao.insert(member);
        return "redirect:list";
    }
    
    @RequestMapping("delete")
    public String delete(String id) throws Exception {
        memberDao.delete(id);
        return "redirect:list";
    }

    @RequestMapping("update")
    public String update(Member member) throws Exception {
        if (memberDao.update(member) == 0) {
            return "member/updatefail";
        } else {
            return "redirect:list";
        }
    }
    
    @RequestMapping("view/{id}")
    public String view(
            @PathVariable String id,
            Model model) throws Exception {
        //jsp 객체를 보내야 되기 떄문에 model이 필요
        
        Member member = memberDao.selectOne(id);
        model.addAttribute("member", member);
        return "member/view";
    }
}










