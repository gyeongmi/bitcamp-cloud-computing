package bitcamp.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/fileupload02")
public class FileUploadServlet02 extends HttpServlet {
    @Override
    protected void doPost(
            HttpServletRequest req, 
            HttpServletResponse resp) throws ServletException, IOException {
        
        
        //업로드 파일을 외장 하드에 저장하는 역할을 수행
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        //멀티파트 데이터를 파싱한다.
        //업로드된 파일은 위에서 설정한 factory를 이용하여 다룬다. 
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        //클라이언트가 보낸 데이터를 분석한다.
        //HashMap<String,Object> paramMap = new HashMap<>();
        try {
            Map<String, List<FileItem>> paramMap =
                    upload.parseParameterMap(req); //map 객체를 아예 리턴한다.
            
            //Map은 인터페이스
            //Map map; 값을 저장할 때 map.put(key, value) 키의 타입을 지정하지 않았기 때문에 마구잡이로 넣을 수 있다.
            //용도를 제한하는 것.. 제네릭.
            //Map<String, Object> map; key 와 value 타입을 지정하는 것.
            //클래스가 아니고 인터페이스이기 때문에 new Map이 안 된다.
            //Map 인터페이스를 구현한 것, HashMap, HashTable.
            //new HashMap<S, O>;
            //jdk 8부터는 앞에 있는 Map 타입을 추론해서 생략할 수 있다. new HashMap<>;
            //List<FileItem> => fileitem을 담고 있는.. list로 제한된 것..
            
            //req.getParameter(name); //오로지 한개 값만 뽑는다.
            //String[] n = req.getParameterValues(name); //스트링 배열
            /*FileItem nameItem = paramMap.get("name").get(0); //?name=aa&name=bb.. 같은 파라미터명으로 올수 있다. 여러개 값중에서  1개만 뽑고 싶으면 리스트에서 반복문으로 추출하지 말고 .get(0)
            FileItem ageItem = paramMap.get("age").get(0);
            FileItem photoItem = paramMap.get("photo").get(0);
*/
            String name = paramMap.get("name").get(0).getString("UTF-8"); //문자열만 추출하자. .getString
            String age = paramMap.get("age").get(0).getString(); //age는 utf8이 필요 X
            FileItem photoItem = paramMap.get("photo").get(0);
            
            /*List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) { // 일반 폼 데이터인 경우,
                    paramMap.put(item.getFieldName(), 
                            item.getString("UTF-8"));
                } else { // 파일 데이터
                    // 새 파일명 준비
                    String newfilename = UUID.randomUUID().toString(); 
                    String path = this.getServletContext().getRealPath(
                            "/files/" + newfilename);
                    item.write(new File(path));
                    paramMap.put(item.getFieldName(), 
                            newfilename);
                    paramMap.put(item.getFieldName(),
                            item.getName());

                }
            }*/
            
            String newfilename = UUID.randomUUID().toString(); 
            String path = this.getServletContext().getRealPath(
                    "/files/" + newfilename);
            photoItem.write(new File(path));
            
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>파일업로드</title></head><body>");
/*            out.printf("name = %s<br>\n", nameItem.getString("UTF-8"));
            out.printf("age = %s<br>\n", nameItem.getString("UTF-8"));
*/          out.printf("name = %s<br>\n", name);
            out.printf("age = %s<br>\n", age);

            out.printf("photo = <a href='files/%s'>%s</a><br>\n", 
                    newfilename,
                    newfilename);
            out.printf("<p><img src='files/%s'></p>", newfilename); //업로드하자마자 이미지 태그 소스를 지정하는 경우
            out.println("<p><img id='img1'></p>"); //자바스크립트에 의해서 5초가 지난후 이미지 태그 소스를 지정하는 경우
            out.println("<script>");
            out.println("   setTimeout(() => {");
            out.printf("        document.getElementById('img1').src = 'files/%s';", 
                    newfilename);
            out.println("   },5000)");
            out.println("</script>");
            out.println("</body></html>");
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
