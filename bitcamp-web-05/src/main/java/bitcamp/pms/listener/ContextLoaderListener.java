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
             * 
            */
            InputStream inputStream = Resources.getResourceAsStream(resource); //4.읽어들이는 객체만들어줌(inpustStream)
            SqlSessionFactory sqlSessionFactory =
              new SqlSessionFactoryBuilder().build(inputStream);
            
            MemberDao memberDao = new MemberDao(sqlSessionFactory); //2.
            ServletContext sc = sce.getServletContext();
            sc.setAttribute("memberDao", memberDao); //memberDao 이런 문자열로 리턴해준다
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
