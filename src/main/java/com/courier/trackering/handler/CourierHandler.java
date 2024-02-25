package com.courier.trackering.handler;

import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;

public interface CourierHandler {
  boolean handle(CourierRequest courier, StoreEntity store);
}