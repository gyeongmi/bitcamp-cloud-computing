package bitcamp.pms.test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bitcamp.pms.context.ApplicationContext;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext iocContainer =
                new ClassPathXmlApplicationContext(
                        "bitcamp/pms/test/application-context.xml");
        System.out.println(iocContainer.getBeanDefinitionCount());
        System.out.println("------------------------------------");
        String[] names = iocContainer.getBeanDefinitionNames();
        for(String name : names) {
            System.out.println(iocContainer.getBean(name).getClass().getName());
        }
        
    }
}


