package com.cloud.webcore.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "LocationVo", description = "地址信息")
public class LocationVo {

        private String  status;
        private String  country;
        private String  countryCode;
        private String  region;
        private String  regionName;
        private String  city;
        private String  zip;
        private String  lat;
        private String  lon;
        private String  timezone;
        private String  isp;
        private String  org;
        private String  as;
        private String  query;

}
