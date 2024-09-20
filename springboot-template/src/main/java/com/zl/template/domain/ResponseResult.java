package com.zl.template.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * 响应类
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) //Json序列化时会忽略掉Null的属性，只有非空属性才会被序列化
@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private T data;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
