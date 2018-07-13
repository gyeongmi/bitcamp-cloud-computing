package bitcamp.pms.listener;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import bitcamp.pms.controller.MemberAddController;
import bitcamp.pms.controller.MemberDeleteController;
import bitcamp.pms.controller.MemberListController;
import bitcamp.pms.controller.MemberUpdateController;
import bitcamp.pms.controller.MemberViewController;
import bitcamp.pms.dao.MemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ContextLoaderListener 실행!"); //서버리스타트하고 콘솔 확인
        //5. try안에 넣음
        try {
            //1.여기에서 만든다
            String resource = "bitcamp/pms/config/mybatis-config.xml"; //3.
            /*FileInputStream in = new FileInputStream(file); 어느경로에있는지 정확하게 기입해야돼서
            */
            InputStream inputStream = Resources.getResourceAsStream(resource); //4.읽어들이는 객체만들어줌(inpustStream)
            SqlSessionFactory sqlSessionFactory =
              new SqlSessionFactoryBuilder().build(inputStream);
            
            MemberDao memberDao = new MemberDao(sqlSessionFactory); 
            
            ServletContext sc = sce.getServletContext();
            sc.setAttribute("/member/list", new MemberListController(memberDao));
            sc.setAttribute("/member/view", new MemberViewController(memberDao));
            sc.setAttribute("/member/update", new MemberUpdateController(memberDao));
            sc.setAttribute("/member/delete", new MemberDeleteController(memberDao));
            sc.setAttribute("/member/add", new MemberAddController(memberDao));
            

            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
