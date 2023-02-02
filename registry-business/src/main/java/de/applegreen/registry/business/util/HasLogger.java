package de.applegreen.registry.business.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
public interface HasLogger {

    default Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
