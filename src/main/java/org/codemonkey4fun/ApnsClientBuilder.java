package org.codemonkey4fun;

import lombok.Getter;
import org.codemonkey4fun.model.ApnsServer;

/**
 * APNs configuration builder
 * @Author Jonathan
 * @Date 2020/1/2
 **/
public class ApnsClientBuilder {

    @Getter
    private ApnsServer apnsServer = ApnsServer.PROD;

    @Getter
    private Long idlePingInterval = 1L;// 1 second

    @Getter
    private Long connectTimeout = 5L; // 5 second

    @Getter
    private Long readTimeout = 5L;// 5 second

    @Getter
    private Long writeTimeout = 5L;//5 second

    @Getter
    private Long keepAliveDuration = 600L;// 10 min

    @Getter
    private Integer maxIdleConnections = 10;

    @Getter
    private Integer maxRequests = 100;

    @Getter
    private Integer maxRequestsPerHost = 100;

    public ApnsClientBuilder setApnsServer(ApnsServer apnsServer) {
        this.apnsServer = apnsServer;
        return this;
    }

    public ApnsClientBuilder setIdlePingInterval(long idlePingInterval) {
        this.idlePingInterval = idlePingInterval;
        return this;
    }

    public ApnsClientBuilder setReadTimeoutMillis(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public ApnsClientBuilder setWriteTimeoutMillis(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public ApnsClientBuilder setConnectTimeoutMillis(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public ApnsClientBuilder setKeepAliveDuration(long keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
        return this;
    }

    public ApnsClientBuilder setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
        return this;
    }

    public ApnsClientBuilder setMaxRequests(Integer maxRequests) {
        this.maxRequests = maxRequests;
        return this;
    }

    public ApnsClientBuilder setMaxRequestsPerHost(Integer maxRequestsPerHost) {
        this.maxRequestsPerHost = maxRequestsPerHost;
        return this;
    }

    public ApnsClient build() {
        return new ApnsClient(this);
    }


}
