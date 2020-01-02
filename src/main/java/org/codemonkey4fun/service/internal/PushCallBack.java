package org.codemonkey4fun.service.internal;

import org.codemonkey4fun.model.SendResult;

/**
 * use to callback in parallel way
 */
public interface PushCallBack {

    void callback(SendResult result);
}
