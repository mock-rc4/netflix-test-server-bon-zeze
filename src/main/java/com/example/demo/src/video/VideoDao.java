package com.example.demo.src.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.src.character.CharacterDao;
import com.example.demo.src.video.domain.*;

@Repository
public class VideoDao {

    private final JdbcTemplate jdbcTemplate;
    private final CharacterDao characterDao;

    @Autowired
    public VideoDao(JdbcTemplate jdbcTemplate, CharacterDao characterDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.characterDao = characterDao;
    }

    public List<Video.getVideoResDto> getVideosByGenre(int videoType, String genre) {
        String isMovieOrSeries = videoType == 0 ? "=" : "<";
        String query = "select V.* from GenreContact as GC "
                + "left join Video as V on GC.videoIdx = V.videoIdx "
                + "left join Genre as G on G.genreIdx = GC.genreIdx where G.name = ? and 0 " + isMovieOrSeries
                + "V.season";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new Video.getVideoResDto(
                        rs.getInt("videoIdx"),
                        rs.getInt("year"),
                        rs.getInt("season"),
                        rs.getInt("ageGrade"),
                        rs.getString("title"),
                        rs.getString("runningTime"),
                        rs.getString("photoUrl"),
                        rs.getString("summary"),
                        rs.getString("director"),
                        rs.getString("resolution"),
                        rs.getString("previewVideoUrl"),
                        rs.getString("openDate")
                ),
                genre);
    }

    public List<VideoContent.resDto> getVideoContentsByVideoIdx(int videoIdx) {
        String query = "select * from Videos where videoIdx = ? order by title ,season";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new VideoContent.resDto(
                        rs.getString("title"),
                        rs.getString("runningTime"),
                        rs.getString("summary"),
                        rs.getInt("season"),
                        rs.getInt("episode")
                ),
                videoIdx);
    }

    public List<VideoContent.resDto> getVideoContentsByVideoIdxAndSeasonNumber(int videoIdx, int seasonNumber) {
        String query = "select * from Videos where videoIdx = ? and season = ?";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new VideoContent.resDto(
                        rs.getString("title"),
                        rs.getString("runningTime"),
                        rs.getString("summary"),
                        rs.getInt("season"),
                        rs.getInt("episode")
                ),
                videoIdx, seasonNumber);
    }

    public List<Video.getEachSeasonEpisodeCountsResDto> getEachSeasonEpisodeCounts(int videoIdx) {
        String query = "select season, count(episode) as episodeCount from Videos where videoIdx = ? group by season";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new Video.getEachSeasonEpisodeCountsResDto(
                        rs.getInt("season"),
                        rs.getInt("episodeCount")
                ),
                videoIdx);
    }

    public int checkHasVideoIdx(int videoIdx) {
        String query = "select exists(select videoIdx from Video where videoIdx = ?)";
        int params = videoIdx;
        return this.jdbcTemplate.queryForObject(query,
                int.class,
                params);
    }

    public List<GetVideoRes> getNewVideos() {
        String query = "select videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n"
                + "from Video\n"
                + "order by updatedAt desc limit 20";

        List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper());
        setVideoCharacters(videos);
        return videos;
    }


    //현재 10개까지 값이 안불러와지는 것은 좋아요에 대한 데이터가 부족해서. (데이터 추가 필요)
    public List<GetVideoRes> getTopTenVideos() {
        String query = "select totalScore, Video.videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n" +
                "from (select videoIdx, sum(status) as totalScore from Assessment group by videoIdx order by totalScore desc limit 10) as Popular\n" +
                "join Video on Video.videoIdx = Popular.videoIdx";

        List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper());
        setVideoCharacters(videos);
        return videos;
    }

    //Top10과 차이를 위해 찜하기 개수가 많은 것으로 반환
    public List<GetVideoRes> getPopularVideos() {
        String query = "select totalScore, Video.videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n" +
                "from (select videoIdx, status, count(status) as totalScore from Bookmark group by videoIdx having status = 1 order by totalScore desc limit 20) as Popular\n" +
                "join Video on Video.videoIdx = Popular.videoIdx";

        List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper());
        setVideoCharacters(videos);
        return videos;
    }


    public List<GetVideoRes> getWatchingVideos(int profileIdx) {
        String query = "select Video.videoIdx, Video.photoUrl, Video.ageGrade, Video.season, Video.runningTime, Video.resolution\n" +
                "from Video\n" +
                "join Videos on Video.videoIdx = Videos.videoIdx\n" +
                "join Play on Play.videosIdx = Videos.videosIdx\n" +
                "where Play.profileIdx = ?";

        List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper(), profileIdx);
        setVideoCharacters(videos);
        return videos;
    }


    public List<GetVideoRes> getGenreVideos(int genreIdx) {
        String query = "select Video.videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n" +
                "from Video\n" +
                "join GenreContact GC on Video.videoIdx = GC.videoIdx\n" +
                "join Genre G on G.genreIdx = GC.genreIdx\n" +
                "where G.genreIdx = ?";

        List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper(), genreIdx);
        setVideoCharacters(videos);
        return videos;
    }

    public List<Video.getVideoResDto> getVideosByActor(int actorIdx) {
        String query = "select V.videoIdx, V.year, V.season, V.ageGrade, V.title, V.runningTime, "
                + "V.photoUrl, V.summary, V.director, V.resolution, V.previewVideoUrl, V.openDate from ActorParticipate as AP "
                + "left join Video as V "
                + "on V.videoIdx = AP.videoIdx "
                + "where actorIdx = ? "
                + "order by V.updatedAt desc";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new Video.getVideoResDto(
                        rs.getInt("videoIdx"),
                        rs.getInt("year"),
                        rs.getInt("season"),
                        rs.getInt("ageGrade"),
                        rs.getString("title"),
                        rs.getString("runningTime"),
                        rs.getString("photoUrl"),
                        rs.getString("summary"),
                        rs.getString("director"),
                        rs.getString("resolution"),
                        rs.getString("previewVideoUrl"),
                        rs.getString("openDate")
                ),
                actorIdx);
    }

    public List<Video.getVideoResDto> getVideosByCharacter(int characterIdx) {
        String query = "select V.videoIdx, V.year, V.season, V.ageGrade, V.title, V.runningTime,"
                + " V.photoUrl, V.summary, V.director, V.resolution, V.previewVideoUrl, V.openDate from CharacterContact as CC "
                + "left join Video as V "
                + "on V.videoIdx = CC.videoIdx "
                + "where characterIdx = ? "
                + "order by V.updatedAt desc";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new Video.getVideoResDto(
                        rs.getInt("videoIdx"),
                        rs.getInt("year"),
                        rs.getInt("season"),
                        rs.getInt("ageGrade"),
                        rs.getString("title"),
                        rs.getString("runningTime"),
                        rs.getString("photoUrl"),
                        rs.getString("summary"),
                        rs.getString("director"),
                        rs.getString("resolution"),
                        rs.getString("previewVideoUrl"),
                        rs.getString("openDate")
                ),
                characterIdx);
    }

    public List<VideoDetail.actorInfoResDto> getActorsByVideoIdx(int videoIdx) {
        String query = "select A.actorIdx, A.name from ActorParticipate as AP\n"
                + "left join Actor as A\n"
                + "on A.actorIdx = AP.actorIdx\n"
                + "where AP.videoIdx = ?;";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new VideoDetail.actorInfoResDto(
                        rs.getInt("actorIdx"),
                        rs.getString("name")
                ),
                videoIdx);
    }

    public List<VideoDetail.genreInfoResDto> getGenresByVideoIdx(int videoIdx) {
        String query = "select G.genreIdx, G.name from GenreContact as GC\n"
                + "left join Genre as G\n"
                + "on G.genreIdx = GC.genreIdx\n"
                + "where GC.videoIdx = ? ";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new VideoDetail.genreInfoResDto(
                        rs.getInt("genreIdx"),
                        rs.getString("name")
                ),
                videoIdx);
    }

    public List<VideoDetail.characterInfoResDto> getCharactersByVideoIdx(int videoIdx) {
        String query = "select C.characterIdx, C.name from CharacterContact as CC\n"
                + "left join `Character` as C\n"
                + "on C.characterIdx = CC.characterIdx\n"
                + "where CC.videoIdx = ? ";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new VideoDetail.characterInfoResDto(
                        rs.getInt("characterIdx"),
                        rs.getString("name")
                ),
                videoIdx);
    }

    public List<Video.getVideoResDto> getVideoDetailByVideoIdx(int videoIdx) {
        String query = "select * from Video where videoIdx = ?";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new Video.getVideoResDto(
                        rs.getInt("videoIdx"),
                        rs.getInt("year"),
                        rs.getInt("season"),
                        rs.getInt("ageGrade"),
                        rs.getString("title"),
                        rs.getString("runningTime"),
                        rs.getString("photoUrl"),
                        rs.getString("summary"),
                        rs.getString("director"),
                        rs.getString("resolution"),
                        rs.getString("previewVideoUrl"),
                        rs.getString("openDate")
                ),
                videoIdx);
    }

	public List<GetVideoRes> getVideosByMovieTitle(String keyword) {
		String query = "select videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n" +
				"from Video\n" +
				"where title like ?";
		String param = "%" + keyword + "%";

		List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper(), param);
		setVideoCharacters(videos);
		return videos;
	}

	public List<GetVideoRes> getVideosByActorName(String keyword) {
		String query = "select Video.videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n" +
				"from Video\n" +
				"join ActorParticipate AP on Video.videoIdx = AP.videoIdx\n" +
				"join Actor A on A.actorIdx = AP.actorIdx\n" +
				"where A.name like ?";
		String param = "%" + keyword + "%";

		List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper(), param);
		setVideoCharacters(videos);
		return videos;
	}

	public List<GetVideoRes> getVideosByGenreName(String keyword) {
		String query = "select Video.videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n" +
				"from Video\n" +
				"join GenreContact GC on Video.videoIdx = GC.videoIdx\n" +
				"join Genre G on G.genreIdx = GC.genreIdx\n" +
				"where G.name like ?";
		String param = "%" + keyword + "%";

		List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper(), param);
		setVideoCharacters(videos);
		return videos;
	}


	public List<GetVideoRes> getThisWeekOpenVideos() {
		String query = "select videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n" +
				"from Video\n" +
				"where openDate > now() and openDate < date_add(now(), interval 1 week)";

		List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper());
		setVideoCharacters(videos);
		return videos;
	}

	public List<GetVideoRes> getNextWeekOpenVideos() {
		String query = "select videoIdx, photoUrl, ageGrade, season, runningTime, resolution\n" +
				"from Video\n" +
				"where openDate >= date_add(now(), interval 1 week) and openDate < date_add(now(), interval 2 week)";

		List<GetVideoRes> videos = this.jdbcTemplate.query(query, videoRowMapper());
		setVideoCharacters(videos);
		return videos;
	}


    private void setVideoCharacters(List<GetVideoRes> videos) {
        for (GetVideoRes video : videos) {
            int videoIdx = video.getVideoIdx();
            List<String> characters = characterDao.getVideoCharacters(videoIdx);
            video.setCharacter(characters);
        }
    }

    private RowMapper<GetVideoRes> videoRowMapper() {
        return (rs, rowNum) -> new GetVideoRes(
                rs.getInt("videoIdx"),
                rs.getString("photoUrl"),
                rs.getInt("ageGrade"),
                rs.getInt("season"),
                rs.getInt("runningTime"),
                rs.getString("resolution")
        );
    }
}