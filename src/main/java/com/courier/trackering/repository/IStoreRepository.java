package com.courier.trackering.repository;

import com.courier.trackering.domain.entity.StoreEntity;
import org.springframework.data.repository.CrudRepository;

public interface IStoreRepository extends CrudRepository<StoreEntity,Long> {
}
