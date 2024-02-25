package com.courier.trackering.controller;

import com.courier.trackering.domain.request.CourierRequest;
import com.courier.trackering.service.impl.CourierServiceImpl;
import com.courier.trackering.service.impl.MigrosStoreServiceImpl;
import com.courier.trackering.service.impl.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/api/couriers")
@RequiredArgsConstructor
public class CourierController {

    public static final String STORES_JSON = "stores.json";
    private final CourierServiceImpl courierService;
    private final MigrosStoreServiceImpl migrosStoreService;
    private final StoreService storeService;

    @PostMapping("/location")
    public ResponseEntity<?> logCourierLocation(@RequestBody CourierRequest courier) {
        if (migrosStoreService.isCourierNearMigrosStore(courier)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{courierId}/total-travel-distance")
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable String courierId) {
        Double totalDistance = courierService.getTotalTravelDistance(courierId);
        return ResponseEntity.ok(totalDistance);
    }

    @PostMapping("/load-store")
    public ResponseEntity<String> saveStoresToDb() {
        try {
            storeService.saveStoresFromJson(STORES_JSON);
            return ResponseEntity.ok("Stores saved successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while saving stores to the database.");
        }
    }

}