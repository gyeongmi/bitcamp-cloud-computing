package bitcamp.pms.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebListener
public class ContextLoaderListener 
    implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ContextLoaderListener 실행!");
        
        try {
            ClassPathXmlApplicationContext iocContainer = 
                    new ClassPathXmlApplicationContext(
                            "bitcamp/pms/config/application-context.xml");
            String[] names = iocContainer.getBeanDefinitionNames();
            System.out.println("-----------------------------------------");
            for(String name : names) {
                System.out.printf("%s ==> %s\n", name,
                        iocContainer.getBean(name).getClass().getName());
            }
            System.out.println("-----------------------------------------");
            
            
            //스프링 ioc에는 addbean이 없다
            
            ServletContext sc = sce.getServletContext();
            sc.setAttribute("iocContainer", iocContainer);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



