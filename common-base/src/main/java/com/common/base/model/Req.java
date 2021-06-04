package com.common.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.validation.Valid;
import java.io.Serializable;
@Data
@ApiModel(value = "req", description = "请求信息")
public class Req<T> implements Serializable {

    @ApiModelProperty("请求头")
    private ReqHeader header;

    @ApiModelProperty(value = "ip地址")
    public String ip;

    @ApiModelProperty("请求数据")
    @Valid
    private T data;

    public Req(T data) {
        this.data = data;
    }

    public Req() {
    }
}

