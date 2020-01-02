package org.codemonkey4fun.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Notification data model
 *
 * @Author Jonathan
 * @Date 2020/1/2
 **/
@Data
@NoArgsConstructor
public class ApnsNotification implements Notification {
    private Long msgId;
    private AuthenticationInstance provider;
    private String deviceToken;
    private String payload;
    private String topic;
    private Long expiration;
    private DeliveryPriority priority;
    private String collapseId;

    /**
     * @param deviceToken the device token to which this push notification should be delivered; must not be {@code null}
     * @param topic       the topic to which this notification should be sent; must not be {@code null}
     * @param payload     the payload to include in this push notification; must not be {@code null}
     */
    public ApnsNotification(Long msgId, AuthenticationInstance provider, String deviceToken, String payload, String topic) {
        this.msgId = msgId;
        this.provider = provider;
        this.deviceToken = deviceToken;
        this.payload = payload;
        this.topic = topic;
    }

    /**
     * @param deviceToken the device token to which this push notification should be delivered; must not be {@code null}
     * @param topic       the topic to which this notification should be sent; must not be {@code null}
     * @param payload     the payload to include in this push notification; must not be {@code null}
     * @param expiration  the time at which Apple's servers should stop trying to deliver this message; if
     *                    {@code null}, no delivery attempts beyond the first will be made
     * @param priority    the priority with which this notification should be delivered to the receiving device
     * @param collapseId  the "collapse identifier" for this notification, which allows it to supersede or be superseded
     *                    by other notifications with the same identifier
     */
    public ApnsNotification(Long msgId, AuthenticationInstance provider, String deviceToken, String payload, String topic, Long expiration, DeliveryPriority priority, String collapseId) {
        this.msgId = msgId;
        this.provider = provider;
        this.deviceToken = deviceToken;
        this.payload = payload;
        this.topic = topic;
        this.expiration = expiration;
        this.priority = priority;
        this.collapseId = collapseId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
