package bitcamp.pms.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.context.ApplicationContext;

@SuppressWarnings("serial")
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(
            HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        
        // 클라이언트가 요청한 서비스 URL을 알아낸다.
        // 즉 /app/* 에서 *에  해당하는 값을 추출한다.
        // 예) /app/member/list => /member/list를 추출한다.
        String pathInfo = request.getPathInfo();
        
        response.setContentType("text/html;charset=UTF-8");

        // ServletContext 보관소에 저장된 IoC 컨테이너를 꺼낸다.
        ApplicationContext iocContainer = 
                (ApplicationContext) getServletContext()
                                     .getAttribute("iocContainer");
        
        try {
            
            // IoC 컨테이너에 저장된 페이지 컨트롤러를 찾는다.
            Object pageController = 
                    iocContainer.getBean(pathInfo);

            // 페이지 컨트롤러를 못 찾았으면 오류를 내보낸다.
            if (pageController == null) 
                throw new Exception("해당 URL에 대해 서비스를 처리할 수 없습니다.");
            
            // 페이지 컨트롤러에 있는 메서드 중에서 @RequestMapping이라는
            // 애노테이션이 붙은 메서드를 찾아 호출한다.
            Method requestHandler = getRequestHandler(pageController.getClass());
            // 클래스를 찾아서 메소드 정보를 리턴해 줄래??
            // 페이지 컨트롤러의 클래스 정보
            // 오브젝트에 존재하는 모든 자바클래스는 자기 자신을 리턴하는
            // toString, 고유식별자 hashCode(), 비교하기위해서 equals() 등등..
            // 모든 자바 객체는 자기가 어떤 클래스의 객체인지 알수있다 getClass() -> 클래스 정보를 다르는 도구 Class
            // 이런게 있다 Thread 클래스, System 클래스, File 클래스, 클래스를 다루는 클래스(Class)
            
            
            
            if (requestHandler == null) 
                throw new Exception("요청 핸들러를 찾지 못했습니다.");
            
            // 페이지 컨트롤러의 메서드를 호출한다. 메소드 정보를 가지고 호출해 달라고 하는 게 invoke
            String view = (String) requestHandler.invoke(
                    pageController, request, response);
            // invoke(객체 주소, 파라미터값, 파라미터값) 컨트롤들 마다 가지는 파라미터 request, response
            
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
        // Class clazz;
        // 클래스 객체 변수. 예) Member member
        
        // 클래스 정보에서 메서드 정보를 추출한다.
        Method[] methods = clazz.getMethods(); //배열을 리턴한다. 메소드 목록 꺼내줌.
        
        // 메서드 중에서 @RequestMapping 애노테이션이 붙은 메서드를 찾아낸다.
        for (Method m : methods) { //메소드를 하나씩 꺼냄
            RequestMapping anno = m.getAnnotation(RequestMapping.class);
            // Annotation.type(class) => RequestMapping.class
            // 그 즉시 메소드를 리턴, 없으면 null
            // 스태틱 변수는 직접 접근. 도움창에서 아이콘 위에 s가 있으면 스태틱.
            // .class는 클래스 타입의 스태틱 변수
            // 자바 버츄얼 머신이 자동으로 만든다. static Class class = .....;
            // Annotation, Interface, Class .. 등 다 클래스 변수가 있다. 이유는 바이트코드로 저장하기 떄문에 class로 저장되기 때문.
            
            // 애노테이션 정보를 담은 객체를 리턴 = anno
            if (anno != null)
                return m;
        }
        
        return null;
    }

}









