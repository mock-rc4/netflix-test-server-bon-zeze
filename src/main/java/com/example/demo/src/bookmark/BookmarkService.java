package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.domain.PatchBookmarkReq;
import com.example.demo.src.bookmark.domain.PostBookmarkReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkDao bookmarkDao;

    public int create(PostBookmarkReq postBookmarkReq) throws BaseException {
        try {
            if(bookmarkDao.checkBookmarkExists(postBookmarkReq)!=0){
                throw new BaseException(POST_BOOKMARK_ALREADY_EXISTS);
            }
            return bookmarkDao.create(postBookmarkReq);
        } catch (BaseException exception) {
            throw new BaseException(POST_BOOKMARK_ALREADY_EXISTS);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void update(PatchBookmarkReq patchBookmarkReq) throws BaseException {
        try {
            int result = bookmarkDao.update(patchBookmarkReq);
            if (result == 0) {
                throw new BaseException(PATCH_BOOKMARK_STATUS_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
