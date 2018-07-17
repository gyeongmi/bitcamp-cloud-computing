import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.Repository;

public class Test {
    
    static ArrayList<Object> objPool = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        ClassLoader defaultClassLoader =
                ClassLoader.getSystemClassLoader();
        
        URL url = defaultClassLoader.getResource(
                "bitcamp/pms");
        //출력: file:/C:/Users/bit-user/git/bitcamp-cloud-computing/bitcamp-web-099/bin/main/bitcamp/pms
        File file = new File(url.toURI());
        //출력: C:\Users\bit-user\git\bitcamp-cloud-computing\bitcamp-web-099\bin\main\bitcamp\pms
        
        findClass(file, "bitcamp.pms");
        
        /*for(Object obj : objPool) {
            System.out.println(obj.getClass().getName());
            //class bitcamp.pms.controller.MemberAddController
        }*/
        
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
         
         //3. 람다 문법. 직접 객체를 만들 필요 없이(new FileFilter()) 인터페이스 이름 지정할 필요가 없음(메소드 파라미터가 FileFilter이기 떄문)
         // 파일 객체를 파라미터로 1개 받는(File pathname){} 이런 몸체를 가진 함수를 만들면 대신해서 익명클래스를 만들어 던짐(??)
         
         File[] subFiles = path.listFiles((File pathname)->{
            if(pathname.isDirectory())
                return true;
            if(pathname.isFile() && pathname.getName().endsWith(".class"))
                return true;
            return false;
         });
         

         for(File subFile : subFiles) {
             if(subFile.isFile()) {
               //System.out.println(subFile.getAbsolutePath()); //클래스의 절대경로
               //System.out.println(packageName + "."+ subFile.getName()); ///패키지이름을 포함한 경로
               //System.out.println(packageName + "."+ subFile.getName().replace(".class",""));  //bitcamp.pms.annotation + . +Component
                String className = packageName + "." + subFile.getName().replace(".class","");
                createObject(className);
             }
             else {
                 findClass(subFile, packageName + "." + subFile.getName());
             }
         }
    }

    private static void createObject(String className) {
        try {
            //클래스 이름 (패키지명 + 클래스명) 으로 .class 파일을 찾아 로딩한다.
            Class<?> clazz = Class.forName(className);//클래스 로딩
            //여러 종류의 클래스를 저장하는 객체 => Class<?>
            
            //@Componente, @Controller, @Repository 애노테이션이 붙은 클래스가 아니라면 객체를 생성하지 않는다.
            if(clazz.getAnnotation(Component.class) == null && 
                    clazz.getAnnotation(Controller.class) == null &&
                    clazz.getAnnotation(Repository.class) == null) 
                return;
            
           //객체를 저장할 때 사용할 이름을 알아낸다.
            String objName = getObjectName(clazz);
            System.out.println(objName);

        /*  객체를 저장할 때 사용할 이름 출력
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
            System.out.println(objName);
            
                           출력: @Controller("이름 지정") 이름 지정했던 대로 나옴
            /member/add
            /member/delete
            /member/list
            /member/update
            /member/view
         */
            //클래스 정보를 보고 기본 생성자를 알아낸다.
            
            Constructor<?> constructor = clazz.getConstructor(); //파라미터가 없는 기본 생성자(default 생성자)
            //기본 생성자를 리턴하고 생성자의 제네릭 경고가 안 뜨기 위해 Constructor<?>
            
            //기본 생성자를 호출하여 객체를 생성한 후 객체 보관소에 저장한다.
            objPool.add(constructor.newInstance()); //오브젝트에 인스턴스를 리턴

            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            /*
            bitcamp.pms.anotation.*.<init>() 에러가 뜨는데
                           이유는
            MemberAddController, MemberDeleteController,
            MemberUpdateController, MemberViewController, MemberDao 에
                           기본 생성자를 만들지 않아서다.
            
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
