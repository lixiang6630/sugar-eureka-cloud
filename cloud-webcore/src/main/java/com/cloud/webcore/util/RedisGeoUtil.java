package com.cloud.webcore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Redis工具类
 *
 * @author WangFan
 * @date 2018-02-24 下午03:09:50
 * @version 1.1 (GitHub文档: https://github.com/whvcse/RedisUtil )
 */
@Component
public class RedisGeoUtil {
	@Autowired
	private StringRedisTemplate redisTemplate;
	/**
	 * 添加经纬度信息
	 *
	 * redis 命令：geoadd key 116.405285 39.904989 "北京"
	 */
	public Long geoAdd(String key, Point point, String member) {
		if (redisTemplate.hasKey(key)) {
			redisTemplate.opsForGeo().remove(key, member);
		}
		return redisTemplate.opsForGeo().add(key, point, member);
	}

	/**
	 * 查找指定key的经纬度信息，可以指定多个member，批量返回
	 *
	 * redis命令：geopos key 北京
	 */
	public List<Point> geoGet(String key, String... members) {
		return redisTemplate.opsForGeo().position(key, members);
	}

	/**
	 * 返回两个地方的距离，可以指定单位，比如米m，千米km，英里mi，英尺ft
	 *
	 * redis命令：geodist key 北京 上海
	 */
	public Distance geoDist(String key, String member1, String member2, Metric metric) {
		return redisTemplate.opsForGeo().distance(key, member1, member2, metric);
	}

	/**
	 * 根据给定的经纬度，返回半径不超过指定距离的元素
	 *
	 * redis命令：georadius key 116.405285 39.904989 100 km WITHDIST WITHCOORD ASC
	 * COUNT 5
	 */
	public GeoResults<RedisGeoCommands.GeoLocation<String>> nearByXY(String key, Circle circle, long count) {
		// includeDistance 包含距离
		// includeCoordinates 包含经纬度
		// sortAscending 正序排序
		// limit 限定返回的记录数
		RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
				.includeDistance().includeCoordinates().sortAscending().limit(count);
		return redisTemplate.opsForGeo().radius(key, circle, args);
	}

	/**
	 * 根据指定的地点查询半径在指定范围内的位置
	 *
	 * redis命令：georadiusbymember key 北京 100 km WITHDIST WITHCOORD ASC COUNT 5
	 */
	public GeoResults<RedisGeoCommands.GeoLocation<String>> nearByPlace(String key, String member, Distance distance,
																		long count) {
		// includeDistance 包含距离
		// includeCoordinates 包含经纬度
		// sortAscending 正序排序
		// limit 限定返回的记录数
		RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
				.includeDistance().includeCoordinates().sortAscending().limit(count);
		return redisTemplate.opsForGeo().radius(key, member, distance, args);
	}

	/**
	 * 返回的是geohash值
	 *
	 * redis命令：geohash key 北京
	 */
	public List<String> geoHash(String key, String member) {
		return redisTemplate.opsForGeo().hash(key, member);
	}

//	class geoRedisExample{
//			@Autowired
//			private RedisGeoUtil RedisGeoUtil;
//
//			private final String GEO_KEY = "geo_key";
//
//			/**
//			 * 使用redis+GEO，上报司机位置
//			 */
//			@PostMapping("addDriverPosition")
//			public Long addDriverPosition(String cityId, String driverId, Double lng, Double lat) {
//				String redisKey = CommonUtil.buildRedisKey(GEO_KEY, cityId);
//				Long addnum = redisGeoService.geoAdd(redisKey, new Point(lng, lat), driverId);
//
//				List<Point> points = redisGeoService.geoGet(redisKey, driverId);
//				System.out.println("添加位置坐标点：" + points);
//
//				return addnum;
//			}
//
//			/**
//			 * 使用redis+GEO，查询附近司机位置
//			 */
//			@GetMapping("getNearDrivers")
//			public List<DriverPosition> getNearDrivers(String cityId, Double lng, Double lat) {
//				String redisKey = CommonUtil.buildRedisKey(GEO_KEY, cityId);
//
//				Circle circle = new Circle(lng, lat, Metrics.KILOMETERS.getMultiplier());
//				GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisGeoService.nearByXY(redisKey, circle, 5);
//				System.out.println("查询附近司机位置：" + results);
//
//				List<DriverPosition> list = new ArrayList<>();
//				results.forEach(item -> {
//					GeoLocation<String> location = item.getContent();
//					Point point = location.getPoint();
//					DriverPosition position = DriverPosition.builder().cityCode(cityId).driverId(location.getName())
//							.lng(point.getX()).lat(point.getY()).build();
//					list.add(position);
//				});
//
//				return list;
//			}
//
//		}
}
