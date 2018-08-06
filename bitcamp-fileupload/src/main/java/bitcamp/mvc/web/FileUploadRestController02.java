package bitcamp.mvc.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@RestController
@RequestMapping("/ajax")
public class FileUploadRestController02 {
    
    @Autowired ServletContext sc;
    
    @RequestMapping("upload01")
    public Object upload01(
            String name, 
            String age, 
            MultipartFile[] files) { //여러개의 파일이 넘어올 일이 없다.
        
        //낱개로 1번씩 보낸다.
        System.out.println("upload01()...호출됨!");
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        
        ArrayList<String> filenames = new ArrayList<>();
        result.put("filenames", filenames);
        
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;
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
    
    
    @RequestMapping("upload02")
    public Object upload02(
            String name, 
            String age, 
            MultipartFile[] files) { //여러개의 파일이 넘어올 일이 없다.
        
        //낱개로 1번씩 보낸다.
        System.out.println("upload01()...호출됨!");
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        
        ArrayList<String> filenames = new ArrayList<>();
        result.put("filenames", filenames);
        
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;
                String newfilename = UUID.randomUUID().toString(); 
                String path = sc.getRealPath("/files/" + newfilename);
                file.transferTo(new File(path));
                filenames.add(newfilename); //담은건 객체가 아닌 어레이리스트 주소를 저장, 어레이리스트에다가 더하더라도 상관이 없다.
                Thumbnails.of(path)
                          .size(20, 20)
                          .outputFormat("jpg")
                          .toFile(path+"_20x20");
                Thumbnails.of(path)
                          .size(80, 80)
                          .outputFormat("jpg")
                          .toFile(path+"_80x80");
                Thumbnails.of(path)
                          .size(120, 120)
                          .outputFormat("jpg")
                          .toFile(path+"_120x120");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
