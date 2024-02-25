package com.courier.trackering.repository;

import com.courier.trackering.domain.entity.CourierEntryLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface ICourierEntryLogRepository extends CrudRepository<CourierEntryLogEntity,Long> {
}
