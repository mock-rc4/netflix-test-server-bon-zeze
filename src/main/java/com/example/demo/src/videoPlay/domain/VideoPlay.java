package com.example.demo.src.videoPlay.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VideoPlay {
	private int playIdx;
	private int profileIdx;
	private int videosIdx;
	private float currentPlayTime;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class createDto {
		private int profileIdx;
		private int videosIdx;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class checkHasDuplicatedVideoPlayReqDto {
		private int profileIdx;
		private int videosIdx;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class createReqDto {
		private float currentPlayTime;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class createResDto {
		private int playIdx;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class modifyReqDto {
		private float currentPlayTime;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class modifyDto {
		private int profileIdx;
		private int videosIdx;
		private float currentPlayTime;
	}

}