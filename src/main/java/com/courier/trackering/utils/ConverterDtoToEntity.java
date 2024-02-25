package com.courier.trackering.utils;

import com.courier.trackering.domain.Store;
import com.courier.trackering.domain.entity.StoreEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ConverterDtoToEntity {
    private static final ConverterDtoToEntity instance = new ConverterDtoToEntity();

    public static ConverterDtoToEntity getInstance() {
        return instance;
    }

    public List<StoreEntity> convertStoreToEntityList(List<Store> stores) {
        return stores == null ? null : stores.stream()
            .map(this::convertToEntity)
            .collect(Collectors.toList());
    }

    private StoreEntity convertToEntity(Store store) {
        StoreEntity entity = new StoreEntity();
        entity.setId(store.getId());
        entity.setName(store.getName());
        entity.setLat(store.getLat());
        entity.setLng(store.getLng());
        return entity;
    }
}