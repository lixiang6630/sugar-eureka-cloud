package com.common.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//类似于BaseJson
@Data
@ApiModel(value = "resp", description = "响应信息")
public class Resp<T> {

    //成功code码
    private static final Integer SUCCESS_CODE = 200;

    //内部服务错误code码
    private static final Integer SERVER_ERROR = 500;

    //系统维护code码
    private static final Integer SERVER_MAINTAIN = 502;

    //系统维护提示信息
    private static final String SERVER_MAINTAIN_MSG = "系统维护中,请稍等再试";

    @ApiModelProperty("响应data")
    private T data;

    @ApiModelProperty("响应code,200正常，其他异常")
    private Integer code;

    @ApiModelProperty("提示信息msg，不管响应正常还是错误，提示信息都在这里")
    private String msg;

    @ApiModelProperty("响应时间,秒")
    private String time;

    public Resp() {
        this(null, SUCCESS_CODE, "");
    }

    public Resp(T result) {
        this(result, SUCCESS_CODE, "");
    }

    public Resp(Integer code, String msg) {
        this(null, code, msg);
    }

    public Resp(T data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.time = String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static <T> Resp<T> success() {
        return new Resp<T>(null, SUCCESS_CODE, "");
    }

    public static <T> Resp<T> successData(T data) {
        return new Resp<T>(data, SUCCESS_CODE, "");
    }

    public static <T> Resp<T> success(String msg) {
        return new Resp<T>(null, SUCCESS_CODE, msg);
    }

    public static <T> Resp<T> error(String msg) {
        return new Resp<T>(null, SERVER_ERROR, msg);
    }

    public static <T> Resp<T> fixing() {
        return new Resp<T>(null, SERVER_MAINTAIN, SERVER_MAINTAIN_MSG);
    }

    public boolean isSuccess() {
        if (code != null && SUCCESS_CODE == code.intValue()) {
            return true;
        }
        return false;
    }

}