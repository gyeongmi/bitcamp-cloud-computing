package bitcamp.pms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//정책,
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value() default "";
    // 프로퍼티기 떄문에 명사형으로 작성한다 기본값을 준다

}
