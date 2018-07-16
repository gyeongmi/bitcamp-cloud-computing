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
import org.springframework.web.context.support.WebApplicationContextUtils;

import bitcamp.pms.annotation.RequestMapping;


@SuppressWarnings("serial")
@WebServlet(value="/app/*",loadOnStartup=1)
public class DispatcherServlet extends HttpServlet {
    
    ApplicationContext iocContainer;
    //init()가 두개 servletconfig를 받는 init가 있고 안받는 init가 있는데
    //아래의 init는 안받는 init()
    @Override
    public void init() throws ServletException {
        // DispatcherSevlet이 본격적으로 클라이언트 요청을 처리하기 전에
        // Spring의 ContextLoderListener가 준비한 IoC 컨테이너를 꺼내자.
        // 다음과 같이 다른 클래스의 도움을 받아서 IoC 컨테이너를 꺼내야 한다.
        
        iocContainer = WebApplicationContextUtils
                            .getWebApplicationContext(
                                    this.getServletContext());
        //유틸스의 스태틱 메소드인 getApplicationContext의 도움을 통해서 파라미터에 현재 서블릿컨텍스트 주소(어느 웹어플리케이션인지 알려줘야(sc) = 서블릿 컨텍스트 주소만 주면 됨
        // 그러면 보관된 ioc 컨테이너를 꺼내줄거임
        
        //ioc에 컨테이너에 들어있는 객체를 한 번 출력해 보자!
        System.out.println("-------------------------------");
        String[] names= iocContainer.getBeanDefinitionNames();
        for(String name : names) {
            System.out.printf("%s => %s\n", name,
                    iocContainer.getBean(name).getClass().getName());
        }
        System.out.println("-------------------------------");
        
        
    }
    
    @Override
    protected void service(
            HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        response.setContentType("text/html;charset=UTF-8");
        
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









