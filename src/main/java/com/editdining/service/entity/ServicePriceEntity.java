package com.editdining.service.entity;

import com.editdining.service.dto.Price;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tbl_service_price")
@NoArgsConstructor
@AllArgsConstructor
public class ServicePriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceId;
    private int serviceId;
    private int price;
    private int period;
    private int option1;
    private int option2;
    private String description;

}
