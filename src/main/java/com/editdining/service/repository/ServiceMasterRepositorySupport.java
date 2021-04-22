package com.editdining.service.repository;

import com.editdining.service.dto.ServiceDto;
import com.editdining.service.entity.ScrapEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.editdining.service.entity.QServiceMasterEntity.serviceMasterEntity;
import static com.editdining.service.entity.QServicePriceEntity.servicePriceEntity;
import static com.editdining.service.entity.QMemberEntity.memberEntity;
import static com.editdining.service.entity.QScrapEntity.scrapEntity;
import static com.editdining.service.entity.QPurchaseReviewEntity.purchaseReviewEntity;

@Repository
@RequiredArgsConstructor
public class ServiceMasterRepositorySupport {
    private final JPAQueryFactory queryFactory;


    /*
    * 2-2. 서비스 리스트
    * -> join이 필요해 querydsl로
    * */
    public List<ServiceDto.Response> findByCategory(int member_id, int category, Integer edit_type, int offset, int limit){

        return queryFactory
                .select(Projections.fields(ServiceDto.Response.class,
                        serviceMasterEntity.service_id,
                        serviceMasterEntity.edit_type,
                        serviceMasterEntity.thumbnail,
                        serviceMasterEntity.category,
                        serviceMasterEntity.title,
                        serviceMasterEntity.description,
                        servicePriceEntity.price,
                        memberEntity.name,
                        scrapEntity.scrapId.as("is_scrap"),
                        purchaseReviewEntity.rate.avg().as("rate")))
                .from(serviceMasterEntity)
                // 가격
                .join(servicePriceEntity)
                    .on(servicePriceEntity.priceId
                            .eq(JPAExpressions.select(servicePriceEntity.priceId.min())
                                                .from(servicePriceEntity)
                                                .where(serviceMasterEntity.service_id.eq(servicePriceEntity.serviceId))))
                // 회원
                .join(memberEntity)
                    .on(memberEntity.member_id.eq(serviceMasterEntity.member_id))
                // 리뷰
                .leftJoin(purchaseReviewEntity)
                    .on(purchaseReviewEntity.serviceId.eq(serviceMasterEntity.service_id))
                .leftJoin(scrapEntity)
                    .on(scrapEntity.memberId.eq(serviceMasterEntity.member_id)
                            .and(scrapEntity.serviceId.eq(serviceMasterEntity.service_id))
                            .and(scrapEntity.memberId.eq(member_id)))
                .where(serviceMasterEntity.category.eq(category), eqEditType(edit_type))
                .groupBy(serviceMasterEntity.service_id)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public long findByCategoryTotal(int member_id, int category, Integer edit_type){

        return queryFactory
                .selectFrom(serviceMasterEntity)
                // 가격
                .join(servicePriceEntity)
                .on(servicePriceEntity.priceId
                        .eq(JPAExpressions.select(servicePriceEntity.priceId.min())
                                .from(servicePriceEntity)
                                .where(serviceMasterEntity.service_id.eq(servicePriceEntity.serviceId))))
                // 회원
                .join(memberEntity)
                .on(memberEntity.member_id.eq(serviceMasterEntity.member_id))
                .leftJoin(scrapEntity)
                .on(scrapEntity.memberId.eq(serviceMasterEntity.member_id)
                        .and(scrapEntity.serviceId.eq(serviceMasterEntity.service_id))
                        .and(scrapEntity.memberId.eq(member_id)))
                .where(serviceMasterEntity.category.eq(category), eqEditType(edit_type))
                .groupBy(serviceMasterEntity.service_id)
                .fetchCount();
    }

    // null 처리
    private BooleanExpression eqEditType(Integer edit_type) {
        if (edit_type == null) {
            return null;
        }
        return serviceMasterEntity.edit_type.eq(edit_type);
    }

    public ServiceDto.DetailResponse getServiceDetail(int service_id, int member_id) {
        return queryFactory
                .select(Projections.fields(ServiceDto.DetailResponse.class,
                        serviceMasterEntity.service_id,
                        serviceMasterEntity.category,
                        serviceMasterEntity.title,
                        serviceMasterEntity.edit_type,
                        serviceMasterEntity.thumbnail,
                        serviceMasterEntity.description,
                        memberEntity.name,
                        scrapEntity.scrapId.as("is_scrap"),
                        purchaseReviewEntity.rate.avg().as("rate")))
                .from(serviceMasterEntity)
                // 회원
                .join(memberEntity)
                .on(memberEntity.member_id.eq(serviceMasterEntity.member_id))
                // 리뷰
                .leftJoin(purchaseReviewEntity)
                .on(purchaseReviewEntity.serviceId.eq(serviceMasterEntity.service_id))
                // 스크랩
                .leftJoin(scrapEntity)
                .on(scrapEntity.memberId.eq(serviceMasterEntity.member_id)
                        .and(scrapEntity.serviceId.eq(serviceMasterEntity.service_id))
                        .and(scrapEntity.memberId.eq(member_id)))
                .where(serviceMasterEntity.service_id.eq(service_id))
                .fetchOne();

    }

}
