package bitcamp.mvc.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    //톰캣 서버가 시작했을 때 시작하거나 종료할 때 알림을 받ㄱ고 싶으면
    //ServletContextListener 의 규칙에 따라서 만들어야 한다
    
    //시작할 때
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized().......");
    }
    
    //종료할 때
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed()........");
    }
}
