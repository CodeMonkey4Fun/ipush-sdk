# ipush-sdk
Java Client for Apple Push Notification service.

## How to use

**Some of configuration**

> To get details from Apple official website: <a>https://developer.apple.com/library/archive/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/APNSOverview.html#//apple_ref/doc/uid/TP40008194-CH8-SW1</a>

```
private static final String KEY_ID = <replace_me>
private static final String TEAM_ID = <replace_me>
private static final String AUTHENTICATION_TOKEN = <replace_me>
private static final String DEVICE_TOKEN = <replace_me>
private static final String PAYLOAD = "{\n" +
            "    \"aps\" : { \"alert\" : \"Hello World\" },\n" +
            "    \"acme2\" : [ \"bang\",  \"whiz\" ]\n" +
"}";
private static final String TOPIC = <replace_me>

```


**Use our SDK to push message on synchronized way** 

```
// ApnsServer.DEV / PROD / DEV_ALT / PROD_ALT
ApnsClient client = new ApnsClientBuilder().
						setApnsServer(ApnsServer.PROD).build();

// the first parameter of ApnsNotification 1L is message id for tracking purpose
Notification notification = new ApnsNotification(1L, AuthenticationInstance.getInstance(KEY_ID, TEAM_ID,AUTHENTICATION_TOKEN), deviceToken, PAYLOAD, topic);

SendResult result = client.push(notification);
System.out.println(result.toString());
    
```

**Use our SDK to push message on asynchronized way** 

```
// ApnsServer.DEV / PROD / DEV_ALT / PROD_ALT
ApnsClient client = new ApnsClientBuilder().
						setApnsServer(ApnsServer.PROD).build();

// the first parameter of ApnsNotification 1L is message id for tracking purpose
Notification notification = new ApnsNotification(1L, AuthenticationInstance.getInstance(KEY_ID,TEAM_ID,AUTHENTICATION_TOKEN), deviceToken, PAYLOAD, topic, null, DeliveryPriority.IMMEDIATE, null);

// We provided a callback method in async way to push message
client.push(notification, result -> System.out.println(result));
        
    
```