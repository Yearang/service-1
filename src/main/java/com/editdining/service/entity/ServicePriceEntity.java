package com.editdining.service.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tbl_service_price")
public class ServicePriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int price_id;
    private int service_id;
    private int price;
    private int period;
    private int option1;
    private int option2;
    private String description;
}
