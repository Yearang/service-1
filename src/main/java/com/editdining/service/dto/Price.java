package com.editdining.service.dto;


import com.editdining.service.entity.ServicePriceEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Price {
    @ApiModelProperty(name = "price", value = "가격", example = "1000", required = true)
    @NotNull
    private int price;
    @ApiModelProperty(name = "period", value = "작업기간", example = "10", required = true)
    @NotNull
    private int period;
    @ApiModelProperty(name = "option1", value = "옵션1", example = "1", required = true)
    @NotNull
    private int option1;
    @ApiModelProperty(name = "option2", value = "옵션2", example = "1")
    private int option2;
    @ApiModelProperty(name = "description", value = "옵션 설명", example = "옵션 설명 100자 이내")
    private String description;
    private int service_id;

    public ServicePriceEntity toEntity(){
        return ServicePriceEntity.builder()
                .price(price)
                .period(period)
                .option1(option1)
                .option2(option2)
                .description(description)
                .serviceId(service_id)
                .build();
    }
}
