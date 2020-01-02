package org.codemonkey4fun.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Data model of push result
 * @Author Jonathan
 * @Date 2020/1/2
 **/
@Data
public class SendResult {

    // logic code for tracking
    private Integer code;

    // custom message
    private String msg;

    // extra data object
    private Object result;

    @JSONField(serialize = false)
    public boolean isSuccess() {
        return code != null && code == 200;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
