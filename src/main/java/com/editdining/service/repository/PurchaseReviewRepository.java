package com.editdining.service.repository;

import com.editdining.service.entity.PurchaseReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseReviewRepository extends JpaRepository<PurchaseReviewEntity, Integer> {
    List<PurchaseReviewEntity> findByServiceId(int service_id);
}
