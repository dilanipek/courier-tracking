package com.courier.trackering.handler;

import com.courier.trackering.domain.entity.CourierEntryLogEntity;
import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;
import com.courier.trackering.repository.ICourierEntryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntranceHandler implements CourierHandler {
  private final ICourierEntryLogRepository courierEntryLogRepository;

  @Override
  public boolean handle(CourierRequest courier, StoreEntity store) {
    saveCourierEntranceLessThan100Meters(courier, store);
    return true; // Request handled
  }

  private void saveCourierEntranceLessThan100Meters(CourierRequest courier, StoreEntity store) {
    CourierEntryLogEntity courierEntryEntity = new CourierEntryLogEntity();
    courierEntryEntity.setStore(store);
    courierEntryEntity.setEntryTimestamp(courier.getTimestamp());
    courierEntryEntity.setCourierId(courier.getId());
    courierEntryLogRepository.save(courierEntryEntity);
  }

}