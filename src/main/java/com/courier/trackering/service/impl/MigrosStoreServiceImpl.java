package com.courier.trackering.service.impl;

import com.courier.trackering.domain.entity.CourierLocationEntity;
import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.domain.request.CourierRequest;
import com.courier.trackering.handler.EntranceHandler;
import com.courier.trackering.handler.ReentryHandler;
import com.courier.trackering.repository.ICourierLocationRepository;
import com.courier.trackering.repository.IStoreRepository;
import com.courier.trackering.service.IMigrosStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MigrosStoreServiceImpl implements IMigrosStoreService {

    public static final double STORE_DISTANCE = 100.0;
    private final IStoreRepository storeRepository;
    private final ICourierLocationRepository courierLocationRepository;

    private final ReentryHandler reentryHandler;

    private final EntranceHandler entranceHandler;

    @Override
    public boolean isCourierNearMigrosStore(CourierRequest courier) {
        Iterable<StoreEntity> migrosStores = getMigrosStores();

        for (StoreEntity store : migrosStores) {
            double distance = calculateDistance(courier.getLatitude(), courier.getLongitude(), store.getLat(), store.getLng());
            saveCourierTravelDistanceToStore(courier,distance);
            if (distance <= STORE_DISTANCE) {
                if (reentryHandler.handle(courier, store)) {
                    return false; // Reentry detected, stop processing
                }
                // Continue processing if reentry not detected
                return entranceHandler.handle(courier, store);
            }
        }

        return false;
    }

    private void saveCourierTravelDistanceToStore(CourierRequest courier, double distance ) {
        CourierLocationEntity locationEntity = new CourierLocationEntity();
        locationEntity.setCourierId(courier.getId());
        locationEntity.setLongitude(courier.getLongitude());
        locationEntity.setLatitude(courier.getLatitude());
        locationEntity.setTimestamp(courier.getTimestamp());
        locationEntity.setDistance(distance);
        courierLocationRepository.save(locationEntity);
    }



    private Iterable<StoreEntity> getMigrosStores() {
        return  storeRepository.findAll();
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371.0; // in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
}