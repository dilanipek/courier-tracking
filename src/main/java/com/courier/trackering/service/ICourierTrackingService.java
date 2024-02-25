package com.courier.trackering.service;

import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;

public interface ICourierTrackingService {
  boolean isReentryWithinTimeThreshold(CourierRequest courier, StoreEntity storeEntity);

  void logCourierEntrance(CourierRequest courier, StoreEntity storeEntity);
}


