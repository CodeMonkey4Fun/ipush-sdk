package org.codemonkey4fun.model;

/**
 * sandbox development environment and production environment of apple official addresses
 *
 * @Author Jonathan
 * @Date 2019/12/27
 */
public enum ApnsServer {

    DEV("api.development.push.apple.com", 443),
    PROD("api.push.apple.com", 443),
    //You can alternatively use port 2197 when communicating with APNs.
    // You might do this, for example, to allow APNs traffic through your firewall but to block other HTTPS traffic.
    DEV_ALT("api.development.push.apple.com", 2197),
    PROD_ALT("api.push.apple.com", 2197);


    private String host;
    private int port;

    ApnsServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String host() {
        return this.host;
    }

    public int port() {
        return this.port;
    }


}
