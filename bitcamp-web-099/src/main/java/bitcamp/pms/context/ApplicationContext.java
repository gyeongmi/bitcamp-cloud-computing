package bitcamp.pms.context;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.Repository;

public class ApplicationContext {
    static ArrayList<Object> objPool = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        ClassLoader defaultClassLoader =
                ClassLoader.getSystemClassLoader();
        
        URL url = defaultClassLoader.getResource(
                "bitcamp/pms");
        File file = new File(url.toURI());
        
        findClass(file, "bitcamp.pms");
        
        
    }
    
     static void findClass(File path, String packageName) {

         File[] subFiles = path.listFiles(new MyFilter());
     
         
       
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

    private static void createObject(String className) {
        try {
            Class<?> clazz = Class.forName(className);//클래스 로딩
            
            if(clazz.getAnnotation(Component.class) == null && 
                    clazz.getAnnotation(Controller.class) == null &&
                    clazz.getAnnotation(Repository.class) == null) 
                return;
            
            String objName = getObjectName(clazz);
            System.out.println(objName);

            Constructor<?> constructor = clazz.getConstructor(); //파라미터가 없는 기본 생성자(default 생성자)
            
            objPool.add(constructor.newInstance()); 

            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
