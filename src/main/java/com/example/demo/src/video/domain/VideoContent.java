package com.example.demo.src.video.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VideoContent {

	@Getter
	@Setter
	@AllArgsConstructor
	public static class resDto {
		private String title;
		private String runningTime;
		private String summary;
		private int season;
		private int episode;
	}
}
