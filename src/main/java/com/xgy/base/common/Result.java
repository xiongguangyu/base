package com.xgy.base.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口公共返回结果。
 *
 * @param <T> 业务数据类型
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 成功状态码。 */
    public static final int SUCCESS_CODE = 200;

    /** 失败状态码。 */
    public static final int ERROR_CODE = 500;

    /** 状态码。 */
    private Integer code;

    /** 提示信息。 */
    private String msg;

    /** 业务数据。 */
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功（带数据）。
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "success", data);
    }

    /**
     * 成功（自定义提示信息 + 数据）。
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(SUCCESS_CODE, msg, data);
    }

    /**
     * 成功（无数据）。
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "success", null);
    }

    /**
     * 失败（默认状态码 + 自定义提示信息）。
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ERROR_CODE, msg, null);
    }

    /**
     * 失败（自定义状态码 + 提示信息）。
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}
