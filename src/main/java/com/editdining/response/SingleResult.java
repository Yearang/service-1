package com.editdining.response;

import lombok.Getter;
import lombok.Setter;


public class SingleResult<T> extends CommonResult {
	
	@Getter
	@Setter
    private T data;
}