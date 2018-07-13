package bitcamp.pms.context;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import org.apache.ibatis.io.Resources;

import bitcamp.pms.annotation.Autowired;
import bitcamp.pms.annotation.Component;
import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.Repository;

public class ApplicationContext {
    
    HashMap<String,Object> objPool = new HashMap<>();
    
    /* 테스트
    public static void main(String[] args) throws Exception{
        ApplicationContext iocContainer =
                new ApplicationContext("bitcamp.pms");
    }
    */
    
    
    public ApplicationContext(String packageName) throws Exception {
        String filePath = packageName.replace('.', '/');
        
        /* 다음에서 사용할 Resources.getResourceAsFile()이 
         * 대충 다음과 같이 되어 있다는 것이다.
        ClassLoader defaultClassLoader = 
                ClassLoader.getSystemClassLoader();
        System.out.println(filePath); //테스트 그러나 톰캣서버에서 못찾음
        URL url = defaultClassLoader.getResource(filePath);
        File dir = new File(url.toURI());
        */
        
        //마이바티스에는 Resources가 있고 그걸 써야한다.
        File dir = Resources.getResourceAsFile(filePath);
        //System.out.println(dir.getAbsolutePath()); //테스트
        
        findClass(dir, packageName);
        injectDependency();
    }
    
    
    private void injectDependency() throws Exception {
        // 내부에서 의존 객체를 주입하는 것
        
        // objPool 보관소에 저장된 모든 객체를 꺼낸다.
        Collection<Object> objList = objPool.values();
        // Collection<> 은 arraylist의 list라는데..? 잘 모르겟다
        
        for (Object obj : objList) {
            // 객체의 클래스 정보를 추출한다.
            Class<?> clazz = obj.getClass();
            
            // 해당 클래스의 모든 메서드를 추출한다.
            Method[] methods = clazz.getMethods();
            
            for (Method m : methods) {
                // 메소드만큼 반복문 돌린다
                
                
                // 각 객체에 존재하는 메서드 중에서 @Autowired가 붙은 셋터를 찾아낸다. (?메소드에서 getAnnotation 찾음?)
                // => 셋터가 아니면 무시
                if (!m.getName().startsWith("set"))
                    continue;
                    //셋으로 시작하지 않는다면 다음 continue
                
                // => @Autowired가 붙지 않았으면 무시
                // set은 set인데 Autowired가 선언됐는지
                if (m.getAnnotation(Autowired.class) == null)
                    continue;
                
                // => 파라미터 개수가 한 개가 아니라면 무시
                if (m.getParameterTypes().length != 1)
                    continue;
                    //파라미터 개수(m.getParmeterTypes() 는 배열을 의미)
                
                // 셋터의 파라미터 타입을 알아낸다.
                Class<?> paramType = m.getParameterTypes()[0];
                //리턴되는 것은 배열. 오로지 한 개니까 0번째 것 저장
                
                try {
                    // 셋터의 파라미터 타입에 해당하는 객체를 objPool 보관소에서 꺼낸다.
                    Object dependency = getBean(paramType);
                
                    // 셋터를 호출하여 의존 객체를 주입한다.
                    m.invoke(obj, dependency);
                    // 인스턴스 주소 obj
                } catch (Exception e) {
                    System.out.println("error: " + e.getMessage());
                    // 의존 객체가 없으면 셋터를 호출하지 않는다.
                    // 그냥 무시한다.
                }
                
            }
        }
        
    }
    
    public void refresh() throws Exception {
        // refresh() 다시 처음부터 의존 객체를 주입하라는 것
        // 외부에서 의존객체를 주입하는 것
        injectDependency();
    }

    public Object getBean(Class<?> type) {
        Collection<Object> objList = objPool.values();
        for (Object obj : objList) {
            if (type.isInstance(obj)) 
                //이 객체가 주어진 타입이 인스턴스면 리턴
                return obj;
        }
        throw new RuntimeException(type.getName() + 
                "의 객체가 존재하지 않습니다.");
    }
    
    public Object getBean(String name) {
        Object obj = objPool.get(name);
        if (obj == null) 
            throw new RuntimeException(name + 
                    " 이름의 객체가 존재하지 않습니다.");
        return obj;
    }
    
    public void addBean(String name, Object obj) {
        objPool.put(name, obj);
    }
    
    private void findClass(File path, String packageName) {
        
        File[] subFiles = path.listFiles((File pathname) -> {
            if (pathname.isDirectory())
                return true;
            if (pathname.isFile() && 
                    pathname.getName().endsWith(".class")) 
                return true;
            return false;
        });
        
        for (File subFile : subFiles) {
            if (subFile.isFile()) {
                String className = packageName + "." + 
                        subFile.getName().replace(".class", "");
                createObject(className);
            } else {
                findClass(subFile, 
                        packageName + "." + subFile.getName());
            }
        }
    }

    private void createObject(String className) {
        try {
            // 클래스 이름(패키지명 + 클래스명)으로 .class 파일을 찾아 로딩한다.
            Class<?> clazz = Class.forName(className);
            
            //@Component, @Controller, @Repository 애노테이션이 
            //붙은 클래스가 아니라면 객체를 생성하지 않는다.
            if (clazz.getAnnotation(Component.class) == null &&
                    clazz.getAnnotation(Controller.class) == null &&
                    clazz.getAnnotation(Repository.class) == null)
                return;
            
            
            // 객체를 저장할 때 사용할 이름을 알아낸다.
            String objName = getObjectName(clazz);
            
            // 클래스 정보를 보고 기본 생성자를 알아낸다.
            Constructor<?> constructor = clazz.getConstructor();

            // 기본 생성자를 호출하여 객체를 생성한 후 객체 보관소에 저장한다.
            objPool.put(objName, constructor.newInstance());
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getObjectName(Class<?> clazz) throws Exception {
        String objName = null;
        
        Component compAnno = clazz.getAnnotation(Component.class);
        if (compAnno != null) 
            objName = compAnno.value();
        
        Controller ctrlAnno = clazz.getAnnotation(Controller.class);
        if (ctrlAnno != null)
            objName = ctrlAnno.value();
        
        Repository repoAnno = clazz.getAnnotation(Repository.class);
        if (repoAnno != null)
            objName = repoAnno.value();
        
        if (objName.length() == 0) {
            return clazz.getCanonicalName();
        } else {
            return objName;
        }
    }
}
