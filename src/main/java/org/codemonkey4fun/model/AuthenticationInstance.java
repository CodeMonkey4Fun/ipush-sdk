package org.codemonkey4fun.model;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * A JWT object to securely connect to APNs
 *
 * @Author Jonathan
 * @Date 2019/12/27
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationInstance {

    private String keyId;
    private String teamId;
    private String token;

    /**
     * @param keyId  A 10-character key identifier (kid) key, obtained from your developer account
     * @param teamId The issuer (iss) registered claim key, whose value is your 10-character Team ID, obtained from your developer account
     * @param token  The issued at (iat) registered claim key, whose value indicates the time at which the token was generated, in terms of the number of seconds since Epoch, in UTC
     * @return
     */
    public static AuthenticationInstance getInstance(String keyId, String teamId, String token) {
        return new AuthenticationInstance(keyId, teamId, token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationInstance that = (AuthenticationInstance) o;
        return Objects.equals(keyId, that.keyId) &&
                Objects.equals(teamId, that.teamId) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        // an odd prime
        final int prime = 31;
        // an arbitrary number
        int result = 17;
        result = prime * result + ((keyId == null) ? 0 : keyId.hashCode());
        result = prime * result + ((teamId == null) ? 0 : teamId.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
