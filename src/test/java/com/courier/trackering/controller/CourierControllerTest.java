package com.courier.trackering.controller;

import com.courier.trackering.domain.request.CourierRequest;
import com.courier.trackering.service.impl.MigrosStoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CourierControllerTest {

    @InjectMocks
    private CourierController courierController;

    @Mock(lenient = true)
    private MigrosStoreServiceImpl migrosStoreService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogCourierLocation_CourierNearMigrosStore() {
        CourierRequest courier = new CourierRequest();
        courier.setId("1");
        courier.setLatitude(40.991115);
        courier.setLongitude(29.115744);
        courier.setTimestamp(Date.from(Instant.now()));
        ResponseEntity<?> response = courierController.logCourierLocation(courier);
        when(migrosStoreService.isCourierNearMigrosStore(courier)).thenReturn(true);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testLogCourierLocation_CourierNotNearMigrosStore() {
        CourierRequest courier = new CourierRequest();
        courier.setId("1");
        courier.setLatitude(40.991120);
        courier.setLongitude(29.115744);
        ResponseEntity<?> response = courierController.logCourierLocation(courier);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}
