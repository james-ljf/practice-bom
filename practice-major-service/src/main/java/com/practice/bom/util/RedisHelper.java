package com.practice.bom.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ljf
 * @description redis工具组件
 * @date 2022/12/30 4:06 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisHelper {

    private final Logger logger = LoggerFactory.getLogger(RedisHelper.class);

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key     键
     * @param timeout 时间(秒)
     */
    public void expire(String key, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */

    public long getExpire(String key) {
        return Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(-1L);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return boolean
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 删除key
     *
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 获取
     *
     * @param key key
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 存放
     *
     * @param key   key
     * @param value 值
     */
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 带缓存有效时限的存放
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间，单位 秒
     */
    public boolean set(String key, Object value, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return Optional.ofNullable(redisTemplate.opsForValue().increment(key, delta)).orElse(-1L);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return Optional.ofNullable(redisTemplate.opsForValue().increment(key, -delta)).orElse(-1L);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object getHash(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean setHash(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean setHash(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean setHash(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean setHash(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void delHash(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hasHashKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public double hashIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public double hashDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public Set<Object> getSet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Collections.emptySet();
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean hasSetKey(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setSet(String key, Object... values) {
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().add(key, values)).orElse(0L);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setSetExpire(String key, long time, Object... values) {
        try {
            long count = Optional.ofNullable(redisTemplate.opsForSet().add(key, values)).orElse(0L);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public long getSetSize(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().size(key)).orElse(0L);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long removeSet(String key, Object... values) {
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().remove(key, values)).orElse(0L);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public long lGetListSize(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForList().size(key)).orElse(0L);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            return Optional.ofNullable(redisTemplate.opsForList().remove(key, count, value)).orElse(0L);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    //--------------- GEO ----------------


    /**
     * 添加经纬度信息
     * 如：key, "广州", 116.405285d, 39.904989d
     *
     * @param key       键
     * @param certId    标识
     * @param longitude 经度
     * @param latitude  纬度
     */
    public void addGeo(String key, String certId, double longitude, double latitude) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForGeo().remove(key, certId);
        }
        Point point = new Point(longitude, latitude);
        redisTemplate.opsForGeo().add(key, point, certId);
    }

    /**
     * 添加经纬度信息
     * 如：key, "北京", point{x:116.405285d, y:39.904989d}
     *
     * @param key    key
     * @param certId 标识
     * @param point  坐标
     */
    public void addGeo(String key, String certId, Point point) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForGeo().remove(key, certId);
        }
        redisTemplate.opsForGeo().add(key, point, certId);
    }

    /**
     * 查找经纬度信息
     * 注：返回后注意判断空值
     *
     * @param key    键
     * @param certId 标识
     * @return 坐标列表
     */
    public Point getGeo(String key, String certId) {
        List<Point> pointList = getGeoBatch(key, certId);
        return pointList.get(0);
    }

    /**
     * 查找经纬度信息，可以指定多个标识，批量返回
     *
     * @param key     键
     * @param certIds 标识列表
     * @return 坐标列表
     */
    public List<Point> getGeoBatch(String key, String... certIds) {
        return redisTemplate.opsForGeo().position(key, certIds);
    }

    /**
     * 获取两个位置之间的距离信息，默认单位：米
     *
     * @param key       键
     * @param certIdOne 标识1
     * @param certIdTwo 标识2
     * @return 距离信息
     */
    public Distance getGeoBetween(String key, String certIdOne, String certIdTwo) {
        return getGeoBetween(key, certIdOne, certIdTwo, Metrics.MILES);
    }

    /**
     * 获取两个位置之间的距离信息
     *
     * @param key       键
     * @param certIdOne 标识1
     * @param certIdTwo 标识2
     * @param metric    距离单位
     * @return 距离信息
     */
    public Distance getGeoBetween(String key, String certIdOne, String certIdTwo, Metric metric) {
        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        return geoOperations.distance(key, certIdOne, certIdTwo, metric);
    }

    /**
     * 返回两个位置的距离
     * 默认：米
     *
     * @param key       键
     * @param certIdOne 标识1
     * @param certIdTwo 标识2
     * @return 距离
     */
    public double distanceGeoBetween(String key, String certIdOne, String certIdTwo) {
        return distanceGeoBetween(key, certIdOne, certIdTwo, Metrics.MILES);
    }

    /**
     * 返回两个位置的距离
     *
     * @param key       键
     * @param certIdOne 标识1
     * @param certIdTwo 标识2
     * @param metric    距离单位
     * @return 距离
     */
    public double distanceGeoBetween(String key, String certIdOne, String certIdTwo, Metric metric) {
        Distance distance = getGeoBetween(key, certIdOne, certIdTwo, metric);
        if (distance == null) {
            return 0d;
        }
        return convertDistance(distance.getValue());
    }

    /**
     * 查询距离某个标识指定范围内的数据
     * 默认单位： 米
     *
     * @param key      键
     * @param certId   标识
     * @param distance 距离
     * @return {名称，距离}
     */
    public Map<String, Double> distanceGeoInclude(String key, String certId, double distance) {
        return distanceGeoInclude(key, certId, distance, Metrics.MILES);
    }

    /**
     * 查询距离某个标识指定范围内的数据
     *
     * @param key      键
     * @param certId   标识
     * @param distance 距离
     * @param metric   距离单位
     * @return map{名称，距离}
     */
    public Map<String, Double> distanceGeoInclude(String key, String certId, double distance, Metric metric) {
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending();
        Distance distanceObj = new Distance(distance, metric);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo().radius(key, certId, distanceObj, args);
        return accessGeoResults(geoResults, certId);
    }

    /**
     * 查询距离某个标识指定范围内的数据
     *
     * @param key      key
     * @param certId   标识
     * @param distance 距离信息
     * @param count    限定返回的记录数
     * @return map{名称，距离}
     */
    public Map<String, Double> distanceGeoInclude(String key, String certId, Distance distance, long count) {
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending().limit(count);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo().radius(key, certId, distance, args);
        return accessGeoResults(geoResults, certId);
    }

    /**
     * 处理地理位置信息结果集
     *
     * @param geoResults 地理位置信息结果集
     * @param certId     当前查询条件标识
     * @return map{名称，距离}
     */
    private Map<String, Double> accessGeoResults(GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults, String certId) {
        Map<String, Double> map = new LinkedHashMap<>();
        if (geoResults != null) {
            for (GeoResult<RedisGeoCommands.GeoLocation<Object>> geoResult : geoResults) {
                // 该点的信息
                RedisGeoCommands.GeoLocation<Object> geoResultContent = geoResult.getContent();
                // 剔除查询标识本身
                if (StringUtils.equals((CharSequence) geoResultContent.getName(), certId)) {
                    continue;
                }
                // 与目标点相距的距离信息
                Distance geoResultDistance = geoResult.getDistance();
                double distance = convertDistance(geoResultDistance.getValue());
                map.put(String.valueOf(geoResultContent.getName()), distance);
            }
        }
        return map;
    }

    /**
     * 将距离设置为保留一位小数
     *
     * @param distance 距离
     * @return double
     */
    private double convertDistance(double distance) {
        return BigDecimal.valueOf(distance).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

}
