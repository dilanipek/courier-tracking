package com.courier.trackering.service.impl;

import com.courier.trackering.domain.entity.CourierLocationEntity;
import com.courier.trackering.repository.ICourierLocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CourierServiceImplTest {

    @InjectMocks
    private CourierServiceImpl courierService;

    @Mock
    private ICourierLocationRepository courierLocationRepository;


    @Test
    void testGetTotalTravelDistance() {
        String courierId = "2";
        Long locationId = 1L;
        Double latitude = 40.991115;
        Double longitude = 29.115744;
        Date today = Date.from(Instant.now());
        List<CourierLocationEntity> locations = Arrays.asList(
                new CourierLocationEntity(locationId, courierId, today, latitude, longitude, 0.7408607082953231),
                new CourierLocationEntity(locationId, courierId, today, latitude, longitude, 0.7408607082953231)
        );

        when(courierLocationRepository.findAllByCourierId(courierId)).thenReturn(locations);

        Double totalTravelDistance = courierService.getTotalTravelDistance(courierId);

        assertEquals(1.4817214165906463, totalTravelDistance, 1e-6);

        verify(courierLocationRepository).findAllByCourierId(courierId);
    }


}