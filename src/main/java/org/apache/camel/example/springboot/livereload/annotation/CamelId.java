package org.apache.camel.example.springboot.livereload.annotation;

import java.lang.annotation.*;

/**
 * Created by Bowie on 18/12/2016.
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CamelId {

    String value();

}
