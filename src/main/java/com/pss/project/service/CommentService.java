package com.pss.project.service;

import com.pss.project.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(long postId,CommentDto commentDto);

    List<CommentDto> readComment(long postId);

    CommentDto readCommentByCommentId(long postId, int commentId);

    CommentDto updateComment(long postId, int commentId, CommentDto commentDto);

    String deleteComment(long postId, int commentId);
}
