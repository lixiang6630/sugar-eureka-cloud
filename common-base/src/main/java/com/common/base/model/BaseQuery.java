package com.common.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "baseQuery", description = "基本查询")
@Data
public class BaseQuery {

    @ApiModelProperty("第几页")
    public Integer page = 1;

    @ApiModelProperty("每页多少条")
    public Integer rows = 5;

    @ApiModelProperty(value = "操作用户Id",hidden = true)
    public Long userId;

    @ApiModelProperty(value = "ip地址",hidden = true)
    public String ip;
}
