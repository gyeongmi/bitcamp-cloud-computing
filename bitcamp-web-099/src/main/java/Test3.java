import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.Repository;

public class Test3 {
    
    static ArrayList<Object> objPool = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        ClassLoader defaultClassLoader =
                ClassLoader.getSystemClassLoader();
        
        URL url = defaultClassLoader.getResource(
                "bitcamp/pms");
        //JVM이 클래스를 실행 -> ClassLoader 메모리 올리는 역할 함
        //defaultClassLoader를 얻어낸 후 bitcamp(패키지)의 실제 위치를 알아야 클래스 파일을 찾는다. -> getResource("bitcamp");

        File file = new File(url.toURI());
        /*
        file 객체를 얻으면 file을 통해서 절대경로를 알아낼 수 있다.
        file + directory 같이 묶어서 'file'이라고 부른다.
        subFiles = 파일 목록
         */

        //System.out.println("url 주소: "+url);
        //System.out.println("uri 주소: "+file);
        
        findClass(file, "bitcamp.pms");
        
      //기본 생성자를 호출하여 객체를 생성한 후 객체 보관소에 저장한 값. 인스턴스...
        for(Object obj : objPool) {
            System.out.println("add: "+obj.getClass().getName());
        }
        
    }
    
     static void findClass(File path, String packageName) {

         /*
         //1. 인터페이스 FileFilter을 MyFilter로 구현
         class MyFilter implements FileFilter{
             @Override
             public boolean accept(File pathname) {
                 System.out.println(pathname);
                 if(pathname.isDirectory()) //디렉토리(폴더) 존재 여부
                      return true;
                  if(pathname.isFile() && pathname.getName().endsWith(".class")) //파일 존재 여부 && 파일 이름이.calss로 끝나는지 여부
                      return true;
                 return false;
             }
         }
         //필터 규칙에 따른 모든 디렉토리와 .class로만 끝나는 것
         File[] subFiles = path.listFiles(new MyFilter());*/
         
         /*
         //2. 함수 하나 들어 있던 인터페이스(FileFilter) 를 new FileFilter 익명클래스로 집어넣는 방법
         File[] subFiles = path.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if(pathname.isDirectory()) //디렉토리(폴더) 존재 여부
                    return true;
                if(pathname.isFile() && pathname.getName().endsWith(".class")) //파일 존재 여부 && 파일 이름이.calss로 끝나는지 여부
                    return true;
                return false;
            }
        });*/
         
         //3. 람다 문법. 직접 객체를 만들 필요 없이(new FileFilter())
         // 2번처럼 인터페이스 이름(new FileFilter) 지정할 필요가 없음
         // listFiles(FileFilter filter) 메소드 파라미터가 FileFilter이기 때문이다
         // 파일 객체를 파라미터로 1개 받는(File pathname){} 이런 몸체를 가진 함수를 만들면 대신해서 익명클래스를 만들어 던진다
         
         
         File[] subFiles = path.listFiles((File pathname)->{
            if(pathname.isDirectory()) {
                //디렉토리(폴더) 존재 여부
                //System.out.println("isDirectory() : "+pathname); 
                return true;
            }
            if(pathname.isFile() && pathname.getName().endsWith(".class")) {
                //파일 존재 여부 && 파일 이름이.calss로 끝나는지 여부
                //System.out.println("isFile() : "+pathname);
                return true;
            }
            //System.out.println("둘 다 아니다");
            return false;
            
         });
         

         for(File subFile : subFiles) {
             if(subFile.isFile()) {
               /*
               System.out.println("클래스의 절대경로: "+subFile.getAbsolutePath()); //클래스의 절대경로
               System.out.println(packageName + "."+ subFile.getName()); ///패키지이름을 포함한 경로
                                  출력: bitcamp.pms.annotation + . + Component.class
                                  출력: bitcamp.pms.annotation + . +Component
               */             
                 //System.out.println("클래스 이름(패키지명 + 클래스명): "+packageName + "."+ subFile.getName().replace(".class",""));
                 String className = packageName + "." + subFile.getName().replace(".class","");
                 createObject(className);
             }
             else {
                 //System.out.println("위치: "+subFile);
                 findClass(subFile, packageName + "." + subFile.getName());
             }
         }
    }

    private static void createObject(String className) {
        try {
            //클래스 이름 (패키지명 + 클래스명) 으로 .class 파일을 찾아 로딩한다.
            Class<?> clazz = Class.forName(className);//클래스 로딩
            //여러 종류의 클래스를 저장하는 객체 => Class<?>
            
            //@Component, @Controller, @Repository 애노테이션이 붙은 클래스가 아니라면 객체를 생성하지 않는다.
            if(clazz.getAnnotation(Component.class) == null && 
                    clazz.getAnnotation(Controller.class) == null &&
                    clazz.getAnnotation(Repository.class) == null) 
                return; //아무것도 반환하지 않고 함수 종료
            
           //객체를 저장할 때 사용할 이름을 알아낸다. getObjectName(Class<?> clazz); 메소드 만듬
            String objName = getObjectName(clazz);
            System.out.println("objName : "+objName);

        /*  getObjectName(Class<?> clazz); 메소드로 뺐음
                           
                           객체를 저장할 때 사용할 이름 출력
            String objName = null;
            Component compAnno =
                    clazz.getAnnotation(Component.class);
            if(compAnno != null)
                objName = compAnno.value();
            Controller ctrlAnno = 
                    clazz.getAnnotation(Controller.class);
            if(ctrlAnno != null)
                objName = ctrlAnno.value();
            Repository repoAnno =
                    clazz.getAnnotation(Repository.class);
            if(repoAnno != null)
                objName = repoAnno.value();
            if(objName.length() == 0) {
                objName = clazz.getCanonicalName();
                
            }
            System.out.println(objName);
            
                           출력: @Controller(value="/member/*") 이름 지정했던 대로 나옴,
                           지정하지 않았던 @Repositroy 는  repoAnno != null 이어도 value를 설정하지 않았기 때문에
            objName 의 길이는 0일 것이고.. objName.length() == 0
            clazz.getCanonicalName()  함수로 기본 클래스의 표준 이름을 리턴한다.
            /member/add
            /member/delete
            /member/list
            /member/update
            /member/view
            bitcamp.pms.dao.MemberDao
         */
            
            //클래스 정보를 보고 기본 생성자를 알아낸다.
            Constructor<?> constructor = clazz.getConstructor(); //파라미터가 없는 기본 생성자(default 생성자)
            //기본 생성자를 리턴하고 생성자의 제네릭 경고가 안 뜨기 위해 Constructor<?>
            
            //System.out.println("construct:"+constructor);
            //출력: public bitcamp.pms.controller.MemberAddController()
            
            //기본 생성자를 호출하여 객체를 생성한 후 객체 보관소에 저장한다.
            objPool.add(constructor.newInstance()); //오브젝트에 인스턴스를 리턴
            System.out.println("constructor.newInstance() : "+constructor.newInstance());
                        
        } catch (Exception e) {
            System.out.println(e.getMessage());
            /*
            bitcamp.pms.anotation.*.<init>() 에러가 뜨는데
                           이유는
            MemberAddController, MemberDeleteController,
            MemberUpdateController, MemberViewController, MemberDao 에
                           기본 생성자를 만들지 않아서다. 가서 만들어 주면 됨.
            
            */
        } 
        
    }

    private static String getObjectName(Class<?> clazz)  {
        String objName = null;
        Component compAnno =
                clazz.getAnnotation(Component.class);
        if(compAnno != null)
            objName = compAnno.value();
        Controller ctrlAnno = 
                clazz.getAnnotation(Controller.class);
        if(ctrlAnno != null)
            objName = ctrlAnno.value();
        Repository repoAnno =
                clazz.getAnnotation(Repository.class);
        if(repoAnno != null)
            objName = repoAnno.value();
        if(objName.length() == 0) {
            return clazz.getCanonicalName();
        }
        else {
            return objName;
        }
    }

    
}
