package bitcamp.mvc.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadRestController {
    
    @Autowired ServletContext sc;
    
    @RequestMapping("/json-upload01")
    public Object upload01(
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
            MultipartFile[] files) {
        
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        ArrayList<String> filenames = new ArrayList<>();
        result.put("filenames", filenames);
        try {
            for(MultipartFile file : files) {
                if(file.isEmpty()) continue;
                
                // 새 파일명 준비
                String newfilename = UUID.randomUUID().toString(); 
                String path = sc.getRealPath("/files/" + newfilename);
                file.transferTo(new File(path));
                filenames.add(newfilename); //담은건 객체가 아닌 어레이리스트 주소를 저장, 어레이리스트에다가 더하더라도 상관이 없다.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}