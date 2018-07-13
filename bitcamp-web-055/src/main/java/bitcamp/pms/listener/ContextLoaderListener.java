package bitcamp.pms.listener;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import bitcamp.pms.dao.MemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ContextLoaderListener 실행!"); //서버리스타트하고 콘솔 확인
        
        try {
            String resource = "bitcamp/pms/config/mybatis-config.xml";
            InputStream inputStream;
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            
            MemberDao memberDao = new MemberDao(sqlSessionFactory);
            ServletContext sc = sce.getServletContext();
            sc.setAttribute("memberDao", memberDao); //memberDao 이런 문자열로 리턴해준다

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
}
