package com.courier.trackering.service.impl;

import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;
import com.courier.trackering.service.ICourierTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CourierTrackingService implements ICourierTrackingService {

  private final Map<String, Object> concurrentHap = new ConcurrentHashMap<>();

  @Override
  public boolean isReentryWithinTimeThreshold(CourierRequest courier, StoreEntity store) {
    String key = courier.getId() + "_" + store.getId();
    Long lastEntranceTime = (Long) concurrentHap.get(key);

    if (lastEntranceTime != null) {
      long currentTimestamp = getCurrentTimestampInIstanbul();

      return currentTimestamp - lastEntranceTime < 60000; // Reentry
    } else {
      logCourierEntrance(courier, store);
    }
    return false;
  }


  @Override
  public void logCourierEntrance(CourierRequest courier, StoreEntity storeEntity) {
    String key = courier.getId() + "_" + storeEntity.getId();

    // Only update the timestamp if it's the first entrance or a reentry has occurred
    if (concurrentHap.get(key) == null) {
      long currentTimestamp = getCurrentTimestampInIstanbul();
      concurrentHap.put(key, currentTimestamp);
    }
  }

  private long getCurrentTimestampInIstanbul() {
    return Instant.now().atZone(ZoneId.of("Europe/Istanbul")).toInstant().toEpochMilli();

  }
}



