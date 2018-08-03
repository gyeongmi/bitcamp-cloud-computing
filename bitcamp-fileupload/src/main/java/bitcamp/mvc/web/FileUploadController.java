package bitcamp.mvc.web;

import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    
    @Autowired ServletContext sc;
    
    @RequestMapping("/upload01")
    public void upload01(
            //void이면 결과를 찾는다. 리턴하지 않고 보이드 하면
            //페이지 컨트롤러의 url을 가지고 
            // /mvc/upload01 페이지 컨트롤러를 요청했는데
            //
            //플ㄴ트컨트롤러는 mv 요청
            //
           // 페이지 컨트롤러 앞에다가 
            //prefix /WEB/jsp/ .jsp
            //전체 찾아야할 jsp url 결정
            //
            //
            String name, 
            int age,
            MultipartFile photo,
            Model model) {
        
        // 새 파일명 준비
        String newfilename = UUID.randomUUID().toString(); 
        String path = sc.getRealPath("/files/" + newfilename);
        
        try {
            photo.transferTo(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("newfilename", newfilename);
    }
}