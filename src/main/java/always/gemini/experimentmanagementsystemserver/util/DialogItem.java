package always.gemini.experimentmanagementsystemserver.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DialogItem {

    int TYPE_Spinner = 0;
    int TYPE_EditText = 1;

    /**
     * 名称
     */
    String name();

    /**
     * id 越小越在前面
     */
    int id();

    int type();

    String hint() default "";
}
