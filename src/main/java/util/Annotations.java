package util;

import entity.Right;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Annotations {
    String name();
    Right right();
    int order() default 0;
}