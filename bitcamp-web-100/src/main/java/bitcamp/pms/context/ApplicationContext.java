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
    
    public ApplicationContext(String packageName) throws Exception {
        String filePath = packageName.replace('.', '/');
        
        File dir = Resources.getResourceAsFile(filePath);
        
        findClass(dir, packageName);
        injectDependency();
    }
    
    private void injectDependency() throws Exception {
        Collection<Object> objList = objPool.values();
        
        for (Object obj : objList) {
            Class<?> clazz = obj.getClass();
            
            Method[] methods = clazz.getMethods();
          
            for (Method m : methods) {
                if (!m.getName().startsWith("set"))
                    continue;
                if (m.getAnnotation(Autowired.class) == null)
                    continue;
                
                if (m.getParameterTypes().length != 1)
                    continue;

                Class<?> paramType = m.getParameterTypes()[0];
                
                try {
                    Object dependency = getBean(paramType);
                
                    m.invoke(obj, dependency);
                } catch (Exception e) {
                    System.out.println("error: " + e.getMessage());
                }
            }
        }
    }
    
    public void refresh() throws Exception {
        injectDependency();
    }

    public Object getBean(Class<?> type) {
        Collection<Object> objList = objPool.values();
        for (Object obj : objList) {
            if (type.isInstance(obj)) 
                
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
            Class<?> clazz = Class.forName(className);
            
            if (clazz.getAnnotation(Component.class) == null &&
                    clazz.getAnnotation(Controller.class) == null &&
                    clazz.getAnnotation(Repository.class) == null)
                return;
            
            String objName = getObjectName(clazz);
            
            Constructor<?> constructor = clazz.getConstructor();

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
