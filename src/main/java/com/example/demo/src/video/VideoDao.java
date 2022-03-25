package com.example.demo.src.video;

import java.util.List;

import com.example.demo.src.character.CharacterDao;
import com.example.demo.src.video.domain.GetVideoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.src.video.domain.Video;

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
                + "V.season;";
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
                        rs.getString("director")
                ),
                genre);
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