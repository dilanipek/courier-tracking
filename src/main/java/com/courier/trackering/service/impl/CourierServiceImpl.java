package com.courier.trackering.service.impl;

import com.courier.trackering.domain.entity.CourierLocationEntity;
import com.courier.trackering.repository.ICourierLocationRepository;
import com.courier.trackering.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final ICourierLocationRepository courierLocationRepository;

    @Override
    public Double getTotalTravelDistance(String courierId) {
        List<CourierLocationEntity> locationEntityList =  courierLocationRepository.findAllByCourierId(courierId);
        return locationEntityList.stream()
                .mapToDouble(CourierLocationEntity::getDistance)
                .reduce(Double::sum)
                .orElse(0.0);
    }
}
