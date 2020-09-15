package org.patsimas.file.utils;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CsvBindByNameOrder {

    String[] value() default {};
}
