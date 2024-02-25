package com.courier.trackering.service.impl;

import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierTrackingServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @InjectMocks
    private CourierTrackingService courierTrackingService;

    @Test
    void testIsReentryWithinTimeThreshold() {
        CourierRequest courier = new CourierRequest();
        courier.setId("1");
        courier.setTimestamp(Date.from(Instant.now()));
        courier.setLatitude(40.991115);
        courier.setLongitude(29.115744);

        StoreEntity store = new StoreEntity();
        store.setId(1L);
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        store.setName("Ataşehir MMM Migros");

        String key = courier.getId() + "_" + store.getId();
        Long lastEntranceTime = Instant.now().toEpochMilli() - 30000L;

        ValueOperations<String, Object> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(lastEntranceTime);

        boolean result = courierTrackingService.isReentryWithinTimeThreshold(courier, store);

        assertTrue(result);

        verify(redisTemplate.opsForValue(), times(1)).get(key);
    }

    @Test
    void testLogCourierEntrance() {
        CourierRequest courier = new CourierRequest();
        courier.setId("1");
        courier.setTimestamp(Date.from(Instant.now()));
        courier.setLatitude(40.991115);
        courier.setLongitude(29.115744);

        StoreEntity store = new StoreEntity();
        store.setId(1L);
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        store.setName("Ataşehir MMM Migros");


        String key = courier.getId() + "_" + store.getId();

        ValueOperations<String, Object> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(null);

        courierTrackingService.logCourierEntrance(courier, store);

        verify(redisTemplate.opsForValue()).get(key);

        verify(redisTemplate.opsForValue()).set(eq(key), anyLong(), eq(1L), eq(TimeUnit.MINUTES));
    }


}
