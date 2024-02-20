package com.test.comment.service;

import com.test.comment.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);

    List<CommentDto> getCommentWithPostId(String postId);
}
