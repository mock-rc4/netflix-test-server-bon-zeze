package com.example.demo.src.videoPlay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.src.video.domain.Video;
import com.example.demo.src.videoPlay.domain.VideoPlay;

@Repository
public class VideoPlayDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public VideoPlayDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int createVideoPlay(VideoPlay.createDto requestDto) {
		String query = "insert into Play (profileIdx, videosIdx) VALUES (?,?)";
		Object[] params = new Object[] {requestDto.getProfileIdx(), requestDto.getVideosIdx()};
		this.jdbcTemplate.update(query, params);

		String lastInsertedIdQuery = "select max(playIdx) from Play";
		return this.jdbcTemplate.queryForObject(lastInsertedIdQuery, int.class);
	}

	public int checkHasVideoPlay(VideoPlay.checkHasDuplicatedVideoPlayReqDto requestDto) {
		int profileIdx = requestDto.getProfileIdx();
		int videosIdx = requestDto.getVideosIdx();
		String query = "select exists(select playIdx from Play where profileIdx = ? and videosIdx = ?)";
		return this.jdbcTemplate.queryForObject(query, Integer.class, profileIdx, videosIdx);
	}

	public float getVideoPlayStatus(int profileIdx, int videosIdx) {
		String query = "select currentPlayTime from Play where profileIdx = ? and videosIdx = ?";
		return this.jdbcTemplate.queryForObject(query,
			float.class,
			profileIdx, videosIdx);
	}

	public void modifyVideoPlay(VideoPlay.modifyDto requestDto) {
		String query = "update Play set currentPlayTime = ?, updatedAt = NOW() where profileIdx = ? and videosIdx = ?";
		Object[] params = new Object[] {requestDto.getCurrentPlayTime(), requestDto.getProfileIdx(),
			requestDto.getVideosIdx()};
		this.jdbcTemplate.update(query, params);
	}

	public VideoPlay.getVideoPlayStatusAtMainMenuResDto getVideoPlayStatusAtMainMenu(int profileIdx, int videoIdx) {
		String query = "select P.currentPlayTime, VS.videosIdx from Video as V\n"
			+ "left join Videos as VS\n"
			+ "on ? = VS.videoIdx\n"
			+ "left join Play as P\n"
			+ "on P.videosIdx = VS.videosIdx\n"
			+ "where P.profileIdx = ?\n"
			+ "group by videosIdx;";
		return this.jdbcTemplate.queryForObject(query,
			(rs, rowNum) -> new VideoPlay.getVideoPlayStatusAtMainMenuResDto(
				rs.getFloat("currentPlayTime"),
				rs.getInt("videosIdx")
			),
			videoIdx, profileIdx);
	}
}