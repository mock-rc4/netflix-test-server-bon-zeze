package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.domain.GetBookmarkRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class BookmarkProvider {

    private final BookmarkDao bookmarkDao;

    public List<GetBookmarkRes> getBookmarks(int profileIdx) throws BaseException {
        try {
            return bookmarkDao.getBookmarks(profileIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
