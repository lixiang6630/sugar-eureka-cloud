package com.common.generator;

import com.common.generator.model.GenInfo;

/**
 * 生成代码示例
 *
 * @author: LX
 */
public class CodeCreateExample {

    public static void main(String[] args) {
        GenInfo.build()
                //设置模块路径
                .setProjectPath("/Users/LX/workGroup/xuebapeiban/xueba/xueba-mall/xueba-mall-rest")
                //作者
                .setAuthor("LX")
                //数据库名
                .setDbName("xueba")
                //设置controller包名
                .setControllerPackageName("com.cdnhxx.xueba.mall.rest.controller")
                //设置service包名
                .setServicePackageName("com.cdnhxx.xueba.mall.rest.service")
                //设置dao包名
                .setEntityPackageName("com.cdnhxx.xueba.mall.rest.model.pojo")
                .setMapperPackageName("com.cdnhxx.xueba.mall.rest.dao.mapper")
                .setXmlPackageName("com.cdnhxx.xueba.mall.rest.dao.mapping")
                //设置是否生成controller
                .setGenController(false)
                //设置是否生成service
                .setGenService(false)
                //设置是否覆盖已存在文件
                .setOverrideExistFile(true)
                //生成
                .over();
    }

}
