package org.codemonkey4fun.service.internal;

import org.codemonkey4fun.model.DeliveryPriority;

/**
 * APNs notification common APIs
 */
public interface NotificationService {

    Long getMsgId();

    String getDeviceToken();

    String getPayload();

    String getTopic();

    Long getExpiration();

    DeliveryPriority getPriority();

    String getCollapseId();


}
