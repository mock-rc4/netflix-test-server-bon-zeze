package com.example.demo.src.bookmark;

import com.example.demo.src.bookmark.domain.GetBookmarkRes;
import com.example.demo.src.bookmark.domain.PatchBookmarkReq;
import com.example.demo.src.bookmark.domain.PostBookmarkReq;
import com.example.demo.src.character.CharacterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookmarkDao {

    private final JdbcTemplate jdbcTemplate;
    private final CharacterDao characterDao;

    @Autowired
    public BookmarkDao(JdbcTemplate jdbcTemplate, CharacterDao characterDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.characterDao = characterDao;
    }

    public int create(PostBookmarkReq postBookmarkReq) {
        String query = "insert into Bookmark (videoIdx, profileIdx) values (?,?)";
        Object[] bookmarkParam = new Object[]{
                postBookmarkReq.getVideoIdx(),
                postBookmarkReq.getProfileIdx()};
        this.jdbcTemplate.update(query, bookmarkParam);

        String lastIdSql = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastIdSql, int.class);
    }

    public int update(PatchBookmarkReq patchBookmarkReq) {
        String query = "update Bookmark set profileIdx = ?, videoIdx = ?, status = ?, updatedAt = NOW()";
        Object[] params = new Object[]{patchBookmarkReq.getProfileIdx(), patchBookmarkReq.getVideoIdx(), patchBookmarkReq.getNewStatus()};
        return this.jdbcTemplate.update(query, params);
    }

    public List<GetBookmarkRes> getBookmarks(int profileIdx) {
        //String sql = "select * from bookmark where profileIdx = ?";
        String query = "select Bookmark.bookmarkIdx, Video.videoIdx, Video.photoUrl, Video.ageGrade, Video.season, Video.runningTime, Video.resolution\n" +
                "from Video\n" +
                "join Bookmark on Bookmark.videoIdx = Video.videoIdx\n" +
                "where Bookmark.profileIdx = ?";
        List<GetBookmarkRes> bookmarks = this.jdbcTemplate.query(query, bookmarkRowMapper(), profileIdx);
        setVideoCharacters(bookmarks);
        return bookmarks;
    }

    private RowMapper<GetBookmarkRes> bookmarkRowMapper() {
        return (rs, rowNum) -> new GetBookmarkRes(
                rs.getInt("bookmarkIdx"),
                rs.getInt("videoIdx"),
                rs.getString("photoUrl"),
                rs.getInt("ageGrade"),
                rs.getInt("season"),
                rs.getInt("runningTime"),
                rs.getString("resolution")
        );
    }

    private void setVideoCharacters(List<GetBookmarkRes> bookmarks) {
        for (GetBookmarkRes bookmark : bookmarks) {
            int videoIdx = bookmark.getVideoIdx();
            //해당 비디오에 해당하는 캐릭터들만 가져오기
            List<String> characters = characterDao.getVideoCharacters(videoIdx);
            bookmark.setCharacter(characters);
        }

    }
}
