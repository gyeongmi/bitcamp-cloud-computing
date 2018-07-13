package bitcamp.pms.test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test2 {
    public static void main(String[] args) throws Exception {
        //Spring IoC 컨테이너 객체 생성하기
        AnnotationConfigApplicationContext iocContainer =
                new AnnotationConfigApplicationContext(
                        MyspringConfig.class); //설정파일 이름.. 경로 찾아라..
        System.out.println(iocContainer.getBeanDefinitionCount());
        System.out.println("----------------------------");
        String[] names = iocContainer.getBeanDefinitionNames();
        for(String name : names) {
            System.out.printf("%s ==> %s\n", name,
                    iocContainer.getBean(name).getClass().getName());//클래스정보에서 클래스이름을 추출
                    
        }
        
        //%s엔 이름이 오고 이 이름에 해당하는 객체의 클래스 명은 %s\n
    }
}


