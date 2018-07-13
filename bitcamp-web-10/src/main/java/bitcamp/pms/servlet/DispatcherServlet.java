package bitcamp.pms.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import bitcamp.pms.annotation.RequestMapping;


@SuppressWarnings("serial")
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(
            HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        response.setContentType("text/html;charset=UTF-8");
        
        ApplicationContext iocContainer = 
                (ApplicationContext) getServletContext()
                                     .getAttribute("iocContainer");
        
        try {

            Object pageController = 
                    iocContainer.getBean(pathInfo);


            if (pageController == null) 
                throw new Exception("해당 URL에 대해 서비스를 처리할 수 없습니다.");
 
            Method requestHandler = getRequestHandler(pageController.getClass());

            
            
            
            if (requestHandler == null) 
                throw new Exception("요청 핸들러를 찾지 못했습니다.");
            

            String view = (String) requestHandler.invoke(
                    pageController, request, response);
            
            if (view.startsWith("redirect:")) {
                response.sendRedirect(view.substring(9));
            } else {
                RequestDispatcher rd = 
                        request.getRequestDispatcher(view);
                rd.include(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", e);
            RequestDispatcher rd = 
                    request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }

    private Method getRequestHandler(Class<?> clazz) {

        Method[] methods = clazz.getMethods(); 
        
        
        for (Method m : methods) { 
            RequestMapping anno = m.getAnnotation(RequestMapping.class);
          
            

            if (anno != null)
                return m;
        }
        
        return null;
    }

}









