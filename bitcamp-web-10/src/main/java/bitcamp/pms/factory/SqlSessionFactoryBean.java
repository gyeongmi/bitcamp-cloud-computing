package bitcamp.pms.factory;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;

public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory> {
    
    // 이 클래스가 어떤 타입의 객체를 만들어 주는지 알려준다.
    // Ioc 컨테이너는 이 공장 객체가 어떤 타입의 객체를 만드는지;
    // 확인한 다음에 이 공장 객체로부터 갋을 얻어 컨테이너에 저장한다
    
    @Override
    public Class<?> getObjectType() {
        System.out.println("getObjectType() 호출됨");

        return SqlSessionFactory.class;
    }
    

    //sqlfactory를 만듬 getObject
    @Override
    public SqlSessionFactory getObject() throws Exception {
        System.out.println("getObject() 호출됨");
        String resource = "bitcamp/pms/config/mybatis-config.xml";
        InputStream inputStream = 
                Resources.getResourceAsStream(resource);

        return new SqlSessionFactoryBuilder().build(inputStream);

    }
    
    //이 클래스는 sqlsessionfactory를 만들어주는 클래스다

}
