package org.codemonkey4fun;

import org.codemonkey4fun.model.Notification;
import org.codemonkey4fun.model.SendResult;
import org.codemonkey4fun.service.ApnsServiceImpl;
import org.codemonkey4fun.service.internal.ApnsService;
import org.codemonkey4fun.service.internal.PushCallBack;

import java.net.InetSocketAddress;

/**
 * high level push client
 *
 * @Author Jonathan
 * @Date 2020/1/2
 **/
public class ApnsClient {

    private ApnsService apnsService;

    public ApnsClient(ApnsClientBuilder apnsClientBuilder) {

        apnsService = new ApnsServiceImpl(new InetSocketAddress(apnsClientBuilder.getApnsServer().host(),
                apnsClientBuilder.getApnsServer().port()),
                apnsClientBuilder.getIdlePingInterval(),
                apnsClientBuilder.getReadTimeout(),
                apnsClientBuilder.getWriteTimeout(),
                apnsClientBuilder.getConnectTimeout(),
                apnsClientBuilder.getKeepAliveDuration(),
                apnsClientBuilder.getMaxIdleConnections(),
                apnsClientBuilder.getMaxRequests(),
                apnsClientBuilder.getMaxRequestsPerHost());

    }


    /**
     * synchronized push method
     *
     * @param notification
     * @return
     */
    public SendResult syncPush(Notification notification) {
        return apnsService.push(notification);
    }

    /**
     * asynchronized push method
     *
     * @param notification
     * @param callBack
     */
    public void asyncPush(Notification notification, PushCallBack callBack) {
        apnsService.push(notification, callBack);
    }
}
