package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.bookmark.domain.GetBookmarkRes;
import com.example.demo.src.bookmark.domain.PatchBookmarkReq;
import com.example.demo.src.bookmark.domain.BookmarkReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkProvider bookmarkProvider;

    private static final String SUCCESS = "SUCCESS";
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BookmarkController(BookmarkService bookmarkService, BookmarkProvider bookmarkProvider) {
        this.bookmarkService = bookmarkService;
        this.bookmarkProvider = bookmarkProvider;
    }

    @PostMapping("")
    public BaseResponse<Integer> createBookmark(@RequestBody BookmarkReq bookmarkReq) {
        try {
            int bookmark_idx = bookmarkService.create(bookmarkReq);
            return new BaseResponse<>(bookmark_idx);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //즐겨찾기 자리에서 삭제하면 바로 목록 바뀌게
    @PatchMapping("")
    public BaseResponse<String> updateBookmark(@RequestBody PatchBookmarkReq patchBookmarkReq) {
        try {
            bookmarkService.update(patchBookmarkReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("")
    public BaseResponse<Integer> getBookmarkSetting(@RequestBody BookmarkReq bookmarkReq) {
        try {
            int bookmarkStatus = bookmarkProvider.getBookmarkSetting(bookmarkReq);
            return new BaseResponse<>(bookmarkStatus);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/{profileIdx}")
    public BaseResponse<List<GetBookmarkRes>> getBookmarks(@PathVariable("profileIdx") int profileIdx) {
        try {
            List<GetBookmarkRes> getBookmarkRes = bookmarkProvider.getBookmarks(profileIdx);
            return new BaseResponse<>(getBookmarkRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
