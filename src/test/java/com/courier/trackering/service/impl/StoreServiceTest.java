package com.courier.trackering.service.impl;

import com.courier.trackering.domain.Store;
import com.courier.trackering.repository.IStoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StoreServiceTest {

    private StoreService storeService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private IStoreRepository storeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        storeService = new StoreService(objectMapper, storeRepository);
    }

    @Test
    public void testSaveStoresFromJson() throws IOException {
        String filePath = "test.json";

        Store[] storesArray = {new Store(), new Store()};

        when(objectMapper.readValue(any(File.class), any(Class.class))).thenReturn(storesArray);

        storeService.saveStoresFromJson(filePath);

        verify(objectMapper).readValue(any(File.class), any(Class.class));

        verify(storeRepository).saveAll(any(List.class));
    }
}