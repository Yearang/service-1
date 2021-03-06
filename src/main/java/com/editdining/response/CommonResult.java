package com.editdining.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 코드 번호 : 200 성공")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;
}