package com.iqgames.sudoku.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by qili on 20/11/2016.
 */
public interface Loggable {
    default Log getLog() {
        return LogFactory.getLog(this.getClass());
    }
}
