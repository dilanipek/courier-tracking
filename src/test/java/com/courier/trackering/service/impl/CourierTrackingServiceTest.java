package com.courier.trackering.service.impl;

import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CourierTrackingServiceTest {

    @InjectMocks
    private CourierTrackingService courierTrackingService;

    @Test
    void testIsReentryWithinTimeThreshold() {
        CourierRequest courier = new CourierRequest();
        courier.setId("1");
        courier.setTimestamp(Date.from(Instant.now()));
        courier.setLatitude(40.991115);
        courier.setLongitude(29.115744);

        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setId(1L);
        storeEntity.setName("Ataşehir MMM Migros");
        storeEntity.setLat(40.9923307);
        storeEntity.setLng(29.1244229);

        boolean result = courierTrackingService.isReentryWithinTimeThreshold(courier, storeEntity);

        assertFalse(result);
    }

    @Test
    public void testLogCourierEntrance() {
        CourierRequest courier = new CourierRequest();
        courier.setId("1");
        courier.setTimestamp(Date.from(Instant.now()));
        courier.setLatitude(40.991115);
        courier.setLongitude(29.115744);

        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setId(1L);
        storeEntity.setName("Ataşehir MMM Migros");
        storeEntity.setLat(40.9923307);
        storeEntity.setLng(29.1244229);

        ConcurrentHashMap<String, Object> concurrentHap = new ConcurrentHashMap<>();
        TestUtils.setFieldValue(courierTrackingService, "concurrentHap", concurrentHap);

        courierTrackingService.logCourierEntrance(courier, storeEntity);

        String key = courier.getId() + "_" + storeEntity.getId();
        assertTrue(concurrentHap.containsKey(key));

        Object timestamp = concurrentHap.get(key);
        assertNotNull(timestamp);
        assertTrue(timestamp instanceof Long);
    }

    private static class TestUtils {
        static void setFieldValue(Object obj, String fieldName, Object value) {
            try {
                java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
