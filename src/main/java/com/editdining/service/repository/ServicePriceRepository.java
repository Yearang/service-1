package com.editdining.service.repository;

import com.editdining.service.entity.ServicePriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicePriceRepository extends JpaRepository<ServicePriceEntity, Integer> {
    List<ServicePriceEntity> findByServiceId(int service_id);
}
