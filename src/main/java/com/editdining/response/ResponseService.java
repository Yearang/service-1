package com.editdining.response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service // 해당 Class가 Service임을 명시합니다.
@Slf4j // 로깅을 위한 어노테이션
@Component // 빈 등록을 위한 어노테이션
@RequiredArgsConstructor
public class ResponseService {

	// enum으로 api 요청 결과에 대한 code, message를 정의합니다.
	public enum CommonResponse {
		SUCCESS(200, "SUCCESS"), FAIL(100, "FAIL");


		int code;
		String msg;

		CommonResponse(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}

	// 단일건 결과를 처리하는 메소드
	public <T> SingleResult<T> getSingleResult(T data) {
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		setSuccessResult(result);
		return result;
	}

	// 다중건 결과를 처리하는 메소드
	public <T> ListResult<T> getListResult(List<T> list) {
		ListResult<T> result = new ListResult<>();
		result.setList(list);
		setSuccessResult(result);
		return result;
	}

	public <T> ListResult<T> getListResult(long size, List<T> list) {
		ListResult<T> result = new ListResult<>();
		result.setTotal_count(size);
		result.setList(list);
		setSuccessResult(result);
		return result;
	}

	// 성공 결과만 처리하는 메소드
	public CommonResult getSuccessResult() {
		CommonResult result = new CommonResult();
		setSuccessResult(result);
		return result;
	}

	// 실패 결과만 처리하는 메소드
	public CommonResult getFailResult() {
		CommonResult result = new CommonResult();
		setFailResult(result);
		return result;
	}
	
	// 실패 코드, 메세지 받아서 처리하는 메소
	public CommonResult getFailResult(int code, String message) {
		CommonResult result = new CommonResult();
		result.setCode(code);
		result.setMsg(message);
		return result;
	}
	public<T> SingleResult<T> getFailResult(T data) {
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		setFailResult(result);
		return result;
	}


	// 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
	private void setSuccessResult(CommonResult result) {
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMsg(CommonResponse.SUCCESS.getMsg());
	}
	
	// 실패 결과만 처리하는 메소드
	public void setFailResult(CommonResult result) {
		result.setCode(CommonResponse.FAIL.getCode());
		result.setMsg(CommonResponse.FAIL.getMsg());
	}
}