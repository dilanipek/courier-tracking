package com.courier.trackering.service.impl;

import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;
import com.courier.trackering.handler.EntranceHandler;
import com.courier.trackering.handler.ReentryHandler;
import com.courier.trackering.repository.ICourierLocationRepository;
import com.courier.trackering.repository.IStoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class MigrosStoreServiceImplTest {

    @Mock
    private IStoreRepository storeRepository;

    @Mock
    private ICourierLocationRepository courierLocationRepository;

    @Mock
    private ReentryHandler reentryHandler;

    @Mock
    private EntranceHandler entranceHandler;

    @InjectMocks
    private MigrosStoreServiceImpl migrosStoreService;

    @Test
    public void testIsCourierNearMigrosStore_WithoutReentry() {
        CourierRequest courier = new CourierRequest();
        courier.setId("1");
        courier.setTimestamp(Date.from(Instant.now()));
        courier.setLatitude(40.991115);
        courier.setLongitude(29.115744);

        StoreEntity store = new StoreEntity();
        store.setId(1L);
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        store.setName("Ataşehir MMM Migros");

        Mockito.when(storeRepository.findAll()).thenReturn(Collections.singletonList(store));
        Mockito.when(reentryHandler.handle(Mockito.eq(courier), Mockito.eq(store))).thenReturn(false);
        Mockito.when(entranceHandler.handle(Mockito.eq(courier), Mockito.eq(store))).thenReturn(true);

        boolean result = migrosStoreService.isCourierNearMigrosStore(courier);

        Assertions.assertTrue(result);
        Mockito.verify(entranceHandler, Mockito.times(1)).handle(Mockito.eq(courier), Mockito.eq(store));
    }

    @Test
    public void testIsCourierNearMigrosStore_WithReentry() {
        CourierRequest courier = new CourierRequest();
        courier.setId("1");
        courier.setTimestamp(Date.from(Instant.now()));
        courier.setLatitude(40.991115);
        courier.setLongitude(29.115744);

        StoreEntity store = new StoreEntity();
        store.setId(1L);
        store.setLat(40.9923307);
        store.setLng(29.1244229);
        store.setName("Ataşehir MMM Migros");

        Mockito.when(storeRepository.findAll()).thenReturn(Collections.singletonList(store));
        Mockito.when(reentryHandler.handle(Mockito.eq(courier), Mockito.eq(store))).thenReturn(true);

        boolean result = migrosStoreService.isCourierNearMigrosStore(courier);


        Assertions.assertFalse(result);

        Mockito.verify(reentryHandler, Mockito.times(1)).handle(Mockito.eq(courier), Mockito.eq(store));

    }
}
