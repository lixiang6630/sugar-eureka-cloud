package com.common.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;

@Data
@ApiModel(value = "reqHeader", description = "请求头信息")
public class ReqHeader {

    @ApiModelProperty("app应用名称")
    private String app;

    @ApiModelProperty("app应用版本")
    private String appVersion;

    @ApiModelProperty("手机设备系统,android 或则 ios")
    private String deviceOs;

    @ApiModelProperty("手机设备系统版本")
    private String deviceOsVersion;

    @ApiModelProperty("手机设备的唯一标识")
    private String deviceToken;

    @ApiModelProperty("手机系统语言 英语：en  汉语：zh")
    private String language;

}
