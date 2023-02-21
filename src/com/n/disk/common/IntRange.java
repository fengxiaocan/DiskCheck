package com.n.disk.common;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Documented
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE, ANNOTATION_TYPE})
public @interface IntRange {
    /** Smallest value, inclusive */
    long from() default Long.MIN_VALUE;

    /** Largest value, inclusive */
    long to() default Long.MAX_VALUE;
}
