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
        
        // ServletContext 보관소에 저장된 페이지 컨트롤러를 찾는다.
        Object pageController = 
           getServletContext().getAttribute(pathInfo);
        //특정 인터페이스 X PageController -> Object
        
        // 페이지 컨트롤러를 실행한다.
        try {
            if (pageController == null) 
                throw new Exception("해당 URL에 대해 서비스를 처리할 수 없습니다.");
            
            //페이지 컨트롤러에 있는 메서드 중에서 @RequestMapping이라는
            //애노테이션이 붙은 메서드를 찾아 호출한다.
            Method requestHandler = getRequestHandler(pageController.getClass());
            //클래스를 찾아서 메소드 정보를 리턴해줄래??
            //페이지컨트롤러의 클래스 정보
            //오브젝트에 존재하는 모든 자바클래스는 자기자신을 리턴하는 tostring
            //고유식별자 hashcode()
            //비교하기위해서 equals() 등...
            //모든 자바 객체는 자기가 어떤 클래스의 객ㄱ체인지 getClass() -> 클래스정보를 다루는 도구 Class
            // 쓰레드 클래스, 시스템 클래스, 파일 클래스, 클래스를 다루는 클래스(Class)
            
            if(requestHandler==null)
                throw new Exception("요청 핸드러를 찾지 못했습니다.");
            
            //페이지 컨트롤러의 메서드를 호출한다, 메소드 정보를 가지고 호출해달라고 invoke
            String view = (String) requestHandler.invoke(pageController ,request, response);
            //객체 주소 pageController,  파리미터 값
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
        //Class clazz;
        //클래스 객체 변수... 멤버에서처럼... Member member
        
        //클래스 정보에서 메서드 정보를 추출한다.
        Method[] methods = clazz.getMethods();//배열을 리턴함, 메소드 목록 꺼내서
        
        // 메서드 중에서 @RequestMapping 애노테이션이 붙은 메서드를 찾아낸다
        for(Method m : methods) { //메서드를 한개씩 꺼냄
            RequestMapping anno = m.getAnnotation(RequestMapping.class);
            //애노테이션.type(class) 물어봄 있으면 그즉시 메서드를 리턴 없으면 null
            //스태틱 변수는 직접 접근, 도움창에서 아이콘위에 s가 있으면 스태택 // .class는  클래스타입의 스태틱 변수
            //자바버츄얼머신이 자동으로 만듬. static Class class = ....;
            // 애노테이션, 인터페이스, 클래스... 등 다 클래스변수가 있따. 바이트코드라 class로 저장되기 떄문
            
            //애노테이션 정보를 담은 객체를 리턴 = anno
            if(anno != null)
                return m;
        }
        return null;
    }
}
