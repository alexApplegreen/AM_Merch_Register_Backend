package de.applegreen.registry.business.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */

public class AdviceAnnotations {

    /**
     * Pointcut definition by annotation:
     * Purchase has been registered
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PurchaseCommit {}
}
