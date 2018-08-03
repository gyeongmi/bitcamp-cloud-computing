package bitcamp.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

@WebServlet("/fileupload03")
public class FileUploadServlet03 extends HttpServlet {
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
        InputStream fileContent = null;
        OutputStream fileOut = null;
        try {
            Map<String, List<FileItem>> paramMap =
                    upload.parseParameterMap(req); //map 객체를 아예 리턴한다.
            String name = paramMap.get("name").get(0).getString("UTF-8"); //문자열만 추출하자. .getString
            String age = paramMap.get("age").get(0).getString(); //age는 utf8이 필요 X
            FileItem photoItem = paramMap.get("photo").get(0);
            
            String newfilename = UUID.randomUUID().toString(); 
            String path = this.getServletContext().getRealPath(
                    "/files/" + newfilename);
            
            //photoItem.write(new File(path)); 이것 대신에...
            fileContent = photoItem.getInputStream();
            fileOut = new FileOutputStream(path);
            
            byte[] buf = new byte[1024]; //1k 버퍼 준비
            int len = 0;
            
            while ((len = fileContent.read(buf)) != -1) {
                fileOut.write(buf, 0, len);
                //배열로 읽으라고 한다. 읽은 개수를 리턴한다. 못읽었으면 -1 을 리턴)
                //읽은 개수 만큼 출력한다.
            }
            fileOut.flush(); //버퍼에 남은 게 있으면 깔끔하게 다 출력
            
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>파일업로드</title></head><body>");
            out.printf("name = %s<br>\n", name);
            out.printf("age = %s<br>\n", age);
            out.printf("photo = <a href='files/%s'>%s</a><br>\n", 
                    newfilename,
                    newfilename);
            out.printf("<p><img src='files/%s'></p>", newfilename);
            out.println("<p><img id='img1'></p>");
            out.println("<script>");
            out.println("    setTimeout(() => {");
            out.printf(
                    "        document.getElementById('img1').src = 'files/%s';", 
                    newfilename);
            out.println("    }, 5000);");
            out.println("</script>");
            out.println("</body></html>");
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {fileContent.close();} catch (Exception e) {}
            try {fileOut.close();} catch (Exception e) {} //입출력 닫아줘야 한다..
            
        }

    }
}
