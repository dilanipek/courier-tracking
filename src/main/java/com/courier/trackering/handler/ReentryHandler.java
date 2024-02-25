package com.courier.trackering.handler;

import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;
import com.courier.trackering.service.impl.CourierTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReentryHandler implements CourierHandler {

  private  CourierTrackingService courierTrackingService;

  @Autowired
  public ReentryHandler(CourierTrackingService courierTrackingService) {
    this.courierTrackingService = courierTrackingService;
  }

  @Override
  public boolean handle(CourierRequest courier, StoreEntity store) {
    return courierTrackingService.isReentryWithinTimeThreshold(courier, store); // Reentry detected, request handled
  }
}