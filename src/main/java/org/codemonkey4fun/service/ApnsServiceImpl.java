package org.codemonkey4fun.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.codemonkey4fun.model.SendResult;
import org.codemonkey4fun.service.internal.NotificationService;
import org.codemonkey4fun.service.internal.PushCallBack;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * a implementation of APNs through HTTP2 protocol
 * @Author Jonathan
 * @Date 2020/1/2
 **/
public class ApnsServiceImpl extends AbstractApnsService {

    private OkHttpClient httpClient;
    private InetSocketAddress address;


    /**
     * initial connection information
     * @param address
     * @param idlePingInterval  heartbeat time limitation, seconds format
     * @param connectTimeout    connection time limitation, seconds format
     * @param readTimeout       read data time limitation, seconds format
     * @param writeTimeout      write data time limitation, seconds format
     * @param keepAliveDuration http2 keepalive, used for connection threads pool
     * @param maxIdleConnections    maximum idle threads limitation
     * @param maxRequests       maximum requests limitation
     * @param maxRequestsPerHost maximum requests limitation per each host
     */
    public ApnsServiceImpl(InetSocketAddress address, long idlePingInterval, long connectTimeout, long readTimeout,
                                long writeTimeout, long keepAliveDuration, int maxIdleConnections, int maxRequests,int maxRequestsPerHost) {

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(maxRequests);
        dispatcher.setMaxRequestsPerHost(maxRequestsPerHost);

        httpClient = new OkHttpClient.Builder().connectTimeout(connectTimeout, TimeUnit.SECONDS)
                            .readTimeout(readTimeout, TimeUnit.SECONDS)
                            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                            .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS))
                            .pingInterval(idlePingInterval, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .dispatcher(dispatcher)
                            .addInterceptor(null)
                            .build();

        this.address = address;
    }

    /**
     * send notification sequential way
     * @param notification
     * @return
     */
    @Override
    public SendResult push(NotificationService notification) {
        SendResult result = new SendResult();
        Request request = composeRequest(notification);

        Call call = httpClient.newCall(request);

        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            int code = response.code();
            String content = body != null? body.string() : null;
            Headers headers = response.headers();
            composeSendResult(result, code, content, headers);
        } catch (IOException e) {
            result.setMsg("Connect to APNs server occurs error");
            result.setCode(500);
            e.printStackTrace();

        }
        return result;
    }

    /**
     * send notification parallel way
     * @param notification
     * @param callBack
     */
    @Override
    public void push(NotificationService notification, PushCallBack callBack) {
        SendResult result = new SendResult();
        Request request = composeRequest(notification);

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                result.setCode(500);
                result.setMsg("Push message occurs exception");
                e.printStackTrace();
                if (callBack != null) {
                    callBack.callback(result);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                String data = response.body() != null ? response.body().string() : null;
                Headers headers = response.headers();
                composeSendResult(result, code, data, headers);
                if (callBack != null) {
                    callBack.callback(result);
                }
            }
        });
    }

    /**
     * Compose a Request object, refer <a>https://developer.apple.com/library/archive/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/CommunicatingwithAPNs.html</a>
     * schema: https://<host/>/v3/device/<token/>
     * custom headers related to apns settings.
     * @param notificationService
     * @return
     */
    private Request composeRequest(NotificationService notificationService) {
        Request.Builder builder = new Request.Builder()
                .url("https://" + address.getHostName() + "/3/device/" + notificationService.getDeviceToken())
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(notificationService)));
        if (notificationService.getTopic() != null && !"".equals(notificationService.getTopic())) {
            builder.header("apns-topic", notificationService.getTopic());
        }
        if (notificationService.getExpiration() != null) {
            builder.header("apns-expiration", String.valueOf(notificationService.getExpiration()));
        }
        if (notificationService.getPriority() != null) {
            builder.header("apns-priority", String.valueOf(notificationService.getPriority().code()));
        }
        if (notificationService.getCollapseId() != null && !"".equals(notificationService.getCollapseId())) {
            builder.header("apns-collapse-id", notificationService.getCollapseId());
        }

        return builder.build();
    }

    /**
     * Compose SendResult object by given parameters
     * @param code
     * @param data
     * @param headers
     * @return
     */
    private void composeSendResult(SendResult result, Integer code, String data, Headers headers) {
        result.setCode(code);
        String reason = "OK";
        if (data != null && !"".equals(data)) {
            reason = JSONObject.parseObject(data).getString("reason");
        }
        result.setMsg(reason);

        if (headers != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apns-id", headers.get("apns-id"));
            result.setResult(jsonObject);
        }
    }
}
