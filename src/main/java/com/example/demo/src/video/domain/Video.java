package com.example.demo.src.video.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Video {
	private int videoIdx;
	private String title;
	private int ageGrade;
	private String runningTime;
	private String photoUrl;
	private int year;
	private int season;
	private String summary;
	private String resolution;
	private String previewUrl;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class getVideoResDto {
		private int videoIdx;
		private int year;
		private int season;
		private int ageGrade;
		private String title;
		private String runningTime;
		private String photoUrl;
		private String summary;
		private String resolution;
		private String previewUrl;
	}

	@Getter
	@AllArgsConstructor
	public static class getEachSeasonEpisodeCountsResDto {
		private int season;
		private int episodeCount;
	}

}