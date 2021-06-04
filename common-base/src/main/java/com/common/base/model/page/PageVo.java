package com.common.base.model.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "pageVo", description = "分页查询结果")
@Data
public class PageVo<T> {

    @ApiModelProperty("总数")
    private Integer totalCount;

    @ApiModelProperty("总数简写")
    private String totalCountStr;

    @ApiModelProperty("列表数据")
    private List<T> list ;

    public PageVo() {

    }

    public  PageVo(Integer totalCount,List<T> list) {
        this.totalCount=totalCount;
        this.list=list;
    }
}
