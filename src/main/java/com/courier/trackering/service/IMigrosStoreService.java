package com.courier.trackering.service;


import com.courier.trackering.domain.request.CourierRequest;

public interface IMigrosStoreService {
     boolean isCourierNearMigrosStore(CourierRequest courier);
}
