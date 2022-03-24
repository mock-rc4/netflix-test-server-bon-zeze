package com.example.demo.src.bookmark;

import com.example.demo.src.bookmark.domain.GetBookmarkRes;
import com.example.demo.src.bookmark.domain.PatchBookmarkReq;
import com.example.demo.src.bookmark.domain.PostBookmarkReq;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BookmarkDao {

    private final JdbcTemplate jdbcTemplate;

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

    // 미완성  - JOIN~~~~
    public List<GetBookmarkRes> getBookmarks(int profileIdx) {
        //String sql = "select * from bookmark where profileIdx = ?";
        String query = "select"
                + "join" +
                "where Bookmark.profileIdx = ? and Bookmark.status = '1'";
        return this.jdbcTemplate.query(query, bookmarkRowMapper(), profileIdx);
    }

    private RowMapper<GetBookmarkRes> bookmarkRowMapper(){
        return (rs, rowNum) -> new GetBookmarkRes(
                rs.getInt("videoIdx"),
                rs.getString("photoUrl"),
                rs.getInt("ageGrade"),
                rs.getInt("season"),
                rs.getInt("runningTime"),
                rs.getString("character")
        );
    }
}
