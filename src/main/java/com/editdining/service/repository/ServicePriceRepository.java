package com.editdining.service.repository;

import com.editdining.service.entity.ServicePriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePriceRepository extends JpaRepository<ServicePriceEntity, Integer> {
}
