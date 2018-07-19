package bitcamp.pms.context;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import org.apache.ibatis.io.Resources;

import bitcamp.pms.annotation.Autowired;
import bitcamp.pms.annotation.Component;
import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.Repository;

public class ApplicationContext {
    HashMap<String, Object> objPool = new HashMap<>();
    
    /*public static void main(String[] args) throws Exception {
        //테스트
        ApplicationContext iocContainer =
                new ApplicationContext("bitcamp.pms");
    }*/
    
    public ApplicationContext(String packageName) throws Exception {
        String filePath = packageName.replace(".", "/");
        /* 다음에서 사용할 Resource.getResourceAsFile() 이
         * 대충 다음과 같이 되어 있다는 것이다.
        
        ClassLoader defaultClassLoader =
                ClassLoader.getSystemClassLoader();
        URL url = defaultClassLoader.getResource(filePath);
        File dir = new File(url.toURI());
        System.out.println(dir); 
        //C:\Users\bit-user\git\bitcamp-cloud-computing\bitcamp-web-099\bin\main\bitcamp\pms
        */
        
        //myBatis 에는 Resources 가 있고 그걸 써야 한다.
        File dir = Resources.getResourceAsFile(filePath);
        //System.out.println(dir.getAbsolutePath());
        //C:\Users\bit-user\git\bitcamp-cloud-computing\bitcamp-web-099\bin\main\bitcamp\pms
        
        findClass(dir, packageName);
        injectDependency();
    }
    
    private void injectDependency() {
        //내부에서 의존 객체를 주입하는 것
        
        //objPool 보관소에 저장된 모든 객체(value인 constructor.newInstance())를 꺼낸다.
        Collection<Object> objList = objPool.values();
        //Collection<>은 arraylist의 list라는데 잘 모르겠다>??
        
        for(Object obj : objList) {
            //객체의 클래스 정보를 추출한다.
            Class<?> clazz = obj.getClass();
            
            //해당 클래스의 모든 메서드를 추출한다.
            Method[] methods = clazz.getMethods();
            
            for(Method m : methods) {
                //존재하는 메소드 만큼 반복문 돌린다.
                
                // 각 객체에 존재하는 메서드 중에서 @AutoWired가 붙은 setter를 찾아낸다.
                // => sertter 가 아니면 무시
                if(!m.getName().startsWith("set"))
                    continue;
                //set으로 시작하지 않는다면 다음 continue
                
                // => @Autowired 가 붙지 않았으면 무시
                // set은 set인데 Autowired가 선언됐는지
                if(m.getAnnotation(Autowired.class) == null)
                    continue;
                
                // => 파라미터 갯수가 한 개가 아니라면 무시
                if(m.getParameterTypes().length != 1)
                    continue;
                //파라미터 갯수(m.getParamterTypes() = 배열을 의미한다)
                
                //setter의 파라미터 타입을 알아낸다.
                Class<?> paramType = m.getParameterTypes()[0];
                //리턴되는 것은 배열. 
                //System.out.println(paramType);
                //class bitcamp.pms.dao.MemberDao 5개
                //interface org.apache.ibatis.session.SqlSessionFactory 의 객체가 존재하지 않슴
                
                try {
                    //setter의 파라미터 타입에 해당하는 객체를 objPool 보관소에서 꺼낸다.
                    Object dependency = getBean(paramType);
                    
                    //setter 를 호출하여 의존 객체를 주입한다.
                    m.invoke(obj, dependency);
                    //인스턴스 주소 obj

                } catch (Exception e) {
                    System.out.println("error: " + e.getMessage());
                    //의존 객체가 없으면 setter를 호출하지 않는다.
                    //그냥 무시한다.
                }
            }
            
            
        }
        
    }
    
    public void refresh() throws Exception{
        //refresh() 다시 처음부터 의존 객체를 주입하라는 것
        //외부에서 의존객체를 주입하는 것
        injectDependency();
    }
    

    private Object getBean(Class<?> type) {
        Collection<Object> objList = objPool.values();
        for(Object obj : objList) {
            if(type.isInstance(obj))
                //이 객체가 주어진 타입이 인스턴스면 리턴
                
                return obj;
        }
        throw new RuntimeException(type.getName()+"의 객체가 존재하지 않습니다 .");
    }
    //Exception을 쓰게 되면 메소드에도 throws Exception 선언해줘야 하지만
    //RuntimeException 을 하면 선언하지 않아도 된다!!
    
    public Object getBean(String name) {
        Object obj = objPool.get(name); //objPool.get(key)
        if(obj == null)
            throw new RuntimeException(name + 
                    "이름의 객체가 존재하지 않습니다.");
        return obj;
    }
    
    public void addBean(String name, Object obj) {
        objPool.put(name, obj);
        //System.out.println("보여줘"+objPool.put(name, obj));
    }
    
     private void findClass(File path, String packageName) {

         File[] subFiles = path.listFiles((File pathname)->{
            if(pathname.isDirectory())
                return true;
            if(pathname.isFile() && pathname.getName().endsWith(".class"))
                return true;
            return false;
         });
         
         for(File subFile : subFiles) {
             if(subFile.isFile()) {
                String className = packageName + "." + subFile.getName().replace(".class","");
                createObject(className);
             }
             else {
                 findClass(subFile, packageName + "." + subFile.getName());
             }
         }
    }

    private void createObject(String className) {
        try {
            Class<?> clazz = Class.forName(className);//클래스 로딩
            
            if(clazz.getAnnotation(Component.class) == null && 
                    clazz.getAnnotation(Controller.class) == null &&
                    clazz.getAnnotation(Repository.class) == null) 
                return;
            
            String objName = getObjectName(clazz);

            Constructor<?> constructor = clazz.getConstructor(); //파라미터가 없는 기본 생성자(default 생성자)
            
            objPool.put(objName, constructor.newInstance());
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        
    }

    private String getObjectName(Class<?> clazz)  {
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
