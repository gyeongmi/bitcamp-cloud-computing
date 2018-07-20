package bitcamp.pms.listener;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import bitcamp.pms.context.ApplicationContext;

@WebListener
public class ContextLoaderListener 
    implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ContextLoaderListener 실행!");
        
        try {
            ApplicationContext iocContainer = 
                    new ApplicationContext("bitcamp.pms");
            //하위 패키지를 모두 뒤져서  여섯개의 클래스(memberaddcontroller, delc, upc, viewc, listc, MemberDao)
            //를 찾아내고 다 객체를 생성해서 이 객체 안에
            //objPool HashMap<Key,Value> 안에
            //@Autowired 안에 있는 애들끼리 꼽아준다.

            String resource = "bitcamp/pms/config/mybatis-config.xml";
            InputStream inputStream = 
                    Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory =
              new SqlSessionFactoryBuilder().build(inputStream);
            
            iocContainer.addBean("sqlSessionFactory", 
                    sqlSessionFactory);
            
            iocContainer.refresh();
            /*MemberDao memberDao = new MemberDao(sqlSessionFactory); 
            ServletContext sc = sce.getServletContext();
            sc.setAttribute("/member/list", new MemberListController(memberDao));
            sc.setAttribute("/member/view", new MemberViewController(memberDao));
            sc.setAttribute("/member/update", new MemberUpdateController(memberDao));
            sc.setAttribute("/member/delete", new MemberDeleteController(memberDao));
            sc.setAttribute("/member/add", new MemberAddController(memberDao));
             * */
            
            // 프론트 컨트롤러가 사용할 수 있도록 IoC 컨테이너를
            // ServletContext 보관소에 저장해 둔다.
            ServletContext sc = sce.getServletContext();
            sc.setAttribute("iocContainer", iocContainer);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



