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


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


//Servlet 3.0 부터 멀티파트 데이터를 처리하는 API를 제공한다.
//멀티파트 데이터를 처리하는 서블릿은 멀티파트 처리에 대한 정보를 
//애노테이션 또는 DD파일(web.xml; deployment descriptor 파일)에 설정해야 한다.

// => web.xml 설정하기
public class FileUploadServlet05 extends HttpServlet {
    @Override
    protected void doPost(
            HttpServletRequest req, 
            HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            //여러개일 경우 getPart
            String name = req.getParameter("name"); //문자열만 추출하자. .getString
            String age = req.getParameter("age"); //age는 utf8이 필요 X
            Part photo = req.getPart("photo");
            
            String newfilename = UUID.randomUUID().toString(); 
            String path = this.getServletContext().getRealPath(
                    "/files/" + newfilename);
            
            photo.write(path);
            
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
        }
    }
}
