package com.editdining.service.repository;

import com.editdining.service.entity.ServiceMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceMediaRepository extends JpaRepository<ServiceMediaEntity, Integer> {
    List<ServiceMediaEntity> findByServiceId(int service_id);
}
