package org.codemonkey4fun.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import okhttp3.*;
import okio.Buffer;
import org.codemonkey4fun.model.ApnsNotification;
import org.codemonkey4fun.model.AuthenticationInstance;
import org.codemonkey4fun.model.Notification;
import org.codemonkey4fun.util.JwtUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Override Http2 client interceptor for caching APNs token
 *
 * @Author Jonathan
 * @Date 2020/1/2
 **/
public class ApnsTokenInterceptor implements Interceptor {

    // caching authentication token by application
    private static final LoadingCache<AuthenticationInstance, String> AUTHENTICATION_TOKEN_STRING_LOADING_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(50, TimeUnit.MINUTES)
            .build(new CacheLoader<AuthenticationInstance, String>() {
                @Override
                public String load(AuthenticationInstance authenticationInstance) throws Exception {
                    Date refreshAt = new Date();
                    String token = JwtUtil.getToken(authenticationInstance.getKeyId(), authenticationInstance.getTeamId(),
                            authenticationInstance.getToken(), refreshAt.getTime() / 1000);
                    return token;
                }
    });


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originRequest = chain.request();
        Request currentRequest = build(body(originRequest), originRequest);
        Response originalResponse = chain.proceed(currentRequest);
        MediaType contentType = originalResponse.body().contentType();
        String data = originalResponse.body().string();
        return originalResponse.newBuilder().body(ResponseBody.create(data, contentType)).build();
    }

    private Request build(Notification notification, Request origin) {
        Request request = null;
        try {
            request = origin.newBuilder().post(RequestBody.create(notification.getPayload(), MediaType.parse("application/json; charset=utf-8")))
                    .header("authorization", "bearer " + AUTHENTICATION_TOKEN_STRING_LOADING_CACHE.get(notification.getProvider()))
                    .build();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return request;
    }
    private Notification body(final Request request) {
        Notification notification = null;
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            String body = buffer.readUtf8();
            JSON json = (JSON) JSON.toJSON(body);
            notification = JSON.toJavaObject(json, ApnsNotification.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notification;
    }
}
