package com.courier.trackering.repository;

import com.courier.trackering.domain.entity.CourierLocationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICourierLocationRepository extends CrudRepository<CourierLocationEntity,Long> {

    List<CourierLocationEntity> findAllByCourierId(String courierId);
}
