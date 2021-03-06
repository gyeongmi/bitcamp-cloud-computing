package bitcamp.pms.test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test4 {
    public static void main(String[] args) throws Exception {
        //Spring IoC 컨테이너 객체 생성하기
        ClassPathXmlApplicationContext iocContainer =
                new ClassPathXmlApplicationContext(
                        "bitcamp/pms/test/application-context4.xml"); //설정파일 이름.. 경로 찾아라..
        System.out.println(iocContainer.getBeanDefinitionCount());
        System.out.println("----------------------------");
        String[] names = iocContainer.getBeanDefinitionNames();
        for(String name : names) {
            System.out.printf("%s => %s\n",name,
                    iocContainer.getBean(name).toString());
        }
        //ioc컨테이너 getbean 객체를 꺼내서
        //이름 => tostring 리턴값
        //tostring에 객체를 꽂는 법
    }
}


