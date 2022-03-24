package com.example.demo.src.assessment.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Assessment {
	private int assessmentIdx;
	private int status;
	private int profileIdx;
	private int videoIdx;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class createOrModifyDto {
		private int profileIdx;
		private int videoIdx;
		private int status;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class createReqDto {
		private int status;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class createResDto {
		private int assessmentIdx;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class modifyReqDto {
		private int status;
	}
}
