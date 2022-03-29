package com.example.demo.src.video.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VideoDetail {

	@Getter
	@AllArgsConstructor
	public static class actorInfoResDto {
		private int actorIdx;
		private String name;
	}

	@Getter
	@AllArgsConstructor
	public static class genreInfoResDto {
		private int genreIdx;
		private String name;
	}

	@Getter
	@AllArgsConstructor
	public static class characterInfoResDto {
		private int characterIdx;
		private String name;
	}

	@Getter
	@AllArgsConstructor
	public static class directorInfoResDto {
		private int directorIdx;
		private String name;
	}
}
