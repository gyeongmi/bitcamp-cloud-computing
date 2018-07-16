package bitcamp.pms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
    
    String value() default "";//디폴트는 빈 문자열

}
