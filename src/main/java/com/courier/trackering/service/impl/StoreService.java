package com.courier.trackering.service.impl;

import com.courier.trackering.domain.Store;
import com.courier.trackering.domain.entity.StoreEntity;
import com.courier.trackering.repository.IStoreRepository;
import com.courier.trackering.utils.ConverterDtoToEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final ObjectMapper objectMapper;
    private final IStoreRepository storeRepository;

    public void saveStoresFromJson(String filePath) throws IOException {
        File file = new File(filePath);
        Store[] storesArray = objectMapper.readValue(file, Store[].class);
        if(storesArray!= null) {
            List<Store> stores = Arrays.asList(storesArray);
            List<StoreEntity> storeEntityList = ConverterDtoToEntity.getInstance().convertStoreToEntityList(stores);
            storeRepository.saveAll(storeEntityList);
        }
    }
}
