package org.codemonkey4fun.model;

/**
 * APNs notification common APIs
 */
public interface Notification {

    Long getMsgId();

    AuthenticationInstance getProvider();

    String getDeviceToken();

    String getPayload();

    String getTopic();

    Long getExpiration();

    DeliveryPriority getPriority();

    String getCollapseId();


}
