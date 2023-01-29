package com.practice.bom;

import com.alibaba.fastjson.JSON;
import com.practice.bom.constants.GlobalConstants;
import com.practice.bom.util.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ljf
 * @description
 * @date 2023/1/5 2:36 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Resource
    private RedisService redisService;

    @Before
    public void before() {
        redisService.addGeo(GlobalConstants.GEO_KEY, "北京", 116.41667d, 39.91667d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "上海", 121.43333d, 34.50000d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "天津", 117.20000d, 39.13333d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "香港", 114.10000d, 22.20000d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "广州", 113.23333d, 23.16667d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "珠海", 113.51667d, 22.30000d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "深圳", 114.06667d, 22.61667d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "杭州", 120.20000d, 30.26667d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "澳门", 113.50000d, 22.20000d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "郑州", 113.65000d, 34.76667d);
        redisService.addGeo(GlobalConstants.GEO_KEY, "南京", 118.78333d, 32.05000d);
    }

    @Test
    public void testGeo() {
        StopWatch stopWatch = new StopWatch("test");
        Point pointList = redisService.getGeo(GlobalConstants.GEO_KEY, "广 州");
        System.out.println(JSON.toJSONString(pointList));

        stopWatch.start("between");
        double distance = redisService.distanceGeoBetween(GlobalConstants.GEO_KEY, "梧州", "香港", Metrics.KILOMETERS);
        stopWatch.stop();
        System.out.println("距离：" + distance);
        System.out.println(stopWatch.getLastTaskInfo().getTimeMillis());

        stopWatch.start("111");
        Map<String, Double> map = redisService.distanceGeoInclude(GlobalConstants.GEO_KEY, "北京", 500d, Metrics.KILOMETERS);
        stopWatch.stop();
        Assert.assertNotNull(map);
        System.out.println(map);
        System.out.println(stopWatch.getLastTaskInfo().getTimeMillis());

        stopWatch.start("222");
        Map<String, Double> distanceMap = redisService.distanceGeoInclude(GlobalConstants.GEO_KEY, "广州", new Distance(500d, Metrics.KILOMETERS), 10);
        stopWatch.stop();
        System.out.println(distanceMap);
        System.out.println(stopWatch.getLastTaskInfo().getTimeMillis());

    }

}
