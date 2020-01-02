package org.codemonkey4fun.service.internal;

import org.codemonkey4fun.model.SendResult;

/**
 *
 */
public interface PushCallBack {

    void callback(SendResult result);
}
