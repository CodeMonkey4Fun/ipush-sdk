package org.codemonkey4fun.service.internal;

import org.codemonkey4fun.model.Notification;
import org.codemonkey4fun.model.SendResult;

/**
 * APNs common APIs
 */
public interface ApnsService {

    /**
     * send notification sequential way
     * @param notification
     * @return
     */
    SendResult push(Notification notification);

    /**
     * send notification parallel way
     * @param notification
     * @param callBack
     */
    void push(Notification notification, PushCallBack callBack);
}
