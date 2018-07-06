package bitcamp.pms.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import bitcamp.pms.dao.MemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ContextLoaderListener 실행!"); //서버리스타트하고 콘솔 확인
        MemberDao memberDao = new MemberDao(
                "jdbc:mysql://13.124.153.245:3306/studydb",
                "study", "1111");
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("memberDao", memberDao); //memberDao 이런 문자열로 리턴해준다
    }
    

}
