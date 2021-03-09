package com.editdining.service.repository;

import com.editdining.service.entity.ScrapEntity;
import com.editdining.service.entity.ServicePriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ScrapRepository extends JpaRepository<ScrapEntity, Integer> {

    @Transactional
    int deleteByMemberIdAndServiceId(int memberId, int serviceId);

}
